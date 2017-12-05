package com.example.abdel.backingapp.Interfaces;

import com.example.abdel.backingapp.Models.Recipe;

import java.util.List;

/**
 * Created by abdel on 11/19/2017.
 */

public interface RecipesManagerInterface {
    void sendData(List<Recipe> recipesList);
    void handleProgressBar(int visibility);
    void showError();
    void onRecipeClick(Recipe recipe);
}
