package com.example.android.popularmoviesV2.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private static final String TAG = FavoritesAdapter.class.getSimpleName();
    private Cursor mCursor;
    private Context mContext;

    public FavoritesAdapter(Context context) {
        mContext = context;
        //mCursor = cursor;
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

        // Indices for the _id, image, and movie_title columns
        int idIndex = mCursor.getColumnIndex(MovieContract.MovieEntry._ID);
        int imageIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_THUMBNAIL);
        int titleIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE);
        //int movieIdIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);

        // get to the right location in the cursor
        mCursor.moveToPosition(position);

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String image = mCursor.getString(imageIndex);
        String title = mCursor.getString(titleIndex);
        //int movieId = mCursor.getInt(movieIdIndex);

        //Set values
        holder.itemView.setTag(id);
        //holder.favoriteThumbnail.setImageURI(Uri.parse(image));
        holder.favoriteMovieTitle.setText(title);
        Picasso.with(mContext)
                .load(Uri.parse(image))
                .placeholder(R.drawable.default_placeholder)
                .error(R.drawable.default_placeholder)
                .into(holder.favoriteThumbnail);


        // Move the mCursor to the position of the item to be displayed
       /* if (!mCursor.moveToPosition(position))
            return;*/

        // Update the view holder with the information needed to display
        /*String movieTitle = mCursor.getString(mCursor.
                getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE));
        String favoriteThumbnail = mCursor.getString(mCursor.
                getColumnIndex(MovieContract.MovieEntry.COLUMN_THUMBNAIL));*/

        // Retrieve the id from the cursor and display the movie Title and thumbnail

        /*long id = mCursor.getLong(mCursor.getColumnIndex(MovieContract.MovieEntry._ID));
        holder.favoriteMovieTitle.setText(movieTitle);
        holder.itemView.setTag(id);
        String favoriteThumbUrl = Uri.parse(favoriteThumbnail).toString();

        Picasso.with(mContext)
                .load(favoriteThumbUrl)
                .placeholder(R.drawable.default_placeholder)
                .error(R.drawable.default_placeholder)
                .into(holder.favoriteThumbnail);*/

    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */

    public Cursor swapCursor(Cursor newCursor) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == newCursor) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = newCursor; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
        return temp;


        /*// Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }*/
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
