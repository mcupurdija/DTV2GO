package rs.multitelekom.dtv2go.ui.vod;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
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
import rs.multitelekom.dtv2go.ui.MainActivity;
import rs.multitelekom.dtv2go.ui.video.VideoActivity;

public class VodFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener, LoaderManager.LoaderCallbacks<Cursor> {

    private String mCurFilter;

    private Context context;
    private MainActivity mainActivity;
    private VodRecyclerViewCursorAdapter mAdapter;

    private SearchView mSearchView;

    public VodFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vod, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        context = getActivity();
        mainActivity = ((MainActivity) context);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new VodRecyclerViewCursorAdapter(context, null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new VodRecyclerViewCursorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Cursor cursor = mAdapter.getCursor();
                if (cursor.moveToPosition(position)) {
                    openVideoActivity(cursor.getString(MoviesQuery.MOVIE_TITLE), cursor.getString(MoviesQuery.MOVIE_VIDEO_URI));
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
            mSearchView.setQueryHint("ime filma");
            mSearchView.setBackgroundResource(0);
            item_search.setActionView(mSearchView);

            menu.add(Menu.NONE, 1, Menu.FIRST + 1, "Svi žanrovi").setIcon(R.drawable.ic_action_filter).setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri;
        if (mCurFilter != null) {
            baseUri = Uri.withAppendedPath(DatabaseContract.Movies.CONTENT_FILTER_URI, Uri.encode(mCurFilter));
        } else {
            baseUri = DatabaseContract.Movies.CONTENT_URI;
        }
        return new CursorLoader(context, baseUri, MoviesQuery.PROJECTION, null, null, DatabaseContract.Movies.SORT_BY_TITLE);
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

    public interface MoviesQuery {

        String[] PROJECTION = {
                Tables.MOVIES + "." + BaseColumns._ID,
                Tables.MOVIES + "." + DatabaseContract.Movies.MOVIE_TITLE,
                Tables.MOVIES + "." + DatabaseContract.Movies.MOVIE_DESCRIPTION,
                Tables.MOVIES + "." + DatabaseContract.Movies.MOVIE_DURATION,
                Tables.MOVIES + "." + DatabaseContract.Movies.MOVIE_GENRE,
                Tables.MOVIES + "." + DatabaseContract.Movies.MOVIE_POSTER,
                Tables.MOVIES + "." + DatabaseContract.Movies.MOVIE_VIDEO_URI,
                Tables.MOVIES + "." + DatabaseContract.Movies.MOVIE_SUBTITLE
        };

        int _ID = 0;
        int MOVIE_TITLE = 1;
        int MOVIE_DESCRIPTION = 2;
        int MOVIE_DURATION = 3;
        int MOVIE_GENRE = 4;
        int MOVIE_POSTER = 5;
        int MOVIE_VIDEO_URI = 6;
        int MOVIE_SUBTITLE = 7;
    }
}
