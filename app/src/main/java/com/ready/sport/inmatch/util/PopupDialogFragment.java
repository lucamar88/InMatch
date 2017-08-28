package com.ready.sport.inmatch.util;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ready.sport.inmatch.R;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by l.martelloni on 28/08/2017.
 */

public class PopupDialogFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.soccer_fragment, container,
                false);
        CircularProgressBar c3 = (CircularProgressBar) rootView.findViewById(R.id.circularprogressbar1);
        c3.setTitle("7,5");
        c3.setProgress(75);
        List<String> categories = new ArrayList<String>();
        categories.add("Portiere");
        categories.add("Difensore");
        categories.add("Centrocampista");
        categories.add("Attaccante");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AppCompatSpinner spinner = (AppCompatSpinner) rootView.findViewById(R.id.spinnerSoccer);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setClickable(false);

        final BubbleSeekBar seekbar = (BubbleSeekBar)rootView.findViewById(R.id.seekbarSocVel);
        setBubbleSeekBar(seekbar);
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
}