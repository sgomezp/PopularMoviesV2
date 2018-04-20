package com.example.android.popularmoviesV2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmoviesV2.data.MovieContract;
import com.example.android.popularmoviesV2.model.Movies;
import com.example.android.popularmoviesV2.utils.Constants;
import com.example.android.popularmoviesV2.utils.FragmentsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = DetailActivity.class.getSimpleName();
    private static final String FAVORITE_STATE = "favorite_state";
    private static final int LOADER_ID = 0;


    public static Movies currentMovie;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    private boolean mIsFavorite;
    private MenuItem mMenuAddFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        //Handle Toolbar
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
            getSupportActionBar().setTitle(R.string.title_bar_details_activity);


        } else {
            Log.d(TAG, "Detail Activity myToolBar es Null");
        }


        //Retrieve Intents Extras
        Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        if (extras != null) {

            int position = extras.getInt("position");

            currentMovie = MainActivity.movies.get(position);


        }


        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager(), DetailActivity.this);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()

        tabLayout.setupWithViewPager(viewPager);

        // Initialize the loader only for the first time the activity is launched
        if (savedInstanceState != null) {
            mIsFavorite = savedInstanceState.getBoolean(FAVORITE_STATE);
        } else {
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FAVORITE_STATE, mIsFavorite);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mIsFavorite = savedInstanceState.getBoolean(FAVORITE_STATE);
        }
    }


    public void addToFavorites() {

        Uri uri;

        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, currentMovie.getId());
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, currentMovie.getOriginalTitle());
        cv.put(MovieContract.MovieEntry.COLUMN_THUMBNAIL, Uri.parse(Constants.URL_IMAGE +
                currentMovie.getPosterPath()).toString());


        try {
            uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, cv);

        } catch (IllegalArgumentException aaa) {
            uri = null;
            Log.e(TAG, "aaa: " + aaa.toString());
        }

        if (uri == null) {
            Toast.makeText(getBaseContext(), "Failed to add movie to Favorite", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), "Movie added to favorites", Toast.LENGTH_LONG).show();
            mIsFavorite = true;
            mMenuAddFavorite.setIcon(R.drawable.ic_favorite_white_24dp);
        }
    }


    //@Override
    public Loader<Cursor> onCreateLoader(int id, Bundle loaderArgs) {

        final String selection = "((" + MovieContract.MovieEntry.COLUMN_MOVIE_ID +
                " = " + currentMovie.getId() + "))";

        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the favorite data
            Cursor mFavoriteData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mFavoriteData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mFavoriteData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data

                // Query and load all favorite data in the background;
                try {
                    return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            selection,
                            null,
                            null);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mFavoriteData = data;
                super.deliverResult(data);
            }

        };
    }

    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    //@Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update the data that the adapter uses to create ViewHolders
        if (data.moveToFirst()) {
            DatabaseUtils.dumpCursor(data);
            mIsFavorite = true;
        } else {
            mIsFavorite = false;
        }

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    // @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    //Handle menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_main.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        mMenuAddFavorite = menu.getItem(0);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        Log.d(TAG, "onOptionsItemSelected: id: " + id);
        switch (id) {
            case R.id.menu_favorites:
                if (mIsFavorite) {
                    Toast.makeText(this, "This movie is already added to favorites", Toast.LENGTH_LONG).show();
                } else {
                    addToFavorites();
                }

                return true;

        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mMenuAddFavorite = menu.getItem(0);
        if (mIsFavorite) {
            mMenuAddFavorite.setIcon(R.drawable.ic_favorite_white_24dp);
        } else {
            mMenuAddFavorite.setIcon(R.drawable.ic_favorite_border_white_24dp);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void closeOnError() {
        this.finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_LONG).show();
    }

}
