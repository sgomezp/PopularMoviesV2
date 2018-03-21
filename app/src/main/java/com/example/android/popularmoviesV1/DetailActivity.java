package com.example.android.popularmoviesV1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesV1.model.Movies;
import com.example.android.popularmoviesV1.utils.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sgomezp on 06/03/2018.
 */

public class DetailActivity extends AppCompatActivity{

    public static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.posterThumbnail)
    ImageView posterThumbnail;
    @BindView(R.id.titleTextView)
    TextView movieTitle;
    @BindView(R.id.valueRatingTextView)
    TextView userRating;
    @BindView(R.id.release_date_value)
    TextView releaseDate;

    @BindView(R.id.overview_value)
    TextView synopsis;

    @BindDrawable(R.drawable.default_placeholder)
    Drawable placeHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();

        Toolbar myToolbar = findViewById(R.id.toolbar);

        if (myToolbar != null) {
            setSupportActionBar(myToolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }


        Intent intent = getIntent();
        if (intent == null){
            closeOnError();
        }

        if (extras != null) {
            int position = extras.getInt("position");
            showMovieDetails(position);
        }


    }

    private void showMovieDetails(int movieClicked) {
        Movies mMovies = MainActivity.movies.get(movieClicked);
        movieTitle.setText(mMovies.getOriginalTitle());
        userRating.setText(String.valueOf(mMovies.getVoteAverage()));
        releaseDate.setText(mMovies.getReleaseDate());
        synopsis.setText(mMovies.getOverview());

        Picasso.with(this)
                .load(Constants.URL_IMAGE + mMovies.getPosterPath())
                .placeholder(placeHolder)
                .error(placeHolder)
                .into(posterThumbnail);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_LONG).show();
    }

}
