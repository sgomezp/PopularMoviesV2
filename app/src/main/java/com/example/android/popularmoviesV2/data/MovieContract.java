package com.example.android.popularmoviesV2.data;

import android.provider.BaseColumns;

/**
 * Created by sgomezp on 11/04/2018.
 */

public class MovieContract {

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_THUMBNAIL = "poster_path";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";


    }
}
