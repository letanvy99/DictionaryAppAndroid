package com.example.projectgroup.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.example.projectgroup.R;
import com.example.projectgroup.model.AnhViet;
import com.example.projectgroup.model.DatabaseAccess;
import com.example.projectgroup.model.MyAdapter;

import java.util.ArrayList;


public class ListsFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    public ArrayList<AnhViet> dictionary;
    public SearchView searchView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lists, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_ListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dictionary = new ArrayList<AnhViet>();
        searchView = view.findViewById(R.id.sv_anhviet);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.rv_ListView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
        databaseAccess.open();
        dictionary = databaseAccess.getWords();
        databaseAccess.close();
        myAdapter = new MyAdapter(getActivity(), dictionary);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


}