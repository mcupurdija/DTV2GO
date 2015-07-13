package rs.multitelekom.dtv2go.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import rs.multitelekom.dtv2go.R;
import rs.multitelekom.dtv2go.model.NavDrawerItem;

public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return !navDrawerItems.get(position).isHeader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        boolean isUserId = navDrawerItems.get(position).isUserId();
        boolean isHeader = navDrawerItems.get(position).isHeader();
        boolean isCounterVisible = navDrawerItems.get(position).isCounterVisible();
        String title = navDrawerItems.get(position).getTitle();

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }

        RelativeLayout rlDrawerItemLevel = (RelativeLayout) convertView.findViewById(R.id.rlDrawerItemLevel);
        RelativeLayout rlDrawerHeaderLevel = (RelativeLayout) convertView.findViewById(R.id.rlDrawerHeaderLevel);
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.drawer_icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.drawer_title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.drawer_counter);
        TextView txtHeader = (TextView) convertView.findViewById(R.id.drawer_header);

        if (position == 0) {
            rlDrawerItemLevel.setBackgroundResource(R.drawable.drawer_logo);
            imgIcon.setImageDrawable(null);
            txtTitle.setText("");

            txtCount.setVisibility(View.GONE);
            rlDrawerItemLevel.setVisibility(View.VISIBLE);
            rlDrawerHeaderLevel.setVisibility(View.GONE);
        } else if (isUserId) {
            rlDrawerItemLevel.setBackgroundResource(R.drawable.background_tabs_diagonal);
            imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
            txtTitle.setText(title);

            txtCount.setVisibility(View.GONE);
            rlDrawerItemLevel.setVisibility(View.VISIBLE);
            rlDrawerHeaderLevel.setVisibility(View.GONE);
        } else if (isHeader) {
            txtHeader.setText(title);

            txtCount.setVisibility(View.GONE);
            rlDrawerItemLevel.setBackgroundResource(0);
            rlDrawerItemLevel.setVisibility(View.GONE);
            rlDrawerHeaderLevel.setVisibility(View.VISIBLE);
        } else {
            rlDrawerItemLevel.setBackgroundResource(R.drawable.drawer_list_selector);
            imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
            txtTitle.setText(title);

            if (isCounterVisible) {
                txtCount.setText(navDrawerItems.get(position).getCount());
                txtCount.setVisibility(View.VISIBLE);
            } else {
                txtCount.setVisibility(View.GONE);
            }
            rlDrawerItemLevel.setVisibility(View.VISIBLE);
            rlDrawerHeaderLevel.setVisibility(View.GONE);
        }

        return convertView;
    }

}
