package com.example.abdel.backingapp.Database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by abdel on 12/1/2017.
 */

/*
Copyright 2014 Simon Vig Therkildsen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

@ContentProvider(authority = RecipesProvider.AUTHORITY, database = RecipesDatabase.class)
public final class RecipesProvider {

    public static final String AUTHORITY = "com.example.abdel.backingapp";

    @TableEndpoint(table = RecipesDatabase.RECIPES_TABLE)
    public static class Recipes
    {

        @ContentUri(path = "recipes", type = "vnd.android.cursor.dir/recipe", defaultSort = RecipeColumns._ID)
        public static final Uri RECIPES = Uri.parse("content://" + AUTHORITY + "/recipes");
    }

    @TableEndpoint(table = RecipesDatabase.INGREDIENTS_TABLE)
    public static class Ingredients
    {

        @ContentUri(path = "ingredients", type = "vnd.android.cursor.dir/ingredient", defaultSort = IngredientColumns._ID)
        public static final Uri INGREDIENTS = Uri.parse("content://" + AUTHORITY + "/ingredients");
    }

    @TableEndpoint(table = RecipesDatabase.STEPS_TABLE)
    public static class Steps
    {

        @ContentUri(path = "steps", type = "vnd.android.cursor.dir/step", defaultSort = StepColumns._ID)
        public static final Uri STEPS = Uri.parse("content://" + AUTHORITY + "/steps");
    }

}
