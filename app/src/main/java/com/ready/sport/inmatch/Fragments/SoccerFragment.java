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
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.RealmClass.SoccerModel;
import com.ready.sport.inmatch.util.ButtonPlus;
import com.ready.sport.inmatch.util.CircularProgressBar;
import com.ready.sport.inmatch.util.Constants;
import com.ready.sport.inmatch.util.TextViewPlus;
import com.xw.repo.BubbleSeekBar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import io.realm.Realm;


/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class SoccerFragment extends Fragment {

    private boolean isClickable = false;

    private BubbleSeekBar seekbar, seekbar2,seekbar3, seekbar4, seekbar5, seekbar6;
    private TextViewPlus label1, label2, label3, label4, label5, label6;

    private CircularProgressBar c3;
    private NestedScrollView scroller;
    private double ratingFinal;
    private DecimalFormat value;
    private int IdPlayer;
    private PlayersModel pl = null;
    private Realm realm;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.soccer_fragment, container, false);
        c3 = (CircularProgressBar) rootView.findViewById(R.id.circularprogressbarSoccer);
        realm= Realm.getDefaultInstance();
        //c3.setTitle("5,0");
        //c3.setProgress(50);
        value = new DecimalFormat("#.#");
        try {
            isClickable = getArguments().getBoolean("isClick", false);
            IdPlayer = getArguments().getInt("idPlayer", 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(IdPlayer != 0){
            pl = realm.where(PlayersModel.class).equalTo("IdPlayer", IdPlayer).findFirst();
        }

        // Spinner element
        AppCompatSpinner spinner = (AppCompatSpinner) rootView.findViewById(R.id.spinnerSoccer);

        // Spinner click listener
        //spinner.setOnItemSelectedListener();

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        if(pl != null && !isClickable){
            categories.add(Constants.Role.fromInteger(pl.i_RuoloSoccer).toString());
        }else{
            categories.add(Constants.Role.PORTIERE.toString());
            categories.add(Constants.Role.DIFENSORE.toString());
            categories.add(Constants.Role.CENTROCAMPISTA.toString());
            categories.add(Constants.Role.ESTERNO.toString());
            categories.add(Constants.Role.ATTACCANTE.toString());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.my_spinner, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setClickable(isClickable);

        SetAllSeek(rootView);

        SetLayoutValue();
        return rootView;
    }

    public void setBubbleSeekBar(final BubbleSeekBar seek){
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
                    switch (bubbleSeekBar.getId()) {
                        case R.id.seekbarSocVel:
                            label1.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarSocPot:
                            label2.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarSocDri:
                            label3.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarSocDif:
                            label4.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarSocAtt:
                            label5.setText(value.format(progressFloat));
                            break;
                        case R.id.seekbarSocAgi:
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
                    double fin = (tmp1 + tmp2 + tmp3 + tmp4 + tmp5 + tmp6) / 6;

                    c3.setTitle(value.format(fin));
                    ratingFinal = fin;
                    c3.setProgress((int) fin * 10);

                }

                @Override
                public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

                }
            });
        }

    }

    public void SetLayoutValue(){
        double tmp1 = seekbar.getProgressFloat();
        double tmp2 = seekbar2.getProgressFloat();
        double tmp3 = seekbar3.getProgressFloat();
        double tmp4 = seekbar4.getProgressFloat();
        double tmp5 = seekbar5.getProgressFloat();
        double tmp6 = seekbar6.getProgressFloat();
        double fin =(tmp1 + tmp2 + tmp3 + tmp4 + tmp5 + tmp6)/6;

        c3.setTitle(value.format(fin));
        ratingFinal = fin;
        c3.setProgress((int)fin*10);
    }

    public SoccerModel getDataSoccer(){
        SoccerModel model = new SoccerModel();
        model.setAgilitaSoccer(Float.valueOf(value.format(seekbar6.getProgressFloat())));
        model.setAttaccoSoccer(Float.valueOf(value.format(seekbar5.getProgressFloat())));
        model.setDifesaSoccer(Float.valueOf(value.format(seekbar4.getProgressFloat())));
        model.setDribblingSoccer(Float.valueOf(value.format(seekbar3.getProgressFloat())));
        model.setPotenzaSoccer(Float.valueOf(value.format(seekbar2.getProgressFloat())));
        model.setVelocitaSoccer(Float.valueOf(value.format(seekbar.getProgressFloat())));
        model.setRatingSoccer(Double.valueOf(value.format(ratingFinal)));
        return model;
    }

    public void SetAllSeek(View rootView){
        label1 = (TextViewPlus)rootView.findViewById(R.id.labelSocVel);
        label2 = (TextViewPlus)rootView.findViewById(R.id.labelSocPot);
        label3 = (TextViewPlus)rootView.findViewById(R.id.labelSocDri);
        label4 = (TextViewPlus)rootView.findViewById(R.id.labelSocDif);
        label5 = (TextViewPlus)rootView.findViewById(R.id.labelSocAtt);
        label6 = (TextViewPlus)rootView.findViewById(R.id.labelSocAgi);

        seekbar = (BubbleSeekBar)rootView.findViewById(R.id.seekbarSocVel);
        setBubbleSeekBar(seekbar);
        seekbar2 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarSocPot);
        setBubbleSeekBar(seekbar2);
        seekbar3 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarSocDri);
        setBubbleSeekBar(seekbar3);
        seekbar4 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarSocDif);
        setBubbleSeekBar(seekbar4);
        seekbar5 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarSocAtt);
        setBubbleSeekBar(seekbar5);
        seekbar6 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarSocAgi);
        setBubbleSeekBar(seekbar6);

        if(pl!= null){
            label1.setText(String.valueOf(pl.i_VelocitaSoccer));
            label2.setText(String.valueOf(pl.i_PotenzaSoccer));
            label3.setText(String.valueOf(pl.i_DribblingSoccer));
            label4.setText(String.valueOf(pl.i_DifesaSoccer));
            label5.setText(String.valueOf(pl.i_AttaccoSoccer));
            label6.setText(String.valueOf(pl.i_AgilitaSoccer));

            seekbar.setProgress(Float.valueOf(value.format(pl.i_VelocitaSoccer)));
            seekbar2.setProgress(Float.valueOf(value.format(pl.i_PotenzaSoccer)));
            seekbar3.setProgress(Float.valueOf(value.format(pl.i_DribblingSoccer)));
            seekbar4.setProgress(Float.valueOf(value.format(pl.i_DifesaSoccer)));
            seekbar5.setProgress(Float.valueOf(value.format(pl.i_AttaccoSoccer)));
            seekbar6.setProgress(Float.valueOf(value.format(pl.i_AgilitaSoccer)));
        }
    }
}
