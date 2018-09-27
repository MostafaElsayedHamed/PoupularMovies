package com.example.android.poupularmoviesstage1.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.poupularmoviesstage1.Data.FavoriteContract;

/**
 * Created by mostafa on 4/15/2018.
 */

public class FavoriteData extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorite.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 3;

    // Constructor
    public FavoriteData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteContract.FavoriteEntry.TABLE_NAME + " (" +
                FavoriteContract.FavoriteEntry.MOVIE_ID + " INTEGER PRIMARY KEY "  +
                "); ";
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(db);

    }
}
