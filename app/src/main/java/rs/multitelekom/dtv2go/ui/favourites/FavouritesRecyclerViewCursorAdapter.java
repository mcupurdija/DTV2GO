package rs.multitelekom.dtv2go.ui.favourites;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import rs.multitelekom.dtv2go.R;
import rs.multitelekom.dtv2go.adapter.RecyclerViewCursorAdapter;
import rs.multitelekom.dtv2go.util.UIUtils;

public class FavouritesRecyclerViewCursorAdapter extends RecyclerViewCursorAdapter<FavouritesRecyclerViewCursorAdapter.ViewHolder> {

    private Context mContext;
    private OnItemClickListener mItemClickListener;
    private OnLongClickListener mItemLongClickListener;

    public FavouritesRecyclerViewCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView tvItemsNo;
        public ImageView ivItemsImage;

        public ViewHolder(View view) {
            super(view);
            ivItemsImage = (ImageView) view.findViewById(R.id.ivChannelIcon);
            tvItemsNo = (TextView) view.findViewById(R.id.tvChannelName);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mItemLongClickListener != null) {
                mItemLongClickListener.onItemLongClick(v, getLayoutPosition());
                return true;
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public interface OnLongClickListener {
        public void onItemLongClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void SetOnItemLongClickListener(final OnLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_channels, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        // bad practice
        vh.setIsRecyclable(false);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor) {

        viewHolder.tvItemsNo.setText(cursor.getString(FavouritesFragment.FavouritesQuery.CHANNEL_NAME));
        String icon_uri = cursor.getString(FavouritesFragment.FavouritesQuery.CHANNEL_ICON_URI);
        if (!TextUtils.isEmpty(icon_uri)) {

            viewHolder.ivItemsImage.setImageBitmap(null);
            Picasso.with(viewHolder.ivItemsImage.getContext()).cancelRequest(viewHolder.ivItemsImage);
            Picasso.with(viewHolder.ivItemsImage.getContext()).load(icon_uri).resize(0, UIUtils.convertToDip(mContext, 140)).into(viewHolder.ivItemsImage);
        }
    }
}
