package com.ready.sport.inmatch.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.SoccerModel;
import com.ready.sport.inmatch.RealmClass.TennisModel;
import com.ready.sport.inmatch.util.CircularProgressBar;
import com.ready.sport.inmatch.util.TextViewPlus;
import com.xw.repo.BubbleSeekBar;

import java.text.DecimalFormat;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class TennisFragment extends Fragment {
    private boolean isClickable = false;

    private BubbleSeekBar seekbar;
    private BubbleSeekBar seekbar2;
    private BubbleSeekBar seekbar3;
    private BubbleSeekBar seekbar4;
    private BubbleSeekBar seekbar5;
    private BubbleSeekBar seekbar6;

    private TextViewPlus label1, label2, label3, label4, label5, label6;

    private CircularProgressBar c3;
    private double ratingFinal;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tennis_fragment, container, false);
        c3 = (CircularProgressBar) rootView.findViewById(R.id.circularprogressbarTennis);
        //c3.setTitle("5,0");
        //c3.setProgress(50);
        try {
            isClickable = getArguments().getBoolean("isClick");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        NestedScrollView scroller = (NestedScrollView) rootView.findViewById(R.id.scrollTennis);

        if (scroller != null) {

            scroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    //                    seekbar.correctOffsetWhenContainerOnScrolling();
//                    seekbar2.correctOffsetWhenContainerOnScrolling();
//                    seekbar3.correctOffsetWhenContainerOnScrolling();
//                    seekbar4.correctOffsetWhenContainerOnScrolling();
//                    seekbar5.correctOffsetWhenContainerOnScrolling();
//                    seekbar6.correctOffsetWhenContainerOnScrolling();

                }
            });
        }

        label1 = (TextViewPlus)rootView.findViewById(R.id.labelTenAgi);
        label2 = (TextViewPlus)rootView.findViewById(R.id.labelTenPot);
        label3 = (TextViewPlus)rootView.findViewById(R.id.labelTenBat);
        label4 = (TextViewPlus)rootView.findViewById(R.id.labelTenDri);
        label5 = (TextViewPlus)rootView.findViewById(R.id.labelTenRov);
        label6 = (TextViewPlus)rootView.findViewById(R.id.labelTenSc);

        seekbar = (BubbleSeekBar)rootView.findViewById(R.id.seekbarTenAgi);
        setBubbleSeekBar(seekbar);
        seekbar2 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarTenPot);
        setBubbleSeekBar(seekbar2);
        seekbar3 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarTenBat);
        setBubbleSeekBar(seekbar3);
        seekbar4 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarTenDri);
        setBubbleSeekBar(seekbar4);
        seekbar5 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarTenRov);
        setBubbleSeekBar(seekbar5);
        seekbar6 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarTenSc);
        setBubbleSeekBar(seekbar6);

        SetLayoutValue();
        return rootView;
    }

    public void setBubbleSeekBar(BubbleSeekBar seek){

        if(!isClickable){
            seek.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }else{
            seek.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
                @Override
                public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                    DecimalFormat value = new DecimalFormat("#.#");
                    switch (bubbleSeekBar.getId()){
                        case R.id.seekbarTenAgi:
                            label1.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarTenPot:
                            label2.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarTenBat:
                            label3.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarTenDri:
                            label4.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarTenRov:
                            label5.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarTenSc:
                            label6.setText(value.format(progressFloat));
                            break;
                    }
                }

                @Override
                public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                    double tmp1 = seekbar.getProgressFloat();
                    double tmp2 = seekbar2.getProgressFloat();
                    double tmp3 = seekbar3.getProgressFloat();
                    double tmp4 = seekbar4.getProgressFloat();
                    double tmp5 = seekbar5.getProgressFloat();
                    double tmp6 = seekbar6.getProgressFloat();
                    double fin =(tmp1 + tmp2 + tmp3 + tmp4 + tmp5 + tmp6)/6;
                    DecimalFormat value = new DecimalFormat("#.#");
                    c3.setTitle(value.format(fin));
                    ratingFinal = fin;
                    c3.setProgress((int)fin*10);

                }

                @Override
                public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

                }
            });
        }

    }

    public void UpdateOffsetSeekBar(){
        seekbar.correctOffsetWhenContainerOnScrolling();
        seekbar2.correctOffsetWhenContainerOnScrolling();
        seekbar3.correctOffsetWhenContainerOnScrolling();
        seekbar4.correctOffsetWhenContainerOnScrolling();
        seekbar5.correctOffsetWhenContainerOnScrolling();
        seekbar6.correctOffsetWhenContainerOnScrolling();
    }

    public void SetLayoutValue(){
        double tmp1 = seekbar.getProgressFloat();
        double tmp2 = seekbar2.getProgressFloat();
        double tmp3 = seekbar3.getProgressFloat();
        double tmp4 = seekbar4.getProgressFloat();
        double tmp5 = seekbar5.getProgressFloat();
        double tmp6 = seekbar6.getProgressFloat();
        double fin =(tmp1 + tmp2 + tmp3 + tmp4 + tmp5 + tmp6)/6;
        DecimalFormat value = new DecimalFormat("#.#");
        c3.setTitle(value.format(fin));
        ratingFinal = fin;
        c3.setProgress((int)fin*10);
    }

    public TennisModel getDataTennis(){
        TennisModel model = new TennisModel();
        model.setAgilitaTennis(seekbar.getProgressFloat());
        model.setPotenzaTennis(seekbar2.getProgressFloat());
        model.setBattutaTennis(seekbar3.getProgressFloat());
        model.setDrittoTennis(seekbar4.getProgressFloat());
        model.setRovescioTennis(seekbar5.getProgressFloat());
        model.setSchiacciataTennis(seekbar6.getProgressFloat());
        model.setRatingTennis(ratingFinal);
        return model;
    }
}
