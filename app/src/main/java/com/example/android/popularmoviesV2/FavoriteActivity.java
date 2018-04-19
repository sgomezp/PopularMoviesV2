package com.example.android.popularmoviesV2;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.popularmoviesV2.data.MovieContract;
import com.example.android.popularmoviesV2.utils.FavoritesAdapter;

public class FavoriteActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    // Constants for logging and referring to a unique loader
    private static final String TAG = FavoriteActivity.class.getSimpleName();
    private static final int FAVORITE_LOADER_ID = 0;
    RecyclerView favoriteRecyclerView;
    // Member variables for the adapter and RecyclerView
    private FavoritesAdapter mAdapter;
    private TextView mTextViewEmptyList;

    //private SQLiteDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_activity);

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
            getSupportActionBar().setTitle(R.string.title_bar_favorites_activity);


        }

        // Set the RecyclerView to its corresponding view
        favoriteRecyclerView = findViewById(R.id.list_favorite);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get all movies info from the database and save in a cursor
        //Cursor cursor = getAllMovies();

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new FavoritesAdapter(this);
        favoriteRecyclerView.setAdapter(mAdapter);

        mTextViewEmptyList = findViewById(R.id.empty_favorite_list);


        // Create a new ItemTouchHelper with a SimpleCallback that handles both LEFT and RIGHT swipe directions
        // Create an item touch helper to handle swiping items off the list

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping

                return false;
            }

            // Called when a user swipes left or right on a ViewHolder

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int id = (int) viewHolder.itemView.getTag();

                // Build appropriate uri with String row id appended
                String stringId = Integer.toString(id);
                Uri uri = MovieContract.MovieEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();

                //Delete a single row of data using a ContentResolver
                getContentResolver().delete(uri, null, null);

                // Restart the loader to re-query for all tasks after a deletion
                getSupportLoaderManager().restartLoader(FAVORITE_LOADER_ID,
                        null, FavoriteActivity.this);

            }


        }).attachToRecyclerView(favoriteRecyclerView);

        /*
         Ensure a loader is initialized and active. If the loader doesn't already exist, one is
         created, otherwise the last created loader is re-used.
         */
        getSupportLoaderManager().initLoader(FAVORITE_LOADER_ID, null, this);

    }


    /**
     * This method is called after this activity has been paused or restarted.
     * Often, this is after new data has been inserted through an AddTaskActivity,
     * so this restarts the loader to re-query the underlying data for any changes.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // re-queries for all tasks
        getSupportLoaderManager().restartLoader(FAVORITE_LOADER_ID, null, this);
    }


    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id         The ID whose loader is to be created.
     * @param loaderArgs Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle loaderArgs) {

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
                            null,
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
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update the data that the adapter uses to create ViewHolders
        mAdapter.swapCursor(data);
        if (mAdapter.getItemCount() == 0) {
            mTextViewEmptyList.setText(getString(R.string.empty_favorite_movies));
            mTextViewEmptyList.setVisibility(View.VISIBLE);
        } else {
            mTextViewEmptyList.setText("");
            mTextViewEmptyList.setVisibility(View.GONE);
        }


    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }
}
