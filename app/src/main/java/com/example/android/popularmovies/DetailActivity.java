package com.example.android.popularmovies;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    @BindDrawable(R.drawable.default_placeholder)
    Drawable placeHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null){
            closeOnError();
        }



    }

    private void closeOnError() {
        finish();;
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_LONG).show();
    }

}
