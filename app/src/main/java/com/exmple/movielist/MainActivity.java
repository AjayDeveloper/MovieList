package com.exmple.movielist;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exmple.movielist.adapter.MovieAdapter;
import com.exmple.movielist.model.Search;
import com.exmple.movielist.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter recyclerAdapter;
    private List<Search> searchList;
    public static final String TAG = "test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchList = new ArrayList<Search>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        SearchViewModel searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        recyclerAdapter = new MovieAdapter(this, searchList);
        searchViewModel.searchPageList.observe(this, new Observer<PagedList<Search>>() {
            @Override
            public void onChanged(PagedList<Search> searches) {
                Log.d(TAG, "onChanged: "+searches.toString());

                recyclerAdapter.submitList(searches);

            }
        });

        recyclerView.setAdapter(recyclerAdapter);


      /*  ApiClient.getInstance().getApi().getSearch("Batman", 75906267)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.isSuccessful()) {
                            Log.d("test", "onResponse: " + response.body().getSearch());
                            searchList.addAll(response.body().getSearch());
                            recyclerAdapter.setMovieList(searchList);

                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        Log.d("TAG", "Response = " + t.toString());
                    }
                });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}