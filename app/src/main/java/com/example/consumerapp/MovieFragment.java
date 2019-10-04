package com.example.consumerapp;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private MovieAdapter adapter = new MovieAdapter();
    private ArrayList<Movie> movies = new ArrayList<>();
    private ProgressBar loading;
    private RecyclerView rvCategory;
    public static final String KEY_MOVIES = "saveMovies";
    private static final String TAG = "TAG";
    private int someStateValue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"Fragment onCreateView method is called.");
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rvCategory = view.findViewById(R.id.rv_category);
        rvCategory.setAdapter(adapter);
        loading = view.findViewById(R.id.progressBar);
        showLoading(true);

        MovieViewModel model = ViewModelProviders.of(this).get(MovieViewModel.class);
        model.getUsers().observe(this, this.getMov);

    }

    private Observer<ArrayList<Movie>> getMov = new Observer<ArrayList<Movie>>() {

        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                adapter.setMovies(movies);
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
