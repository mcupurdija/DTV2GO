package rs.multitelekom.dtv2go.ui.channels;

import android.content.Context;
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

import rs.multitelekom.dtv2go.R;
import rs.multitelekom.dtv2go.db.DatabaseContract;
import rs.multitelekom.dtv2go.db.Tables;

public class ChannelsFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener, LoaderManager.LoaderCallbacks<Cursor> {

    private String mCurFilter;
    private int span = 3;

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

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            span = 5;
        }

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), span);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ChannelsRecyclerViewCursorAdapter(getActivity(), null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new ChannelsRecyclerViewCursorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Cursor cursor = mAdapter.getCursor();
                if (cursor.moveToPosition(position)) {
                    int id = cursor.getInt(0);
                    // TODO video activity
                }
            }
        });

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if (isAdded()) {
            MenuItem item_search = menu.add("Pretraga");
            item_search.setIcon(R.drawable.ic_action_search);
            item_search.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT | MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            mSearchView = new MySearchView(getActivity());
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
        return new CursorLoader(getActivity(), baseUri, ChannelsQuery.PROJECTION, null, null, DatabaseContract.Channels.SORT_BY_NAME);
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
                Tables.CHANNELS + "." + DatabaseContract.Channels.CHANNEL_NAME,
                Tables.CHANNELS + "." + DatabaseContract.Channels.CHANNEL_ICON_URI,
                Tables.CHANNELS + "." + DatabaseContract.Channels.CHANNEL_VIDEO_URI
        };

        int _ID = 0;
        int CHANNEL_NAME = 1;
        int CHANNEL_ICON_URI = 2;
        int CHANNEL_VIDEO_URI = 3;
    }
}
