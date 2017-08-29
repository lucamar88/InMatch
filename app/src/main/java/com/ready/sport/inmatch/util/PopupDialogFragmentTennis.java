package com.ready.sport.inmatch.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ready.sport.inmatch.R;
import com.xw.repo.BubbleSeekBar;

/**
 * Created by l.martelloni on 29/08/2017.
 */

public class PopupDialogFragmentTennis extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tennis_fragment, container,
                false);
        LinearLayoutCompat lin = (LinearLayoutCompat)rootView.findViewById(R.id.titleTennisFragment);
        lin.setVisibility(View.VISIBLE);
        CircularProgressBar c3 = (CircularProgressBar) rootView.findViewById(R.id.circularprogressbarTennis);
        c3.setTitle("5,0");
        c3.setProgress(50);


        final BubbleSeekBar seekbar = (BubbleSeekBar)rootView.findViewById(R.id.seekbarTenAgi);
        setBubbleSeekBar(seekbar);
        final BubbleSeekBar seekbar2 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarTenBat);
        setBubbleSeekBar(seekbar2);
        final BubbleSeekBar seekbar3 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarTenDri);
        setBubbleSeekBar(seekbar3);
        final BubbleSeekBar seekbar4 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarTenPot);
        setBubbleSeekBar(seekbar4);
        final BubbleSeekBar seekbar5 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarTenRov);
        setBubbleSeekBar(seekbar5);
        final BubbleSeekBar seekbar6 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarTenSc);
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
