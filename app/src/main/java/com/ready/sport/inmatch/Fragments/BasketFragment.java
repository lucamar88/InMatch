package com.ready.sport.inmatch.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.BasketModel;
import com.ready.sport.inmatch.RealmClass.TennisModel;
import com.ready.sport.inmatch.util.CircularProgressBar;
import com.xw.repo.BubbleSeekBar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class BasketFragment extends Fragment {
    private boolean isClickable = false;

    private BubbleSeekBar seekbar;
    private BubbleSeekBar seekbar2;
    private BubbleSeekBar seekbar3;
    private BubbleSeekBar seekbar4;
    private BubbleSeekBar seekbar5;
    private BubbleSeekBar seekbar6;
    private CircularProgressBar c3;
    private double ratingFinal;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.basket_fragment, container, false);
        c3 = (CircularProgressBar) rootView.findViewById(R.id.circularprogressbarBasket);
        c3.setTitle("5,0");
        c3.setProgress(50);
        try {
            isClickable = getArguments().getBoolean("isClick");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        NestedScrollView scroller = (NestedScrollView) rootView.findViewById(R.id.scrollBasket);

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
                    UpdateOffsetSeekBar();
                }
            });
        }

        seekbar = (BubbleSeekBar)rootView.findViewById(R.id.seekbarBasVel);
        setBubbleSeekBar(seekbar);
        seekbar2 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarBasPot);
        setBubbleSeekBar(seekbar2);
        seekbar3 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarBasPas);
        setBubbleSeekBar(seekbar3);
        seekbar4 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarBasDif);
        setBubbleSeekBar(seekbar4);
        seekbar5 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarBasAtt);
        setBubbleSeekBar(seekbar5);
        seekbar6 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarBasFin);
        setBubbleSeekBar(seekbar6);
        UpdateOffsetSeekBar();
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

                }

                @Override
                public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                }

                @Override
                public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
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

    public BasketModel getDataBasket(){
        BasketModel model = new BasketModel();
        model.setVelocitaBasket(seekbar.getProgressFloat());
        model.setPotenzaBasket(seekbar2.getProgressFloat());
        model.setPassaggioBasket(seekbar3.getProgressFloat());
        model.setDifesaBasket(seekbar4.getProgressFloat());
        model.setAttaccoBasket(seekbar5.getProgressFloat());
        model.setFinalizzazioneBasket(seekbar6.getProgressFloat());
        model.setRatingBasket(ratingFinal);
        return model;
    }
}
