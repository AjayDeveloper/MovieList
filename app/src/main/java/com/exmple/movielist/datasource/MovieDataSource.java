package com.exmple.movielist.datasource;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.exmple.movielist.model.Movie;
import com.exmple.movielist.model.Search;
import com.exmple.movielist.util.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<String, Search> {

    //we will start from the first page which is Batman
    private static final String FIRST_PAGE = "batman";
    public static final int apiKey = 75906267;
    private static final String TAG = "test";
    Activity activity;
    private Context context = activity.getApplicationContext();


    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, Search> callback) {
        Log.d(TAG, "loadInitial: ");

        ApiClient.getInstance()
                .getApi()
                .getSearch("batman", apiKey)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {

                        if (response.body() != null) {
                            Log.d(TAG, "onResponse: " + response.body().getSearch().toString());

                            callback.onResult(response.body().getSearch(), null, "batman");

                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        Toast.makeText(activity, "Error while retriving the data" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Search> callback) {

        ApiClient.getInstance().getApi().getSearch(params.key, apiKey).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                String key = response.body().getSearch().get(0).getTitle() ;

                if (response.body() != null) {
                    callback.onResult(response.body().getSearch(), key);
                }


            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(activity, "Error while retriving the data" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Search> callback) {

        Log.d(TAG, "loadAfter: ");

        ApiClient.getInstance().getApi()
                .getSearch(params.key, apiKey)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        String key = response.body().getSearch().get(0).getTitle();
                        if (response.body() != null) {
                            callback.onResult(response.body().getSearch(), key);
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                        Toast.makeText(activity, "Error while retriving the data" + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }
}
