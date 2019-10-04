package com.example.consumerapp;

import java.util.ArrayList;

public interface LoadTvMovieCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> movie);
}
