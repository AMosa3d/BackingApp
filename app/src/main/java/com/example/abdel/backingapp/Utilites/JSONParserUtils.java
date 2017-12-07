package com.example.abdel.backingapp.Utilites;

import com.example.abdel.backingapp.Models.Ingredient;
import com.example.abdel.backingapp.Models.Recipe;
import com.example.abdel.backingapp.Models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdel on 11/18/2017.
 */

public final class JSONParserUtils {

    public static List<Recipe> getRecipesFromJSON(String JSONString) throws JSONException {
        final String RECIPE_ID = "id";
        final String RECIPE_NAME = "name";
        final String RECIPE_IMAGE = "image";
        final String RECIPE_INGREDIENTS = "ingredients";
        final String RECIPE_INGREDIENT = "ingredient";
        final String RECIPE_INGREDIENT_MEASURE = "measure";
        final String RECIPE_INGREDIENT_QUANTITY = "quantity";

        final String RECIPE_STEPS = "steps";
        final String RECIPE_STEP_SHORT_DESCRIPTION = "shortDescription";
        final String RECIPE_STEP_DESCRIPTION = "description";
        final String RECIPE_STEP_VIDEO_URL = "videoURL";

        List<Recipe> recipeList = new ArrayList<>();


        JSONArray allData = new JSONArray(JSONString);

        for (int i=0;i < allData.length();i++)
        {
            JSONObject currentRecipe = allData.getJSONObject(i);

            int id = currentRecipe.getInt(RECIPE_ID);
            String name = currentRecipe.getString(RECIPE_NAME);


            JSONArray currentIngredients = currentRecipe.getJSONArray(RECIPE_INGREDIENTS);

            List<Ingredient> ingredientsListObject = new ArrayList<>();

            for (int j = 0;j < currentIngredients.length();j++)
            {
                JSONObject currentIngredient = currentIngredients.getJSONObject(j);

                int quantity = currentIngredient.getInt(RECIPE_INGREDIENT_QUANTITY);
                String measure = currentIngredient.getString(RECIPE_INGREDIENT_MEASURE);
                String ingredient = currentIngredient.getString(RECIPE_INGREDIENT);

                ingredientsListObject.add(new Ingredient(quantity,measure,ingredient));
            }

            JSONArray currentSteps = currentRecipe.getJSONArray(RECIPE_STEPS);

            List<Step> stepsListObject = new ArrayList<>();

            for (int j = 0;j < currentSteps.length();j++)
            {
                JSONObject currentStep = currentSteps.getJSONObject(j);

                String shortDescription = currentStep.getString(RECIPE_STEP_SHORT_DESCRIPTION);
                String description = currentStep.getString(RECIPE_STEP_DESCRIPTION);
                String videoURL = currentStep.getString(RECIPE_STEP_VIDEO_URL);

                stepsListObject.add(new Step(shortDescription,description,videoURL));
            }

            String image = currentRecipe.getString(RECIPE_IMAGE);

            recipeList.add(new Recipe(id,name,ingredientsListObject,stepsListObject,image));
        }

        return recipeList;
    }
}
