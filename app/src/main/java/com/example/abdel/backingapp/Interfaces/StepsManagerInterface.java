package com.example.abdel.backingapp.Interfaces;

import com.example.abdel.backingapp.Models.Step;

import java.util.List;

/**
 * Created by abdel on 11/22/2017.
 */

public interface StepsManagerInterface {
    void onStepClick(List<Step> stepsList, int stepPosition);
}
