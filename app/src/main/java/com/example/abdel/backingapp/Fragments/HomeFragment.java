package com.example.abdel.backingapp.Fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdel.backingapp.Adapters.RecipeAdapter;
import com.example.abdel.backingapp.Interfaces.RecipesManagerInterface;
import com.example.abdel.backingapp.MainActivity;
import com.example.abdel.backingapp.Models.Recipe;
import com.example.abdel.backingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdel on 11/18/2017.
 */

public class HomeFragment extends Fragment {

    RecipeAdapter adapter;
    RecyclerView recyclerView;
    boolean tabletMode = false;
    List<Recipe> recipesList;
    final int GRID_NUMBER_OF_COLUMNS = 3;
    final String RECIPES_BUNDLE_KEY = "RecipesList";
    final String RECIPES_RECYCLER_POSITION_KEY = "RecyclerPosition";


    public HomeFragment()
    {

    }

    public boolean isTabletMode() {
        return tabletMode;
    }

    public void setRecipesList(List<Recipe> recipesList) {
        this.recipesList = recipesList;

        adapter.setRecipesList(recipesList);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);

        if (view.findViewById(R.id.recipes_recycler_grid) != null)
        {
            recyclerView = (RecyclerView) view.findViewById(R.id.recipes_recycler_grid);
            tabletMode = true;
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),GRID_NUMBER_OF_COLUMNS));
        }
        else {
            recyclerView = (RecyclerView) view.findViewById(R.id.recipes_recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        }

        adapter = new RecipeAdapter(getContext(), (RecipesManagerInterface) getActivity());
        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null)
        {
            recipesList = savedInstanceState.getParcelableArrayList(RECIPES_BUNDLE_KEY);
            adapter.setRecipesList(recipesList);
            recyclerView.setVerticalScrollbarPosition(savedInstanceState.getInt(RECIPES_RECYCLER_POSITION_KEY));
            adapter.notifyDataSetChanged();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(RECIPES_BUNDLE_KEY, (ArrayList<? extends Parcelable>) recipesList);
        outState.putInt(RECIPES_RECYCLER_POSITION_KEY,recyclerView.getVerticalScrollbarPosition());
        super.onSaveInstanceState(outState);
    }
}
