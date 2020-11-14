package com.exmple.movielist.api;

import com.exmple.movielist.model.Movie;
import com.exmple.movielist.model.Search;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("/?")
    Call<Movie> getSearch(@Query("s") String s,@Query("apikey") int apikey);

}
