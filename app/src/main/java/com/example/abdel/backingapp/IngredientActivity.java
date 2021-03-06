package com.example.abdel.backingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.abdel.backingapp.Fragments.IngredientFragment;
import com.example.abdel.backingapp.Fragments.StepFragment;
import com.example.abdel.backingapp.Interfaces.StepsManagerInterface;
import com.example.abdel.backingapp.Interfaces.StepIngredientActivityCommunicator;
import com.example.abdel.backingapp.Models.Recipe;
import com.example.abdel.backingapp.Models.Step;

import java.util.ArrayList;
import java.util.List;

public class IngredientActivity extends AppCompatActivity implements StepsManagerInterface {

    Recipe currentRecipe;
    IngredientFragment mIngredientFragment;
    StepFragment mStepFragment;
    public StepIngredientActivityCommunicator stepIngredientActivityCommunicator;
    boolean tabletMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        mIngredientFragment = new IngredientFragment();
        Intent intent = getIntent();

        if (intent != null) {
            currentRecipe = intent.getParcelableExtra(getString(R.string.recipe_key));
            tabletMode = intent.getBooleanExtra(getString(R.string.tablet_mode),false);
        }
        mIngredientFragment.setCurrentRecipe(currentRecipe);

        if (findViewById(R.id.step_container) != null)
        {
            tabletMode = true;
            mStepFragment = new StepFragment();
            mStepFragment.setStepsList(currentRecipe.getStepsList());

            if (savedInstanceState == null) {

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.step_container, mStepFragment)
                        .commit();
            }
        }


        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingredient_container, mIngredientFragment)
                    .commit();
        }
    }

    @Override
    public void onStepClick(List<Step> stepsList, int stepPosition) {

        if (!tabletMode) {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(getString(R.string.step_position), stepPosition);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getString(R.string.step_bundle), (ArrayList<? extends Parcelable>) stepsList);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else
        {
            if (stepIngredientActivityCommunicator != null)
                stepIngredientActivityCommunicator.sendData(stepPosition,stepsList);
            //mStepFragment.setCurrentStep(stepPosition);
            //mStepFragment.setStepsList(stepsList);
           // mStepFragment.setDataToUI();
        }
    }
}
