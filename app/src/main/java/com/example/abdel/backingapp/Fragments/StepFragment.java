package com.example.abdel.backingapp.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdel.backingapp.Adapters.IngredientsAdapter;
import com.example.abdel.backingapp.Adapters.RecipeAdapter;
import com.example.abdel.backingapp.AsyncTasks.RecipeAsyncTask;
import com.example.abdel.backingapp.Models.Step;
import com.example.abdel.backingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdel on 11/25/2017.
 */

public class StepFragment extends Fragment {

    TextView shortDescriptionTextView,descriptionTextView;
    ImageView nextImageView,prevImageView;
    SimpleExoPlayerView stepExoPlayerView;
    SimpleExoPlayer stepExoPlayer;

    int currentStep;
    List<Step> stepsList;

    final String CURRENT_STEP_KEY = "CurrentStep";
    final String CURRENT_RECIPE_STEPS_LIST_KEY = "StepsList";

    public StepFragment() {
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public void setStepsList(List<Step> stepsList) {
        this.stepsList = stepsList;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_fragment,container,false);

        if (savedInstanceState != null)
        {
            currentStep = savedInstanceState.getInt(CURRENT_STEP_KEY);
            stepsList = savedInstanceState.getParcelableArrayList(CURRENT_RECIPE_STEPS_LIST_KEY);
        }

        if (view.findViewById(R.id.video_exoPlayer_landscape) != null)
            initializeLandscapeMode(view);
        else
            initializeNormalMode(view);

        return view;
    }

    void initializeNormalMode(View view)
    {
        shortDescriptionTextView = (TextView) view.findViewById(R.id.short_description_textView);
        descriptionTextView = (TextView) view.findViewById(R.id.long_description_textView);
        nextImageView = (ImageView) view.findViewById(R.id.next_imageView);
        prevImageView = (ImageView) view.findViewById(R.id.prev_imageView);
        stepExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.video_exoPlayer);

        initializePlayer();

        if (currentStep != -1) {
            setDataToUI();
        }
        nextImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStep == stepsList.size() - 1)
                    return;
                currentStep++;
                setDataToUI();
            }
        });

        prevImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStep == 0)
                    return;
                currentStep--;
                setDataToUI();
            }
        });
    }
    void initializeLandscapeMode(View view)
    {
        stepExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.video_exoPlayer_landscape);

        initializePlayer();

        setUriToPlayer();
    }

    void initializePlayer()
    {
        if (stepExoPlayer == null)
        {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            stepExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            stepExoPlayerView.setPlayer(stepExoPlayer);
        }
    }

    public void setDataToUI()
    {
        shortDescriptionTextView.setText(stepsList.get(currentStep).getShortDescription());
        descriptionTextView.setText(stepsList.get(currentStep).getDescription());

        setUriToPlayer();

        setImageViews();
    }

    void setUriToPlayer()
    {
        Uri uri = Uri.parse(stepsList.get(currentStep).getVideoURL());
        MediaSource mediaSource = new ExtractorMediaSource(uri,
                new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(),"backingapp")),
                new DefaultExtractorsFactory(),null,null);

        stepExoPlayer.prepare(mediaSource);
        stepExoPlayer.setPlayWhenReady(true);
    }

    void setImageViews()
    {
        if (currentStep == 0)
            prevImageView.setImageResource(R.drawable.cannot_back);
        else
            prevImageView.setImageResource(R.drawable.can_back);

        if (currentStep == stepsList.size() - 1)
            nextImageView.setImageResource(R.drawable.cannot_next);
        else
            nextImageView.setImageResource(R.drawable.can_next);
    }

    void releasePlayer()
    {
        stepExoPlayer.stop();
        stepExoPlayer.release();
        stepExoPlayer = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(CURRENT_RECIPE_STEPS_LIST_KEY, (ArrayList<? extends Parcelable>) stepsList);
        outState.putInt(CURRENT_STEP_KEY,currentStep);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
