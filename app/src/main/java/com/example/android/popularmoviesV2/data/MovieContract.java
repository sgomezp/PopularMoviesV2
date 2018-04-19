package com.example.android.popularmoviesV2.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sgomezp on 11/04/2018.
 */

public class MovieContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.android.popularmoviesV2";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_MOVIES = "favorites";


    /* MovieEntry is an inner class that defines the contents of the favorites table */

    public static final class MovieEntry implements BaseColumns {

        // MovieEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        // favorite table and column names
        // Table name
        public static final String TABLE_NAME = "favorites";

        //Columns names
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_THUMBNAIL = "poster_path";


    }
}
