package com.example.android.popularmoviesV2;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesV2.model.Movies;
import com.example.android.popularmoviesV2.utils.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = DetailsFragment.class.getSimpleName();

    public static Movies currentMovie;

    @BindView(R.id.posterThumbnail)
    ImageView posterThumbnail;

    @BindView(R.id.valueRatingTextView)
    TextView userRating;
    @BindView(R.id.release_date_value)
    TextView releaseDate;
    @BindView(R.id.titleTextView)
    TextView movieTitle;
    @BindView(R.id.overview_value)
    TextView synopsis;

    @BindDrawable(R.drawable.default_placeholder)
    Drawable placeHolder;


    public DetailsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.details_fragment, container, false);

        ButterKnife.bind(this, rootView);

        Bundle extras = getActivity().getIntent().getExtras();


        Intent intent = getActivity().getIntent();
        if (intent == null) {

            Log.d(TAG, "onCreateView:  intent = null " + intent);

            closeOnError();
        }

        if (extras != null) {

            int position = extras.getInt("position");
            Log.d(TAG, "onCreateView: position = " + position);

            //showMovieDetails(position);
            Movies mMovies = MainActivity.movies.get(position);

            movieTitle.setText(mMovies.getOriginalTitle());

            userRating.setText(String.valueOf(mMovies.getVoteAverage()));
            releaseDate.setText(mMovies.getReleaseDate());
            synopsis.setText(mMovies.getOverview());

            Picasso.with(getActivity())
                    .load(Constants.URL_IMAGE + mMovies.getPosterPath())
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .into(posterThumbnail);

        }


        return rootView;
    }


    private void closeOnError() {
        getActivity().finish();
        Toast.makeText(getActivity(), R.string.detail_error_message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRefresh() {

    }
}
