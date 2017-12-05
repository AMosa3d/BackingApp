package com.example.abdel.backingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdel.backingapp.Interfaces.StepsManagerInterface;
import com.example.abdel.backingapp.Models.Step;
import com.example.abdel.backingapp.R;

import java.util.List;

/**
 * Created by abdel on 11/22/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {

    Context context;
    StepsManagerInterface mStepsManagerInterface;
    List<Step> stepsList;

    public StepsAdapter(Context context, StepsManagerInterface mStepsManagerInterface) {
        this.context = context;
        this.mStepsManagerInterface = mStepsManagerInterface;
    }

    public void setStepsList(List<Step> stepsList) {
        this.stepsList = stepsList;
        notifyDataSetChanged();
    }

    @Override
    public StepsAdapter.StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_single_item,parent,false);

        return new StepsAdapter.StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.bind("Step " + position + " : " + stepsList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (stepsList != null)
            return stepsList.size();
        return 0;
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView stepNameTextView;
        public StepViewHolder(View itemView) {
            super(itemView);
            stepNameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mStepsManagerInterface.onStepClick(stepsList,getAdapterPosition());
        }

        void bind(String name)
        {
            stepNameTextView.setText(name);
        }
    }
}
