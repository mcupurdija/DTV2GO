package rs.multitelekom.dtv2go.ui;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import rs.multitelekom.dtv2go.R;
import rs.multitelekom.dtv2go.adapter.NavDrawerListAdapter;
import rs.multitelekom.dtv2go.model.NavDrawerItem;
import rs.multitelekom.dtv2go.ui.channels.ChannelsFragment;
import rs.multitelekom.dtv2go.ui.favourites.FavouritesFragment;


public class MainActivity extends BaseActivity {

    private final static int WAIT_FOR_DRAWER_TO_CLOSE_DELAY = 300;

    private final static int DRAWER_LOGO_POSITION = 0;
    private final static int DRAWER_USER_POSITION = 1;
    private final static int DRAWER_MAIN_MENU_TITLE_POSITION = 2;
    private final static int DRAWER_CHANNEL_LIST_POSITION = 3;
    private final static int DRAWER_FAVOURITES_POSITION = 4;
    private final static int DRAWER_OTHER_TITLE_POSITION = 5;
    private final static int DRAWER_SETTINGS_POSITION = 6;

    private String[] navMenuTitles;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private TypedArray navMenuIcons;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBar actionBar;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        actionBar = getSupportActionBar();
        mTitle = mDrawerTitle = getTitle();

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        navDrawerItems = new ArrayList<>();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[DRAWER_LOGO_POSITION], -1, false, true));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[DRAWER_USER_POSITION], navMenuIcons.getResourceId(DRAWER_USER_POSITION, -1), true, false));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[DRAWER_MAIN_MENU_TITLE_POSITION], -1, false, true));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[DRAWER_CHANNEL_LIST_POSITION], navMenuIcons.getResourceId(DRAWER_CHANNEL_LIST_POSITION, -1), false, false, true, getChannelsCount()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[DRAWER_FAVOURITES_POSITION], navMenuIcons.getResourceId(DRAWER_FAVOURITES_POSITION, -1), false, false, true, getFavouritesCount()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[DRAWER_OTHER_TITLE_POSITION], -1, false, true));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[DRAWER_SETTINGS_POSITION], navMenuIcons.getResourceId(DRAWER_SETTINGS_POSITION, -1), false, false));

        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.app_name,
                R.string.app_name
        ) {
            public void onDrawerClosed(View view) {
                actionBar.setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                actionBar.setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // display dashboard
        if (savedInstanceState == null) {
            displayView(DRAWER_CHANNEL_LIST_POSITION);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }

    private void displayView(int position) {

        switch (position) {
            case DRAWER_USER_POSITION:
                fragment = new TestFragment();
                break;
            case DRAWER_CHANNEL_LIST_POSITION:
                fragment = new ChannelsFragment();
                break;
            case DRAWER_FAVOURITES_POSITION:
                fragment = new FavouritesFragment();
                break;
            case DRAWER_SETTINGS_POSITION:
                fragment = new TestFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            try {
                // wait for drawer to close
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commitAllowingStateLoss();
                    }
                }, WAIT_FOR_DRAWER_TO_CLOSE_DELAY);

                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                setTitle(navMenuTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
            } catch (Exception e) {
            }
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void refreshDrawer() {
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[DRAWER_CHANNEL_LIST_POSITION], navMenuIcons.getResourceId(DRAWER_CHANNEL_LIST_POSITION, -1), false, false, true, getChannelsCount()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[DRAWER_FAVOURITES_POSITION], navMenuIcons.getResourceId(DRAWER_FAVOURITES_POSITION, -1), false, false, true, getFavouritesCount()));
        adapter.notifyDataSetChanged();
    }

    private String getChannelsCount() {
        return "99";
    }

    private String getFavouritesCount() {
        return "0";
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        actionBar.setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
