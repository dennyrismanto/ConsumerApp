package com.example.consumerapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> users;

    public LiveData<ArrayList<Movie>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<ArrayList<Movie>>();
            loadUsers();
        }
        return users;
    }

    private void loadUsers() {
        final RequestInterface requestInterface = MovieClient.Retrofit().create(RequestInterface.class);
        requestInterface.getNowPlaying(1).enqueue(new Callback<ApiResponse>() {

            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d("Data", response.body().getResults().get(0).getTitle());
                users.postValue(response.body().getResults());

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }

        });
    }
}
