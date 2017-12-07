package com.example.abdel.backingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdel.backingapp.Interfaces.RecipesManagerInterface;
import com.example.abdel.backingapp.Models.Recipe;
import com.example.abdel.backingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by abdel on 11/19/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    Context context;
    RecipesManagerInterface mRecipesManagerInterface;
    List<Recipe> recipesList;

    public RecipeAdapter(Context context, RecipesManagerInterface mRecipeAdapterInterface) {
        this.context = context;
        this.mRecipesManagerInterface = mRecipeAdapterInterface;
    }

    public void setRecipesList(List<Recipe> recipesList) {
        this.recipesList = recipesList;
        notifyDataSetChanged();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_single_item,parent,false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(recipesList.get(position).getName(),recipesList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        if (recipesList != null)
            return recipesList.size();
        return 0;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView recipeNameTextView;
        ImageView recipeImageView;
        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeNameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
            recipeImageView = (ImageView) itemView.findViewById(R.id.recipe_imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mRecipesManagerInterface.onRecipeClick(recipesList.get(getAdapterPosition()));
        }

        void bind(String name,String image)
        {
            if (image.equals("") || image == null)
            {
                recipeImageView.setVisibility(View.GONE);
            }
            else
                Picasso.with(context).load(image).resize(300,300).into(recipeImageView);
            recipeNameTextView.setText(name);
        }
    }
}
