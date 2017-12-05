package com.example.abdel.backingapp.Widget;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.abdel.backingapp.Database.IngredientColumns;
import com.example.abdel.backingapp.Database.RecipesProvider;
import com.example.abdel.backingapp.Models.Ingredient;
import com.example.abdel.backingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdel on 12/2/2017.
 */

public class IngredientRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    List<Ingredient> ingredientList;


    public IngredientRemoteViewsFactory(Context context,Intent intent) {
        this.context = context;
        ingredientList = fillIngrdientList(
                context.getContentResolver().query(RecipesProvider.Ingredients.INGREDIENTS,null,null,null,null)
        );
    }

    ArrayList<Ingredient> fillIngrdientList(Cursor cursor)
    {
        ArrayList<Ingredient> ingredient = new ArrayList<>();
        int nameIndex = cursor.getColumnIndex(IngredientColumns.INGREDIENT_NAME);
        int quantityIndex = cursor.getColumnIndex(IngredientColumns.QUANTITY);
        int measureIndex = cursor.getColumnIndex(IngredientColumns.MEASURE);
        cursor.moveToFirst();

        try {
            do {
                ingredient.add(new Ingredient(cursor.getInt(quantityIndex),cursor.getString(measureIndex),cursor.getString(nameIndex)));
            } while (cursor.moveToNext());
        }catch (Exception e)
        {
            cursor.close();
        }

        return ingredient;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredientList == null)
            return 0;
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_single_item);

        remoteViews.setTextViewText(R.id.ingredient_widget_textView,ingredientList.get(position).getIngredient() + "\n" + ingredientList.get(position).getQuantity() + " " + ingredientList.get(position).getMeasure());

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return (1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
