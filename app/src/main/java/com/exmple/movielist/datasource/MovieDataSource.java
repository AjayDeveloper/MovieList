package com.exmple.movielist.datasource;

import android.util.Log;

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


    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, Search> callback) {
        Log.d(TAG, "loadInitial: ");

        ApiClient.getInstance()
                .getApi()
                .getSearch(FIRST_PAGE, apiKey)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {

                        if (response.body() != null) {
                            Log.d(TAG, "onResponseOkGoogle: " + response.body());
                            for(int i =0 ;i<response.body().getSearch().size();i++){
                                callback.onResult(response.body().getSearch(), null, response.body().getSearch().get(i).getTitle());
                            }



                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        //      Toast.makeText(activity, "Error while retriving the data" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Search> callback) {
        Log.d(TAG, "loadBefore: "+params.key);
        ApiClient.getInstance().getApi().getSearch(params.key, apiKey).enqueue(new Callback<Movie>() {

            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.d(TAG, "loadBefore: "+params.key);

                    String key = response.body().getSearch().get(0).getTitle();
                    if (response.body() != null) {
                        Log.d(TAG, "onResponseOkMoogle: " + response.body());
                        callback.onResult(response.body().getSearch(), key);
                    }


                }



            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                // Toast.makeText(activity, "Error while retriving the data" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
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
                        for (int i = 0; i < response.body().getSearch().size(); i++) {
                            String key = response.body().getSearch().get(i).getTitle();

                            if (response.body() != null) {
                                Log.d(TAG, "onResponseOkSoogle: " + response.body());
                                callback.onResult(response.body().getSearch(), key);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                        //   Toast.makeText(activity, "Error while retriving the data" + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }
}
