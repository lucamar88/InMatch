package com.ready.sport.inmatch.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.BasketModel;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.RealmClass.VolleyModel;
import com.ready.sport.inmatch.util.CircularProgressBar;
import com.ready.sport.inmatch.util.TextViewPlus;
import com.xw.repo.BubbleSeekBar;

import java.text.DecimalFormat;

import io.realm.Realm;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class VolleyFragment extends Fragment {
    private boolean isClickable = false;

    private BubbleSeekBar seekbar;
    private BubbleSeekBar seekbar2;
    private BubbleSeekBar seekbar3;
    private BubbleSeekBar seekbar4;
    private BubbleSeekBar seekbar5;
    private BubbleSeekBar seekbar6;
    private CircularProgressBar c3;

    private TextViewPlus label1, label2, label3, label4, label5, label6;

    private double ratingFinal;
    private DecimalFormat value;
    private int IdPlayer;
    private PlayersModel pl = null;
    private Realm realm;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.volley_fragment, container, false);
        c3 = (CircularProgressBar) rootView.findViewById(R.id.circularprogressbarVolley);
        realm= Realm.getDefaultInstance();
        value = new DecimalFormat("#.#");
        try {
            isClickable = getArguments().getBoolean("isClick");
            IdPlayer = getArguments().getInt("idPlayer", 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        NestedScrollView scroller = (NestedScrollView) rootView.findViewById(R.id.scrollVolley);

        if (scroller != null) {

            scroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
                    UpdateOffsetSeekBar();
                }
            });
        }

        if(IdPlayer != 0){
            pl = realm.where(PlayersModel.class).equalTo("IdPlayer", IdPlayer).findFirst();
        }

        SetAllSeek(rootView);

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
                        case R.id.seekbarVolBat:
                            label1.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarVolPot:
                            label2.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarVolPre:
                            label3.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarVolRic:
                            label4.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarVolDif:
                            label5.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarVolSc:
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

    public VolleyModel getDataVolley(){
        VolleyModel model = new VolleyModel();
        model.setBattutaVolley(seekbar.getProgressFloat());
        model.setPotenzaVolley(seekbar2.getProgressFloat());
        model.setPrecisioneVolley(seekbar3.getProgressFloat());
        model.setRicezioneVolley(seekbar4.getProgressFloat());
        model.setDifesaVolley(seekbar5.getProgressFloat());
        model.setSchiacciataVolley(seekbar6.getProgressFloat());
        model.setRatingVolley(ratingFinal);
        return model;
    }

    public void SetAllSeek(View rootView){
        label1 = (TextViewPlus)rootView.findViewById(R.id.labelVolBat);
        label2 = (TextViewPlus)rootView.findViewById(R.id.labelVolPot);
        label3 = (TextViewPlus)rootView.findViewById(R.id.labelVolPre);
        label4 = (TextViewPlus)rootView.findViewById(R.id.labelVolRic);
        label5 = (TextViewPlus)rootView.findViewById(R.id.labelVolDif);
        label6 = (TextViewPlus)rootView.findViewById(R.id.labelVolSc);

        seekbar = (BubbleSeekBar)rootView.findViewById(R.id.seekbarVolBat);
        setBubbleSeekBar(seekbar);
        seekbar2 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarVolPot);
        setBubbleSeekBar(seekbar2);
        seekbar3 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarVolPre);
        setBubbleSeekBar(seekbar3);
        seekbar4 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarVolRic);
        setBubbleSeekBar(seekbar4);
        seekbar5 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarVolDif);
        setBubbleSeekBar(seekbar5);
        seekbar6 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarVolSc);
        setBubbleSeekBar(seekbar6);

        if(pl != null){
            label1.setText(String.valueOf(pl.i_BattutaVolley));
            label2.setText(String.valueOf(pl.i_PotenzaVolley));
            label3.setText(String.valueOf(pl.i_PrecisioneVolley));
            label4.setText(String.valueOf(pl.i_RicezioneVolley));
            label5.setText(String.valueOf(pl.i_DifesaVolley));
            label6.setText(String.valueOf(pl.i_SchiacciataVolley));

            seekbar.setProgress(Float.valueOf(value.format(pl.i_BattutaVolley)));
            seekbar2.setProgress(Float.valueOf(value.format(pl.i_PotenzaVolley)));
            seekbar3.setProgress(Float.valueOf(value.format(pl.i_PrecisioneVolley)));
            seekbar4.setProgress(Float.valueOf(value.format(pl.i_RicezioneVolley)));
            seekbar5.setProgress(Float.valueOf(value.format(pl.i_DifesaVolley)));
            seekbar6.setProgress(Float.valueOf(value.format(pl.i_SchiacciataVolley)));
        }
    }
}