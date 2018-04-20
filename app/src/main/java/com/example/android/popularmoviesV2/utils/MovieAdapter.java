package com.example.android.popularmoviesV2.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmoviesV2.DetailActivity;
import com.example.android.popularmoviesV2.R;
import com.example.android.popularmoviesV2.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {


    private static final String TAG = MovieAdapter.class.getSimpleName();


    //private final Movies[] mMovies;
    private List<Movies> mMovies;
    private int gridLayout;
    private Context mContext;


    /**
     * Constructor for MovieAdapter that accepts a number of items to display and the specification
     * for the ListItemClickListener.
     */

    public MovieAdapter(List<Movies> movies, int gridLayout, Context context) {
        this.mMovies = movies;
        this.gridLayout = gridLayout;
        this.mContext = context;


    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Create a new View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.linearlayout_movies, parent, false);

        return new ViewHolder(view);

    }


    //Create new views (invoked by the layout manager)

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Get the data model based on position


        final Movies movie = mMovies.get(position);
        ViewHolder itemViewHolder = holder;
        Picasso.with(mContext)
                .load(Constants.URL_IMAGE + movie.getPosterPath())
                .placeholder(R.drawable.default_placeholder)
                .error(R.drawable.default_placeholder)
                .into(itemViewHolder.viewHolderPoster);
    }


    // Replace the contents of a view (invoke by the layout manager)

    public void updateRecyclerData(List<Movies> newMovies) {
        this.mMovies = newMovies;
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView viewHolderPoster;
        //Drawable placeHolder;
        View gridLayout;

        // Constructor for ViewHolder

        public ViewHolder(View itemView) {
            super(itemView);
            gridLayout = itemView;
            viewHolderPoster = itemView.findViewById(R.id.imageview_poster);
            itemView.setOnClickListener(this);

        }


        //Handles the row being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but user clicked it before the UI removed it
                // We can access the data within the views
                Intent intent = new Intent(mContext.getApplicationContext(), DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("position", position);
                mContext.getApplicationContext().startActivity(intent);

            }
        }
    }


}