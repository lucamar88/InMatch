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
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.xw.repo.BubbleSeekBar;

import java.text.DecimalFormat;

import io.realm.Realm;

/**
 * Created by l.martelloni on 29/08/2017.
 */

public class PopupDialogFragmentVolley extends DialogFragment {
    private Realm realm;
    private int idPLayer;
    private CircularProgressBar c3;
    private DecimalFormat value;
    private TextViewPlus label1, label2, label3, label4, label5, label6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.volley_fragment, container,
                false);
        LinearLayoutCompat lin = (LinearLayoutCompat)rootView.findViewById(R.id.titleVolleyFragment);
        lin.setVisibility(View.VISIBLE);
        CircularProgressBar c3 = (CircularProgressBar) rootView.findViewById(R.id.circularprogressbarVolley);
        realm= Realm.getDefaultInstance();
        value = new DecimalFormat("#.#");
        idPLayer = getArguments().getInt("idPlayer");

        label1 = (TextViewPlus)rootView.findViewById(R.id.labelVolBat);
        label2 = (TextViewPlus)rootView.findViewById(R.id.labelVolPot);
        label3 = (TextViewPlus)rootView.findViewById(R.id.labelVolPre);
        label4 = (TextViewPlus)rootView.findViewById(R.id.labelVolRic);
        label5 = (TextViewPlus)rootView.findViewById(R.id.labelVolDif);
        label6 = (TextViewPlus)rootView.findViewById(R.id.labelVolSc);

        final BubbleSeekBar seekbar = (BubbleSeekBar)rootView.findViewById(R.id.seekbarVolDif);
        setBubbleSeekBar(seekbar);
        final BubbleSeekBar seekbar2 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarVolRic);
        setBubbleSeekBar(seekbar2);
        final BubbleSeekBar seekbar3 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarVolPot);
        setBubbleSeekBar(seekbar3);
        final BubbleSeekBar seekbar4 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarVolBat);
        setBubbleSeekBar(seekbar4);
        final BubbleSeekBar seekbar5 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarVolPre);
        setBubbleSeekBar(seekbar5);
        final BubbleSeekBar seekbar6 = (BubbleSeekBar)rootView.findViewById(R.id.seekbarVolSc);
        setBubbleSeekBar(seekbar6);

        PlayersModel model = realm.where(PlayersModel.class).equalTo("IdPlayer", idPLayer).findFirst();
        c3.setTitle(value.format(model.getRatingVolley()));
        Double d = model.getRatingVolley()*10;
        c3.setProgress(Integer.valueOf(d.intValue()));

        if(model!= null){
            label1.setText(String.valueOf(value.format(model.i_BattutaVolley)));
            label2.setText(String.valueOf(value.format(model.i_PotenzaVolley)));
            label3.setText(String.valueOf(value.format(model.i_PrecisioneVolley)));
            label4.setText(String.valueOf(value.format(model.i_RicezioneVolley)));
            label5.setText(String.valueOf(value.format(model.i_DifesaVolley)));
            label6.setText(String.valueOf(value.format(model.i_SchiacciataVolley)));

            seekbar.setProgress(Float.valueOf(value.format(model.i_BattutaVolley).replace(',','.')));
            seekbar2.setProgress(Float.valueOf(value.format(model.i_PotenzaVolley).replace(',','.')));
            seekbar3.setProgress(Float.valueOf(value.format(model.i_PrecisioneVolley).replace(',','.')));
            seekbar4.setProgress(Float.valueOf(value.format(model.i_RicezioneVolley).replace(',','.')));
            seekbar5.setProgress(Float.valueOf(value.format(model.i_DifesaVolley).replace(',','.')));
            seekbar6.setProgress(Float.valueOf(value.format(model.i_SchiacciataVolley).replace(',','.')));
        }

        return rootView;
    }

    public void setBubbleSeekBar(BubbleSeekBar seek){
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
