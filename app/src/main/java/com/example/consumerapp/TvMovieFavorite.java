package com.example.consumerapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TvMovieFavorite extends AppCompatActivity implements View.OnClickListener, LoadTvMovieCallback {
    private TvMovieAdapter adapter;
    private ArrayList<Movie> movies = new ArrayList<>();
    private ProgressBar loading;
    private RecyclerView rvCategory1;
    private TvMovieHelper tvMovieHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_movie_favorite);

        rvCategory1 = findViewById(R.id.rv_category1);
        rvCategory1.setLayoutManager(new LinearLayoutManager(this));
        rvCategory1.setHasFixedSize(true);
        loading = findViewById(R.id.progressBar);

        tvMovieHelper = TvMovieHelper.getInstance(getApplicationContext());
        tvMovieHelper.open();

        adapter = new TvMovieAdapter();
        rvCategory1.setAdapter(adapter);


        if (savedInstanceState == null) {
            new TvMovieFavorite.LoadMovieAsync(tvMovieHelper, this).execute();
        } else {
            ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (movies != null) {
                adapter.setMovies(movies);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getMovies());
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void preExecute() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<Movie> movie) {
        loading.setVisibility(View.GONE);
        adapter.setMovies(movie);
    }




    class LoadMovieAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {
        private final WeakReference<TvMovieHelper> weaktvMovieHelper;
        private final WeakReference<LoadTvMovieCallback> weakCallback;

        private LoadMovieAsync(TvMovieHelper tvMovieHelper, LoadTvMovieCallback callback) {
            weaktvMovieHelper = new WeakReference<TvMovieHelper>(tvMovieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return weaktvMovieHelper.get().getAllNotes();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movie) {
            super.onPostExecute(movie);
            weakCallback.get().postExecute(movie);
        }
    }
}
