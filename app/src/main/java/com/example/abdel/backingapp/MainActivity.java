package com.example.abdel.backingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.abdel.backingapp.Adapters.RecipeAdapter;
import com.example.abdel.backingapp.AsyncTasks.RecipeAsyncTask;
import com.example.abdel.backingapp.Fragments.HomeFragment;
import com.example.abdel.backingapp.Interfaces.RecipesManagerInterface;
import com.example.abdel.backingapp.Models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipesManagerInterface {

    HomeFragment mHomeFragment = new HomeFragment();
    List<Recipe> recipesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            new RecipeAsyncTask(this).execute();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.home_container, mHomeFragment)
                    .commit();
        }

    }

    @Override
    public void sendData(List<Recipe> recipesList) {
        this.recipesList = recipesList;
        mHomeFragment.setRecipesList(recipesList);
    }

    @Override
    public void handleProgressBar(int visibility) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, IngredientActivity.class);
        intent.putExtra(getString(R.string.recipe_key),recipe);
        intent.putExtra(getString(R.string.tablet_mode),mHomeFragment.isTabletMode());
        startActivity(intent);
    }

}
