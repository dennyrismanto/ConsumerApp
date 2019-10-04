package com.example.consumerapp;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvMovieFragment extends Fragment {
    private TvMovieAdapter adapter = new TvMovieAdapter();
    private ArrayList<Movie> moviestv = new ArrayList<>();
    private ProgressBar loading;
    public RecyclerView rvCategory1;
    public static final String KEY_tv_MOVIES = "saveMovies";
    private static final String TAG = "TAG";
    private int someStateValue;

    public TvMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"Fragment onCreateView method is called.");
        return inflater.inflate(R.layout.fragment_tv_movie, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        rvCategory1 = view.findViewById(R.id.rv_category1);
        rvCategory1.setAdapter(adapter);
        loading = view.findViewById(R.id.progressBar);

        TvMovieViewModel modeltv = ViewModelProviders.of(this).get(TvMovieViewModel.class);
        modeltv.getUsers().observe(this, this.getTV);
    }

    private Observer<ArrayList<Movie>> getTV = new Observer<ArrayList<Movie>>() {

        @Override
        public void onChanged(ArrayList<Movie> moviestv) {
            if (moviestv != null) {
                adapter.setMovies(moviestv);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }
    }

}