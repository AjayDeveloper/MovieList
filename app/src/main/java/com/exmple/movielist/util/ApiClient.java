package com.exmple.movielist.util;


import com.exmple.movielist.api.Api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static  ApiClient mInstance ;

    public static String BASE_URL = "http://www.omdbapi.com/";
    private static Retrofit retrofit;

    private  ApiClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static synchronized ApiClient getInstance(){
          if(mInstance==null){
              mInstance = new  ApiClient();

          }

        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
