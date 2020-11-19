package com.exmple.movielist.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.exmple.movielist.R;
import com.exmple.movielist.model.Search;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends PagedListAdapter<Search,MovieAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<Search> searchList;
    private List<Search> searchListAll;

    public static final String TAG = "test";



    public MovieAdapter(Context context, List<Search> searchList) {
        super(DIFF_CALLBACK);
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
            Log.d(TAG, "onBindViewHolder: " + searchList.size());

            holder.title.setText("Title : " + searchList.get(position).getTitle());
            holder.year.setText("Year : " + searchList.get(position).getYear());
            holder.type.setText("Type : " + searchList.get(position).getType());
            Glide.with(context).load(searchList.get(position).getPoster()).apply(RequestOptions.centerCropTransform()).into(holder.poster);
        }

    }

    @Override
    public int getItemCount() {
        if (searchList != null) {
            Log.d(TAG, "getItemCount: "+searchList.size());
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
            if (searchListAll!=null){
                searchList.addAll((List) results.values);
                notifyDataSetChanged();

            }else {
                Toast.makeText(context, "Empty data ", Toast.LENGTH_SHORT).show();
            }


        }
    };

    private static final DiffUtil.ItemCallback<Search> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Search>() {
                @Override
                public boolean areItemsTheSame(Search oldItem, Search newItem) {
                    Log.d(TAG, "areItemsTheSame: "+ oldItem.getTitle().toString());
                    return oldItem.getTitle().equals(newItem.getTitle());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(Search oldItem, @NotNull Search newItem) {
                    Log.d(TAG, "areContentsTheSame: "+newItem.getTitle().toString());
                    return oldItem.equals(newItem);
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
