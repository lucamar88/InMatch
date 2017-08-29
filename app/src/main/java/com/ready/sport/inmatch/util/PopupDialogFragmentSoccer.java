package com.ready.sport.inmatch.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.ready.sport.inmatch.R;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by l.martelloni on 28/08/2017.
 */

public class PopupDialogFragmentSoccer extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.soccer_fragment, container,
                false);
        LinearLayoutCompat lin = (LinearLayoutCompat)rootView.findViewById(R.id.titleSoccerFragment);
        lin.setVisibility(View.VISIBLE);
        CircularProgressBar c3 = (CircularProgressBar) rootView.findViewById(R.id.circularprogressbarSoccer);
        c3.setTitle("5,0");
        c3.setProgress(50);

        AppCompatSpinner spinner = (AppCompatSpinner) rootView.findViewById(R.id.spinnerSoccer);

        List<String> categories = new ArrayList<String>();
        categories.add("Portiere");
        categories.add("Difensore");
        categories.add("Centrocampista");
        categories.add("Attaccante");
        // Creating adapter for spinner
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.my_spinner, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        //spinner.setClickable(false);
        spinner.setClickable(false);

        final BubbleSeekBar seekbar = (BubbleSeekBar)rootView.findViewById(R.id.seekbarSocVel);
        setBubbleSeekBar(seekbar);
        final BubbleSeekBar seekbar2 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarSocPot);
        setBubbleSeekBar(seekbar2);
        final BubbleSeekBar seekbar3 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarSocDri);
        setBubbleSeekBar(seekbar3);
        final BubbleSeekBar seekbar4 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarSocDif);
        setBubbleSeekBar(seekbar4);
        final BubbleSeekBar seekbar5 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarSocAtt);
        setBubbleSeekBar(seekbar5);
        final BubbleSeekBar seekbar6 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarSocAgi);
        setBubbleSeekBar(seekbar6);


        return rootView;
    }

    public void setBubbleSeekBar(BubbleSeekBar seek){
        seek.getConfigBuilder()
                .min(1)
                .max(10.0f)
                .floatType()
                .progress(5.0f)
                .sectionCount(90)
                .trackColor(ContextCompat.getColor(getContext(), R.color.colorButton))
                .secondTrackColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .thumbColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .showThumbText()
                .thumbTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .thumbTextSize(18)
                .bubbleColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .bubbleTextSize(18)
                .seekBySection()
                .showSectionMark()
                .autoAdjustSectionMark()
                .build();
        seek.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public void onResume()
    {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int height = display.getHeight();
        int width = display.getWidth();

        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout((width*9)/10, (height * 6) /10);
        window.setGravity(Gravity.CENTER);
        //TODO:
    }
}