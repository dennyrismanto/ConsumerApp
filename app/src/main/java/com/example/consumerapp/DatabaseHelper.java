package com.example.consumerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbnoteapp";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_NOTE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseMovie.TABLE_NOTE_MOVIE,
            DatabaseMovie.NoteColumns._ID,
            DatabaseMovie.NoteColumns.ORIGINAL_TITLE,
            DatabaseMovie.NoteColumns.OVERVIEW,
            DatabaseMovie.NoteColumns.RELEASEDATE,
            DatabaseMovie.NoteColumns.POSTERPATH
    );
    private static final String SQL_CREATE_TABLE_NOTE_TVMOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseMovie.TABLE_NOTE_TVMOVIE,
            DatabaseMovie.NoteColumns._ID,
            DatabaseMovie.NoteColumns.ORIGINAL_NAME,
            DatabaseMovie.NoteColumns.OVERVIEW,
            DatabaseMovie.NoteColumns.FIRSTAIRDATE,
            DatabaseMovie.NoteColumns.POSTERPATH
    );
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_NOTE_TVMOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseMovie.TABLE_NOTE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseMovie.TABLE_NOTE_TVMOVIE);
        onCreate(db);
    }
}
