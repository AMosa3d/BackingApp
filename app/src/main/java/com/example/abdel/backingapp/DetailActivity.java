package com.example.abdel.backingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.example.abdel.backingapp.Database.RecipeColumns;
import com.example.abdel.backingapp.Database.RecipesProvider;
import com.example.abdel.backingapp.Fragments.IngredientFragment;
import com.example.abdel.backingapp.Fragments.StepFragment;
import com.example.abdel.backingapp.Models.Step;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    StepFragment mStepFragment;
    int currentStepPosition;
    List<Step> stepsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mStepFragment = new StepFragment();
        Intent intent = getIntent();

        if (intent != null)
        {
            currentStepPosition = intent.getIntExtra(getString(R.string.step_position),0);
            stepsList = intent.getExtras().getParcelableArrayList(getString(R.string.step_bundle));
        }




        if (savedInstanceState == null) {

            mStepFragment.setStepsList(stepsList);
            mStepFragment.setCurrentStep(currentStepPosition);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_container, mStepFragment)
                    .commit();
        }
    }
}
