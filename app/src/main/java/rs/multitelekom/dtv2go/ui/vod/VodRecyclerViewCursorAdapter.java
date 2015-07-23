package rs.multitelekom.dtv2go.ui.vod;

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

public class VodRecyclerViewCursorAdapter extends RecyclerViewCursorAdapter<VodRecyclerViewCursorAdapter.ViewHolder> {

    private Context mContext;
    private OnItemClickListener mItemClickListener;
    private OnLongClickListener mItemLongClickListener;

    public VodRecyclerViewCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView tvMovieTitle, tvMovieDescription, tvMovieDuration, tvMovieGenre;
        public ImageView ivMoviePoster;

        public ViewHolder(View view) {
            super(view);
            ivMoviePoster = (ImageView) view.findViewById(R.id.ivMoviePoster);
            tvMovieTitle = (TextView) view.findViewById(R.id.tvMovieTitle);
            tvMovieDescription = (TextView) view.findViewById(R.id.tvMovieDescription);
            tvMovieDuration = (TextView) view.findViewById(R.id.tvMovieDuration);
            tvMovieGenre = (TextView) view.findViewById(R.id.tvMovieGenre);
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_vod, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor) {

        viewHolder.tvMovieTitle.setText(cursor.getString(VodFragment.MoviesQuery.MOVIE_TITLE));
        viewHolder.tvMovieDescription.setText(cursor.getString(VodFragment.MoviesQuery.MOVIE_DESCRIPTION));
        viewHolder.tvMovieDuration.setText(cursor.getString(VodFragment.MoviesQuery.MOVIE_DURATION));
        viewHolder.tvMovieGenre.setText(cursor.getString(VodFragment.MoviesQuery.MOVIE_GENRE));
        viewHolder.ivMoviePoster.setImageBitmap(null);
        String icon_uri = cursor.getString(VodFragment.MoviesQuery.MOVIE_POSTER);
        if (!TextUtils.isEmpty(icon_uri)) {
            Picasso.with(viewHolder.ivMoviePoster.getContext()).cancelRequest(viewHolder.ivMoviePoster);
            Picasso.with(viewHolder.ivMoviePoster.getContext()).load(icon_uri).placeholder(R.drawable.ic_placeholder).resize(0, UIUtils.convertToDip(mContext, 140)).into(viewHolder.ivMoviePoster);
        }
    }
}
