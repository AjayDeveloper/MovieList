package com.exmple.movielist.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.exmple.movielist.datasource.MovieDataSource;
import com.exmple.movielist.factory.SearchDataSourceFactory;
import com.exmple.movielist.model.Search;

public class SearchViewModel extends ViewModel {

    public static final String TAG ="test";

     public LiveData<PagedList<Search>> searchPageList;
     public LiveData<PageKeyedDataSource<String,Search>> liveDataSource;

    public SearchViewModel() {

        SearchDataSourceFactory searchDataSourceFactory = new SearchDataSourceFactory();
        liveDataSource = searchDataSourceFactory.getSerachLiveDataSource();
       //Log.d(TAG, "liveDataSource: "+liveDataSource.getValue().toString());
        PagedList.Config config =
                                (new PagedList.Config.Builder())
                                 .setEnablePlaceholders(false)
                                 .setPageSize(393)
                                 .build();
        Log.d(TAG, "SearchViewModel: "+config.toString());
        searchPageList = (new LivePagedListBuilder(searchDataSourceFactory,config)).build();
    }
}
