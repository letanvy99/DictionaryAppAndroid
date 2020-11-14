package com.example.projectgroup.model;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectgroup.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {
    public static ArrayList<AnhViet> dictionary;
    public static Context mContext;
    public ArrayList<AnhViet> listAllDictionary;

    public MyAdapter(Context context, ArrayList<AnhViet> dictionary) {
        this.mContext = context;
        this.dictionary = dictionary;
        this.listAllDictionary = new ArrayList<>(dictionary);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dictionary, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textViewDictionary.setText(dictionary.get(position).getWord().toString());
        holder.textViewDefinition.setText(Html.fromHtml(dictionary.get(position).getContent().toString()));

    }

    @Override
    public int getItemCount() {
        return dictionary.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<AnhViet> filtered = new ArrayList<>();
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(mContext);
            databaseAccess.open();
            if (constraint.toString().isEmpty()) {
                filtered.addAll(listAllDictionary);
            } else {
                ArrayList<AnhViet> listsearch = databaseAccess.getWords(constraint.toString());
                if (listsearch != null) {
                    databaseAccess.close();
                    for (int i = 0; i < listsearch.size(); i++) {
                        filtered.add(listsearch.get(i));
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filtered;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dictionary.clear();
            dictionary.addAll((Collection<? extends AnhViet>) results.values);
            notifyDataSetChanged();

        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDictionary;
        public TextView textViewDefinition;
        public View view;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            textViewDictionary = view.findViewById(R.id.tv_dictionary);
            textViewDefinition = view.findViewById(R.id.tv_definition);
        }
    }
}

