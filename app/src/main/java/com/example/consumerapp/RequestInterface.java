package com.example.consumerapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {
    @GET("discover/movie")
    Call<ApiResponse> getNowPlaying(@Query("page") int page);
    @GET("discover/tv")
    Call<ApiResponse> getTvShow(@Query("page") int page);
}
