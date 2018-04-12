package com.example.android.popularmoviesV2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.android.popularmoviesV2.data.MovieContract;
import com.example.android.popularmoviesV2.data.MovieDbHelper;
import com.example.android.popularmoviesV2.utils.FavoritesAdapter;

public class FavoriteActivity extends AppCompatActivity {

    private static final String TAG = FavoriteActivity.class.getSimpleName();

    private FavoritesAdapter mAdapter;
    private SQLiteDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_activity);

        // Set local attributes to corresponding views
        RecyclerView favoriteRecyclerView;
        favoriteRecyclerView = findViewById(R.id.list_favorite);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a DB helper (this will create the DB if run for the first time)
        MovieDbHelper dbHelper = new MovieDbHelper(this);

        // Keep a reference to the mDb until paused or killed. Get a writable database
        // because you will be adding favorites movies
        mDb = dbHelper.getWritableDatabase();

        // Get all movies info from the database and save in a cursor
        Cursor cursor = getAllMovies();

        // Create an adapter for that cursor to display the data
        mAdapter = new FavoritesAdapter(this, cursor);

        // Link the adapter to the RecyclerView
        favoriteRecyclerView.setAdapter(mAdapter);


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

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();

                //call removeGuest and pass through that id
                removeMovies(id);
                //call swapCursor on mAdapter passing in getAllMovies() as the argument
                //update the list
                mAdapter.swapCursor(getAllMovies());

            }
            //attach the ItemTouchHelper to the waitlistRecyclerView

        }).attachToRecyclerView(favoriteRecyclerView);


    }

    // This method is called when user clicks on the Add to waitlist button
    public void addToFavorites(View view) {

    }


    private Cursor getAllMovies() {
        return mDb.query(
                MovieContract.MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MovieContract.MovieEntry.COLUMN_MOVIE_TITLE
        );
    }

    private boolean removeMovies(long id) {
        //call mDb.delete to pass in the TABLE_NAME and the condition that MovieEntry._ID equals id
        return mDb.delete(MovieContract.MovieEntry.TABLE_NAME,
                MovieContract.MovieEntry._ID + "=" + id, null) > 0;
    }
}
