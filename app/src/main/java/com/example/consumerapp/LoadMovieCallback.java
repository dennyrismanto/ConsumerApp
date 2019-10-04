package com.example.consumerapp;

import android.database.Cursor;

import java.util.ArrayList;

public interface LoadMovieCallback {
    void preExecute();
    void postExecute(Cursor movie);
}
