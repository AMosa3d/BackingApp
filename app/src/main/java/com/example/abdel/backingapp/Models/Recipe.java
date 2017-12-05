package com.example.abdel.backingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdel on 11/18/2017.
 */

public class Recipe implements Parcelable {

    int id;
    String name;
    List<Ingredient> ingredientsList = new ArrayList<>();
    List<Step> stepsList = new ArrayList<>();

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public List<Step> getStepsList() {
        return stepsList;
    }

    public void setStepsList(List<Step> stepsList) {
        this.stepsList = stepsList;
    }

    public Recipe(int id, String name, List<Ingredient> ingredientsList, List<Step> stepsList) {
        this.id = id;
        this.name = name;
        this.ingredientsList = ingredientsList;
        this.stepsList = stepsList;
    }

    public Recipe(Parcel p)
    {
        id = p.readInt();
        name = p.readString();
        p.readTypedList(ingredientsList, Ingredient.CREATOR);
        p.readTypedList(stepsList, Step.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredientsList);
        dest.writeTypedList(stepsList);
    }


    @Override
    public int describeContents() {
        return 0;
    }


}
