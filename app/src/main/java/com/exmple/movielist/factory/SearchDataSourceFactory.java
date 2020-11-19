package com.exmple.movielist.factory;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.exmple.movielist.datasource.MovieDataSource;
import com.exmple.movielist.model.Search;

public class SearchDataSourceFactory extends DataSource.Factory {

     MutableLiveData<PageKeyedDataSource<String, Search>> serachLiveDataSource = new MutableLiveData<>();
     public static final String TAG = "test";



    @Override
    public DataSource create() {
        MovieDataSource movieDataSource = new MovieDataSource();
        serachLiveDataSource.postValue(movieDataSource);
       // Log.d(TAG, "DataSourceCreate: "+serachLiveDataSource.getValue().toString());
        return movieDataSource;

    }

    public MutableLiveData<PageKeyedDataSource<String, Search>> getSerachLiveDataSource() {
        return serachLiveDataSource;
    }
}
