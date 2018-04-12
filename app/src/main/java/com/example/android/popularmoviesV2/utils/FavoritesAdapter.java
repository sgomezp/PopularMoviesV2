package com.example.android.popularmoviesV2.utils;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviesV2.R;
import com.example.android.popularmoviesV2.data.MovieContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sgomezp on 11/04/2018.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private static final String TAG = FavoritesAdapter.class.getSimpleName();
    private Cursor mCursor;
    private Context mContext;

    public FavoritesAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_favorite, parent, false);
        return new FavoritesAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder holder, int position) {

        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return;

        // Update the view holder with the information needed to display
        String movieTitle = mCursor.getString(mCursor.
                getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE));
        String favoriteThumbnail = mCursor.getString(mCursor.
                getColumnIndex(MovieContract.MovieEntry.COLUMN_THUMBNAIL));

        // Retrieve the id from the cursor and display the movie Title and thumbnail

        long id = mCursor.getLong(mCursor.getColumnIndex(MovieContract.MovieEntry._ID));
        holder.favoriteMovieTitle.setText(movieTitle);
        holder.itemView.setTag(id);
        String favoriteThumbUrl = Constants.BASE_URL_THUMB_TRAILER + favoriteThumbnail;

        Picasso.with(mContext)
                .load(favoriteThumbUrl)
                .placeholder(R.drawable.default_placeholder)
                .error(R.drawable.default_placeholder)
                .into(holder.favoriteThumbnail);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.favorite_image)
        ImageView favoriteThumbnail;

        @BindView(R.id.favorite_movie_title)
        TextView favoriteMovieTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
