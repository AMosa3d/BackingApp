package com.example.abdel.backingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdel.backingapp.IngredientActivity;
import com.example.abdel.backingapp.Interfaces.StepIngredientActivityCommunicator;
import com.example.abdel.backingapp.Models.Step;
import com.example.abdel.backingapp.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdel on 11/25/2017.
 */

public class StepFragment extends Fragment implements StepIngredientActivityCommunicator {

    TextView shortDescriptionTextView,descriptionTextView;
    ImageView nextImageView,prevImageView;
    SimpleExoPlayerView stepExoPlayerView;
    SimpleExoPlayer stepExoPlayer;

    int currentStep = 0;
    List<Step> stepsList;
    boolean isReady;
    long position = 0;

    final String CURRENT_STEP_KEY = "CurrentStep";
    final String CURRENT_RECIPE_STEPS_LIST_KEY = "StepsList";
    final String PLAYER_POSITION = "ExoPosition";
    final String PLAYER_STATE = "ExoState";

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
        isReady = true;
        if (savedInstanceState != null)
        {
            currentStep = savedInstanceState.getInt(CURRENT_STEP_KEY);
            stepsList = savedInstanceState.getParcelableArrayList(CURRENT_RECIPE_STEPS_LIST_KEY);
            position = savedInstanceState.getLong(PLAYER_POSITION);
            isReady = savedInstanceState.getBoolean(PLAYER_STATE);
        }

        initializeComponents(view);

        setHasOptionsMenu(true);

        return view;
    }

    public void initializeComponents(View view)
    {
        if (view.findViewById(R.id.video_exoPlayer_landscape) != null)
            initializeLandscapeMode(view);
        else
            initializeNormalMode(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(getActivity());
            return true;
        }

        return false;
    }

    void initializeNormalMode(View view)
    {
        shortDescriptionTextView = (TextView) view.findViewById(R.id.short_description_textView);
        descriptionTextView = (TextView) view.findViewById(R.id.long_description_textView);
        nextImageView = (ImageView) view.findViewById(R.id.next_imageView);
        prevImageView = (ImageView) view.findViewById(R.id.prev_imageView);
        stepExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.video_exoPlayer);

        initializePlayer();

        setDataToUI();

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

        stepExoPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                isReady = playWhenReady;
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity() {

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

        if (position != C.TIME_UNSET)
            stepExoPlayer.seekTo(position);
        stepExoPlayer.prepare(mediaSource);
        stepExoPlayer.setPlayWhenReady(isReady);
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
        if (stepExoPlayer != null)
        {
            stepExoPlayer.stop();
            stepExoPlayer.release();
            stepExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(CURRENT_RECIPE_STEPS_LIST_KEY, (ArrayList<? extends Parcelable>) stepsList);
        outState.putInt(CURRENT_STEP_KEY,currentStep);
        outState.putLong(PLAYER_POSITION,position);
        outState.putBoolean(PLAYER_STATE,isReady);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        position = stepExoPlayer.getCurrentPosition();
        releasePlayer();
    }

    @Override
    public void sendData(int sp, List<Step> sl) {
        setCurrentStep(sp);
        setStepsList(sl);
        setDataToUI();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((IngredientActivity)context).stepIngredientActivityCommunicator = this;
    }
}
