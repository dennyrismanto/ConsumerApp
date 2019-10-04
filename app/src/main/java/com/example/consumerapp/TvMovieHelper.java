package com.example.consumerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.consumerapp.DatabaseMovie.NoteColumns.FIRSTAIRDATE;
import static com.example.consumerapp.DatabaseMovie.NoteColumns.ORIGINAL_NAME;
import static com.example.consumerapp.DatabaseMovie.NoteColumns.OVERVIEW;
import static com.example.consumerapp.DatabaseMovie.NoteColumns.POSTERPATH;
import static com.example.consumerapp.DatabaseMovie.TABLE_NOTE_TVMOVIE;

public class TvMovieHelper  {
    private static final String DATABASE_TABLE = TABLE_NOTE_TVMOVIE;
    private static TvMovieHelper INSTANCE;
    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase database;

    private TvMovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static TvMovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvMovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie> getAllNotes() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setOriginalName(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_NAME)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setFirstAirDate(cursor.getString(cursor.getColumnIndexOrThrow(FIRSTAIRDATE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTERPATH)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor cekData2(String title){
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                ORIGINAL_NAME + "=?",
                new String[]{title},
                null,
                null,
                null,
                null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public long insertNote(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(ORIGINAL_NAME, movie.getOriginalName());
        args.put(OVERVIEW, movie.getOverview());
        args.put(FIRSTAIRDATE, movie.getFirstAirDate());
        args.put(POSTERPATH, movie.getPosterPath());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int updateNote(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(ORIGINAL_NAME, movie.getTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(FIRSTAIRDATE, movie.getReleaseDate());
        args.put(POSTERPATH, movie.getPosterPath());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + movie.getId() + "'", null);
    }

    public int deleteNote(int id) {
        return database.delete(TABLE_NOTE_TVMOVIE, _ID + " = '" + id + "'", null);
    }
}
