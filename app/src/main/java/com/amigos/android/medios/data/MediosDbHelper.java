package com.amigos.android.medios.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.amigos.android.medios.data.MediosContract.*;

/**
 * Created by Arijit on 15-07-2017.
 */

public class MediosDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "music.db";
    private static final int DATABASE_VERSION = 1;

    public MediosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the music table
        String SQL_CREATE_MUSIC_TABLE =  "CREATE TABLE " + MediosEntry.TABLE_NAME + " ("
                + MediosEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MediosEntry.COLUMN_MUSIC_TITLE + " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE, "
                + MediosEntry.COLUMN_MUSIC_PATH + " TEXT NOT NULL, "
                + MediosEntry.COLUMN_MUSIC_ALBUM + " TEXT, "
                + MediosEntry.COLUMN_MUSIC_ARTIST + " TEXT, "
                + MediosEntry.COLUMN_MUSIC_GENRE + " TEXT, "
                + MediosEntry.COLUMN_MUSIC_YEAR + " INTEGER, "
                + MediosEntry.COLUMN_MUSIC_DURATION + " INTEGER);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_MUSIC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
