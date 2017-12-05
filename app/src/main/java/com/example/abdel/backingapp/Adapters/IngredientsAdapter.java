package com.example.abdel.backingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdel.backingapp.Interfaces.StepsManagerInterface;
import com.example.abdel.backingapp.Models.Ingredient;
import com.example.abdel.backingapp.Models.Step;
import com.example.abdel.backingapp.R;

import java.util.List;

/**
 * Created by abdel on 11/25/2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    Context context;
    List<Ingredient> ingredientsList;

    public IngredientsAdapter(Context context) {
        this.context = context;
    }

    public void setIngredientsList(List<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_single_item,parent,false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.IngredientViewHolder holder, int position) {
        Ingredient currentIngredient = ingredientsList.get(position);
        holder.bind(currentIngredient.getIngredient(),currentIngredient.getQuantity() + " " + currentIngredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        if (ingredientsList != null)
            return ingredientsList.size();
        return 0;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder
    {
        TextView ingredientNameTextView,quantityAndMeasureTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientNameTextView = (TextView) itemView.findViewById(R.id.ingredient_textView);
            quantityAndMeasureTextView = (TextView) itemView.findViewById(R.id.quantity_and_measure_textView);
        }

        void bind(String ingredient, String quantityAndMeasureString)
        {
            ingredientNameTextView.setText(ingredient);
            quantityAndMeasureTextView.setText(quantityAndMeasureString);
        }
    }

}
