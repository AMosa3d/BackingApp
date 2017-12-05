package com.example.abdel.backingapp.AsyncTasks;

import android.os.AsyncTask;
import android.view.View;

import com.example.abdel.backingapp.Interfaces.RecipesManagerInterface;
import com.example.abdel.backingapp.Models.Recipe;
import com.example.abdel.backingapp.Utilites.JSONParserUtils;
import com.example.abdel.backingapp.Utilites.NetworkUtils;

import java.net.URL;
import java.util.List;

/**
 * Created by abdel on 11/18/2017.
 */

public class RecipeAsyncTask extends AsyncTask<Void,Void,List<Recipe>> {

    RecipesManagerInterface mRecipesManagerInterface;

    public RecipeAsyncTask(RecipesManagerInterface mRecipesManagerInterface) {
        this.mRecipesManagerInterface = mRecipesManagerInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mRecipesManagerInterface.handleProgressBar(View.VISIBLE);
    }

    @Override
    protected List<Recipe> doInBackground(Void... params) {

        URL recipesURL = NetworkUtils.buildURL();

        try {
            String recipesJSONString = NetworkUtils.getDataFromURL(recipesURL);

            List<Recipe> recipesList = JSONParserUtils.getRecipesFromJSON(recipesJSONString);
            return recipesList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {
        mRecipesManagerInterface.handleProgressBar(View.INVISIBLE);

        if(recipes == null)
            mRecipesManagerInterface.showError();
        else
            mRecipesManagerInterface.sendData(recipes);
    }
}
