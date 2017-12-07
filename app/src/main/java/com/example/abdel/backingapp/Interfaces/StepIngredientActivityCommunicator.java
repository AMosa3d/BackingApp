package com.example.abdel.backingapp.Interfaces;

import com.example.abdel.backingapp.Models.Step;

import java.util.List;

/**
 * Created by abdel on 12/7/2017.
 */

public interface StepIngredientActivityCommunicator {
    void sendData(int sp,List<Step> sl);
}
