package com.exmple.movielist.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.exmple.movielist.R;
import com.exmple.movielist.model.Search;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<Search> searchList;
    private List<Search> searchListAll;

    public static final String TAG = "test";

    public MovieAdapter(Context context, List<Search> searchList) {
        this.context = context;
        this.searchList = searchList;


    }

    public void setMovieList(List<Search> searchList) {
        this.searchList = searchList;
        searchListAll = new ArrayList<>(searchList);
        Log.d(TAG, "MovieAdapter: " + searchListAll.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: " + searchList.size());

        if (searchList != null) {

            holder.title.setText("Title : " + searchList.get(position).getTitle());
            holder.year.setText("Year : " + searchList.get(position).getYear());
            holder.type.setText("Type : " + searchList.get(position).getType());
            Glide.with(context).load(searchList.get(position).getPoster()).apply(RequestOptions.centerCropTransform()).into(holder.poster);
        }

    }

    @Override
    public int getItemCount() {
        if (searchList != null) {
            return searchList.size();
        }
        return 0;

    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charsequence) {
            List<Search> filteredList = new ArrayList<>();
            //Log.d(TAG, "performFiltering: "+filteredList.size());

            if (charsequence.toString().isEmpty()) {
                Log.d(TAG, "performFiltering: " + searchListAll.size());
                filteredList.addAll(searchListAll);

            } else {
                String filterPattern = charsequence.toString().toLowerCase().trim();
                for (Search movie : searchListAll) {
                    Log.d(TAG, "performFiltering" + searchListAll.size());
                    if (movie.getTitle().contains(filterPattern)) {
                        filteredList.add(movie);

                    }

                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            searchList.clear();
            searchList.addAll((List) results.values);
            notifyDataSetChanged();


        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView title, year, type;


        public MyViewHolder(@NonNull View view) {
            super(view);

            poster = view.findViewById(R.id.poster);
            title = view.findViewById(R.id.title);
            year = view.findViewById(R.id.year);
            type = view.findViewById(R.id.type);


        }
    }
}
