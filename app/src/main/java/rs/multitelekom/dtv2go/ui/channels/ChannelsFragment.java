package rs.multitelekom.dtv2go.ui.channels;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import rs.multitelekom.dtv2go.R;
import rs.multitelekom.dtv2go.db.DatabaseContract;
import rs.multitelekom.dtv2go.db.Tables;
import rs.multitelekom.dtv2go.ui.MainActivity;
import rs.multitelekom.dtv2go.ui.video.VideoActivity;
import rs.multitelekom.dtv2go.util.ToastUtils;

public class ChannelsFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener, LoaderManager.LoaderCallbacks<Cursor> {

    private String mCurFilter;
    private int span = 3;

    private Context context;
    private MainActivity mainActivity;
    private ChannelsRecyclerViewCursorAdapter mAdapter;

    private SearchView mSearchView;

    public ChannelsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_channels, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        context = getActivity();
        mainActivity = ((MainActivity) context);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            span = 5;
        }

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, span);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ChannelsRecyclerViewCursorAdapter(context, null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new ChannelsRecyclerViewCursorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Cursor cursor = mAdapter.getCursor();
                if (cursor.moveToPosition(position)) {
                    openVideoActivity(cursor.getString(ChannelsQuery.CHANNEL_NAME), cursor.getString(ChannelsQuery.CHANNEL_VIDEO_URI));
                }
            }
        });
        mAdapter.SetOnItemLongClickListener(new ChannelsRecyclerViewCursorAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {

                Cursor cursor = mAdapter.getCursor();
                if (cursor.moveToPosition(position)) {
                    final int channelID = cursor.getInt(ChannelsQuery.CHANNEL_ID);
                    final String channelName = cursor.getString(ChannelsQuery.CHANNEL_NAME);
                    final String channelVideoUri = cursor.getString(ChannelsQuery.CHANNEL_VIDEO_URI);
                    final String channelIconUri = cursor.getString(ChannelsQuery.CHANNEL_ICON_URI);

                    new MaterialDialog.Builder(context)
                            .items(R.array.channel_list_contextual)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog materialDialog, View view, int which, CharSequence charSequence) {
                                    switch (which) {
                                        case 0:
                                            openVideoActivity(channelName, channelVideoUri);
                                            break;
                                        case 1:
                                            addChannelToFavourites(channelID, channelName, channelVideoUri, channelIconUri);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            })
                            .show();
                }
            }
        });

        getLoaderManager().initLoader(0, null, this);
    }

    private void openVideoActivity(String channelName, String videoUri) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(VideoActivity.NAME_EXTRA_KEY, channelName);
        intent.putExtra(VideoActivity.VIDEO_URI_EXTRA_KEY, videoUri);
        startActivity(intent);
    }

    private void addChannelToFavourites(int channelID, String channelName, String videoUri, String iconUri) {

        Cursor cursor = context.getContentResolver().query(DatabaseContract.Favourites.CONTENT_URI, null, Tables.FAVOURITES + "." + DatabaseContract.Favourites.CHANNEL_VIDEO_URI + "='" + String.valueOf(videoUri) + "'", null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count > 0) {
            ToastUtils.displayToast(context, "Kanal je već dodat u listu omiljenih kanala!");
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.Favourites.CHANNEL_ID, channelID);
            contentValues.put(DatabaseContract.Favourites.CHANNEL_NAME, channelName);
            contentValues.put(DatabaseContract.Favourites.CHANNEL_VIDEO_URI, videoUri);
            contentValues.put(DatabaseContract.Favourites.CHANNEL_ICON_URI, iconUri);
            context.getContentResolver().insert(DatabaseContract.Favourites.CONTENT_URI, contentValues);

            mainActivity.refreshDrawer();
            ToastUtils.displayToast(context, "Kanal je uspešno dodat u listu omiljenih kanala!");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if (isAdded()) {
            MenuItem item_search = menu.add("Pretraga");
            item_search.setIcon(R.drawable.ic_action_search);
            item_search.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT | MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            mSearchView = new MySearchView(context);
            mSearchView.setOnQueryTextListener(this);
            mSearchView.setOnCloseListener(this);
            mSearchView.setIconifiedByDefault(true);
            mSearchView.setQueryHint("ime kanala");
//            setSearchTextColor(mSearchView);
            item_search.setActionView(mSearchView);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri;
        if (mCurFilter != null) {
            baseUri = Uri.withAppendedPath(DatabaseContract.Channels.CONTENT_FILTER_URI, Uri.encode(mCurFilter));
        } else {
            baseUri = DatabaseContract.Channels.CONTENT_URI;
        }
        return new CursorLoader(context, baseUri, ChannelsQuery.PROJECTION, null, null, DatabaseContract.Channels.SORT_BY_NAME);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public static class MySearchView extends SearchView {
        public MySearchView(Context context) {
            super(context);
        }

        @Override
        public void onActionViewCollapsed() {
            setQuery("", false);
            super.onActionViewCollapsed();
        }
    }

    @Override
    public boolean onClose() {
        if (!TextUtils.isEmpty(mSearchView.getQuery())) {
            mSearchView.setQuery(null, true);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String newFilter = !TextUtils.isEmpty(newText) ? newText : null;
        if (mCurFilter == null && newFilter == null) {
            return true;
        }
        if (mCurFilter != null && mCurFilter.equals(newFilter)) {
            return true;
        }
        mCurFilter = newFilter;
        getLoaderManager().restartLoader(0, null, this);
        return true;
    }

    public interface ChannelsQuery {

        String[] PROJECTION = {
                Tables.CHANNELS + "." + BaseColumns._ID,
                Tables.CHANNELS + "." + DatabaseContract.Channels.CHANNEL_ID,
                Tables.CHANNELS + "." + DatabaseContract.Channels.CHANNEL_NAME,
                Tables.CHANNELS + "." + DatabaseContract.Channels.CHANNEL_VIDEO_URI,
                Tables.CHANNELS + "." + DatabaseContract.Channels.CHANNEL_ICON_URI
        };

        int _ID = 0;
        int CHANNEL_ID = 1;
        int CHANNEL_NAME = 2;
        int CHANNEL_VIDEO_URI = 3;
        int CHANNEL_ICON_URI = 4;
    }
}
