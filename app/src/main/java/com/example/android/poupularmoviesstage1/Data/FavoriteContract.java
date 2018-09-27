package com.example.android.poupularmoviesstage1.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mostafa on 4/17/2018.
 */

public class FavoriteContract {

    public static final String AUTHORITY = "com.example.android.poupularmoviesstage1";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVORITE = "movies";

    public static final class FavoriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_FAVORITE).build();

        public static final String TABLE_NAME = "movies";
        public static final String MOVIE_ID = "id";
       // public static final String MOVIE_TITLE = "title";
    }
}
