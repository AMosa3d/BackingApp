package com.example.abdel.backingapp.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.abdel.backingapp.Adapters.IngredientsAdapter;
import com.example.abdel.backingapp.Adapters.StepsAdapter;
import com.example.abdel.backingapp.Database.IngredientColumns;
import com.example.abdel.backingapp.Database.RecipeColumns;
import com.example.abdel.backingapp.Database.RecipesProvider;
import com.example.abdel.backingapp.DetailActivity;
import com.example.abdel.backingapp.Interfaces.StepsManagerInterface;
import com.example.abdel.backingapp.Models.Ingredient;
import com.example.abdel.backingapp.Models.Recipe;
import com.example.abdel.backingapp.Models.Step;
import com.example.abdel.backingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdel on 11/22/2017.
 */

public class IngredientFragment extends Fragment {

    Recipe currentRecipe;
    RecyclerView ingredientsRecyclerView,stepsRecyclerView;
    NestedScrollView recipeNestedScrollView;
    IngredientsAdapter ingredientsAdapter;
    StepsAdapter stepsAdapter;
    final String CURRENT_RECIPE_BUNDLE_KEY = "CurrentRecipe";
    final String CURRENT_RECIPE_POSITION_KEY = "RecyclerPosition";
    boolean isFav = false;
    Menu favMenu;

    public IngredientFragment() {
    }

    public void setCurrentRecipe(Recipe currentRecipe) {
        this.currentRecipe = currentRecipe;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredient_fragment,container,false);

        setHasOptionsMenu(true);
        recipeNestedScrollView = (NestedScrollView) view.findViewById(R.id.nested_recipe_recycler_view);
        ingredientsRecyclerView = (RecyclerView) view.findViewById(R.id.ingredients_recycler);
        stepsRecyclerView = (RecyclerView) view.findViewById(R.id.steps_recycler);

        LinearLayoutManager stepsLinearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager ingredientsLinearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        if (savedInstanceState != null)
        {
            recipeNestedScrollView.setVerticalScrollbarPosition(savedInstanceState.getInt(CURRENT_RECIPE_POSITION_KEY));
            currentRecipe = savedInstanceState.getParcelable(CURRENT_RECIPE_BUNDLE_KEY);
        }

        ingredientsAdapter = new IngredientsAdapter(getContext());
        ingredientsRecyclerView.setLayoutManager(ingredientsLinearLayoutManager);
        ingredientsAdapter.setIngredientsList(currentRecipe.getIngredientsList());
        ingredientsAdapter.notifyDataSetChanged();
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        stepsAdapter = new StepsAdapter(getContext(), (StepsManagerInterface) getActivity());
        stepsRecyclerView.setLayoutManager(stepsLinearLayoutManager);
        stepsAdapter.setStepsList(currentRecipe.getStepsList());
        stepsAdapter.notifyDataSetChanged();
        stepsRecyclerView.setAdapter(stepsAdapter);


        initializeBooleanFromDatabase();

        return view;
    }

    void initializeBooleanFromDatabase()
    {
        Cursor cursor = getContext().getContentResolver().query(RecipesProvider.Recipes.RECIPES,null, RecipeColumns.RECIPE_NAME + " = ?",new String[]{currentRecipe.getName()},null);

        if (cursor.getCount() == 1)
            isFav = true;
        else
            isFav = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(CURRENT_RECIPE_BUNDLE_KEY, currentRecipe);
        outState.putInt(CURRENT_RECIPE_POSITION_KEY,recipeNestedScrollView.getVerticalScrollbarPosition());
        super.onSaveInstanceState(outState);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fav_menu,menu);
        favMenu = menu;

        if (isFav)
            favMenu.getItem(0).setIcon(R.drawable.yellow_star);
        else
            favMenu.getItem(0).setIcon(R.drawable.black_star);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.fav_menu_item)
        {
            if (!isFav)
            {
                FavoriteRecipe();
                isFav = true;
                favMenu.getItem(0).setIcon(R.drawable.yellow_star);
            }
            else
            {
                UnFavoriteAll();
                isFav = false;
                favMenu.getItem(0).setIcon(R.drawable.black_star);
            }
            return true;
        }
        if (id == android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(getActivity());
            return true;
        }

        return false;
    }

    void FavoriteRecipe()
    {
        UnFavoriteAll();


        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeColumns.RECIPE_NAME,currentRecipe.getName());
        getContext().getContentResolver().insert(RecipesProvider.Recipes.RECIPES,contentValues);


        List<Ingredient> ingredientList = currentRecipe.getIngredientsList();
        for (int i=0;i<ingredientList.size();i++)
        {
            contentValues = new ContentValues();
            contentValues.put(IngredientColumns.INGREDIENT_NAME,ingredientList.get(i).getIngredient());
            contentValues.put(IngredientColumns.QUANTITY,ingredientList.get(i).getQuantity());
            contentValues.put(IngredientColumns.MEASURE,ingredientList.get(i).getMeasure());
            getContext().getContentResolver().insert(RecipesProvider.Ingredients.INGREDIENTS,contentValues);
        }
    }

    void UnFavoriteAll()
    {
        getContext().getContentResolver().delete(RecipesProvider.Recipes.RECIPES,null,null);
        getContext().getContentResolver().delete(RecipesProvider.Ingredients.INGREDIENTS,null,null);
    }
}
