package com.example.android.popularmoviesV2.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviesV2.R;
import com.example.android.popularmoviesV2.model.MovieTrailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sgomezp on 07/04/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private static final String TAG = TrailerAdapter.class.getSimpleName();
    private final OnItemClickListener mListener;
    private List<MovieTrailer> mMovieTrailerList;
    private int mLayout;
    private Context mContext;

    public TrailerAdapter(List<MovieTrailer> movieTrailerList, int layout, Context context,
                          OnItemClickListener listener) {
        mMovieTrailerList = movieTrailerList;
        mLayout = layout;
        mContext = context;
        mListener = listener;
    }

    public static void watchYoutubeVideo(Context context, String key, int position) {
        Uri trailerUrlApp = Uri.parse(Constants.BASE_URL_VIDEO_TRAILER_APP + key);
        Uri trailerUrlWeb = Uri.parse(Constants.BASE_URL_VIDEO_TRAILER_YT + key);
        Intent appIntent = new Intent(Intent.ACTION_VIEW, trailerUrlApp);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, trailerUrlWeb);
        appIntent.putExtra("position", position);
        webIntent.putExtra("position", position);
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }

    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Create a new View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_trailer, parent, false);
        return new TrailerViewHolder(view);

    }

    //Create new views (invoked by the layout manager
    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        //final MovieTrailer movieTrailer = mMovieTrailerList.get(position);
        holder.bind(mMovieTrailerList.get(position), mListener);

       /*Picasso.with(mContext)
                .load(movieTrailer.getVideoImageThumb(movieTrailer))
                .placeholder(R.drawable.default_placeholder)
                .error(R.drawable.default_placeholder)
                .into(holder.mTrailerImage);


        holder.mTrailerName.setText(movieTrailer.getNameTrailer());
*/
    }

    @Override
    public int getItemCount() {
        if (mMovieTrailerList == null) {
            Log.d(TAG, "getItemCount: mMovieTrailerList es null");
            return 0;
        } else {
            Log.d(TAG, "getItemCount: mMovieTrailerList " + mMovieTrailerList.size());
            return (mMovieTrailerList.size());
        }
    }

    public void updateRecyclerData(List<MovieTrailer> newReview) {
        this.mMovieTrailerList = newReview;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(MovieTrailer movieTrailer);
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.trailer_image)
        ImageView mTrailerImage;

        @BindView(R.id.trailer_name)
        TextView mTrailerName;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        /*@Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but user clicked it before the UI removed it
                MovieTrailer trailer = mMovieTrailerList.get(position);
                mListener.onItemClick(trailer);
                watchYoutubeVideo(mContext, trailer.getKeyTrailer(), position);
            }
        }*/


        public void bind(final MovieTrailer movieTrailer, final OnItemClickListener listener) {
            Picasso.with(mContext)
                    .load(movieTrailer.getVideoImageThumb(movieTrailer))
                    .placeholder(R.drawable.default_placeholder)
                    .error(R.drawable.default_placeholder)
                    .into(mTrailerImage);

            mTrailerName.setText(movieTrailer.getNameTrailer());

            //Handles the row being clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(movieTrailer);
                }
            });
        }
    }


}
