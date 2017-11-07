package com.ready.sport.inmatch.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.SoccerModel;
import com.ready.sport.inmatch.util.ButtonPlus;
import com.ready.sport.inmatch.util.CircularProgressBar;
import com.ready.sport.inmatch.util.TextViewPlus;
import com.xw.repo.BubbleSeekBar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import iammert.com.huelib.HueSeekBar;
import iammert.com.huelib.ProgressListener;
import iammert.com.huelib.VerticalAnimationListener;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class SoccerFragment extends Fragment {

    private boolean isClickable = false;

    private HueSeekBar seekbar;
    private HueSeekBar seekbar2;
    private HueSeekBar seekbar3;
    private HueSeekBar seekbar4;
    private HueSeekBar seekbar5;
    private HueSeekBar seekbar6;

    private TextViewPlus seekbartext;
    private TextViewPlus seekbartext2;
    private TextViewPlus seekbartext3;
    private TextViewPlus seekbartext4;
    private TextViewPlus seekbartext5;
    private TextViewPlus seekbartext6;

    private CircularProgressBar c3;
    private NestedScrollView scroller;
    private double ratingFinal;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.soccer_fragment, container, false);
        c3 = (CircularProgressBar) rootView.findViewById(R.id.circularprogressbarSoccer);
        c3.setTitle("5,0");
        c3.setProgress(50);

        try {
            isClickable = getArguments().getBoolean("isClick");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // Spinner element
        AppCompatSpinner spinner = (AppCompatSpinner) rootView.findViewById(R.id.spinnerSoccer);

        // Spinner click listener
        //spinner.setOnItemSelectedListener();

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Portiere");
        categories.add("Difensore");
        categories.add("Centrocampista");
        categories.add("Attaccante");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.my_spinner, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setClickable(false);

        seekbar = (HueSeekBar)rootView.findViewById(R.id.seekbarSocVel);
        seekbartext = (TextViewPlus) rootView.findViewById(R.id.seekbarSocVelNum);
        setBubbleSeekBar(seekbar, seekbartext);
        seekbar2 = (HueSeekBar)rootView.findViewById(R.id.seekbarSocPot);
        seekbartext2 = (TextViewPlus) rootView.findViewById(R.id.seekbarSocPotNum);
        setBubbleSeekBar(seekbar2,seekbartext2);
        seekbar3 = (HueSeekBar)rootView.findViewById(R.id.seekbarSocDri);
        seekbartext3 = (TextViewPlus) rootView.findViewById(R.id.seekbarSocDriNum);
        setBubbleSeekBar(seekbar3,seekbartext3);
        seekbar4 = (HueSeekBar)rootView.findViewById(R.id.seekbarSocDif);
        seekbartext4 = (TextViewPlus) rootView.findViewById(R.id.seekbarSocDifNum);
        setBubbleSeekBar(seekbar4,seekbartext4);
        seekbar5 = (HueSeekBar)rootView.findViewById(R.id.seekbarSocAtt);
        seekbartext5 = (TextViewPlus) rootView.findViewById(R.id.seekbarSocAttNum);
        setBubbleSeekBar(seekbar5,seekbartext5);
        seekbar6 = (HueSeekBar)rootView.findViewById(R.id.seekbarSocAgi);
        seekbartext6 = (TextViewPlus) rootView.findViewById(R.id.seekbarSocAgiNum);
        setBubbleSeekBar(seekbar6,seekbartext6);
        return rootView;
    }

    public void setBubbleSeekBar(final HueSeekBar seek, TextViewPlus text){
        seek.setCurrentProgress(50);
        final TextViewPlus textViewPlus = text;


        if(!isClickable){
            seek.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }else{

            seek.setProgressListener(new ProgressListener() {

                @Override
                public void onProgressChange(int progress) {

                    float perc = (float)progress / 10;

                    textViewPlus.setText(perc + " / 10");
                    //do somth with progress value

                    double tmp1 = seekbar.getCurrentProgress();
                    double tmp2 = seekbar2.getCurrentProgress();
                    double tmp3 = seekbar3.getCurrentProgress();
                    double tmp4 = seekbar4.getCurrentProgress();
                    double tmp5 = seekbar5.getCurrentProgress();
                    double tmp6 = seekbar6.getCurrentProgress();
                    double fin =(tmp1 + tmp2 + tmp3 + tmp4 + tmp5 + tmp6)/6;
                    DecimalFormat value = new DecimalFormat("#.#");
                    ratingFinal = fin/10;
                    c3.setTitle(value.format(fin/10));
                    c3.setProgress((int)fin);
                }
            });
            seek.setVerticalAnimationListener(new VerticalAnimationListener() {
                @Override
                public void onAnimProgressChanged(int percentage) {
                    float perc = (float)seek.getCurrentProgress() / 10;
                    textViewPlus.setText(perc + " / 10");

                    double tmp1 = seekbar.getCurrentProgress();
                    double tmp2 = seekbar2.getCurrentProgress();
                    double tmp3 = seekbar3.getCurrentProgress();
                    double tmp4 = seekbar4.getCurrentProgress();
                    double tmp5 = seekbar5.getCurrentProgress();
                    double tmp6 = seekbar6.getCurrentProgress();
                    double fin =(tmp1 + tmp2 + tmp3 + tmp4 + tmp5 + tmp6)/6;
                    DecimalFormat value = new DecimalFormat("#.#");
                    ratingFinal = fin/10;
                    c3.setTitle(value.format(fin/10));
                    c3.setProgress((int)fin);
                }
            });

        }

    }



    public SoccerModel getDataSoccer(){
        SoccerModel model = new SoccerModel();
        model.setAgilitaSoccer(seekbar6.getCurrentProgress()/10);
        model.setAttaccoSoccer(seekbar5.getCurrentProgress()/10);
        model.setDifesaSoccer(seekbar4.getCurrentProgress()/10);
        model.setDribblingSoccer(seekbar3.getCurrentProgress()/10);
        model.setPotenzaSoccer(seekbar2.getCurrentProgress()/10);
        model.setVelocitaSoccer(seekbar.getCurrentProgress()/10);
        model.setRatingSoccer(ratingFinal/10);
        return model;
    }
}
