package com.ready.sport.inmatch.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.PlayersModel;

import java.util.List;

public class MatchDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        List<PlayersModel> pl = null;
        PlayersModel pla1 = new PlayersModel();
        pla1.setRatingSoccer(4.5);
        pla1.setRuoloSoccer(0);
        PlayersModel pla2 = new PlayersModel();
        pla2.setRatingSoccer(4.5);
        pla2.setRuoloSoccer(0);
        PlayersModel pla3 = new PlayersModel();
        pla3.setRatingSoccer(4.5);
        pla3.setRuoloSoccer(1);
        PlayersModel pla4 = new PlayersModel();
        pla4.setRatingSoccer(4.5);
        pla4.setRuoloSoccer(1);
        PlayersModel pla5 = new PlayersModel();
        pla1.setRatingSoccer(4.5);
        pla1.setRuoloSoccer(2);
        PlayersModel pla6 = new PlayersModel();
        pla2.setRatingSoccer(4.5);
        pla2.setRuoloSoccer(2);
        PlayersModel pla7 = new PlayersModel();
        pla3.setRatingSoccer(4.5);
        pla3.setRuoloSoccer(3);
        PlayersModel pla8 = new PlayersModel();
        pla4.setRatingSoccer(4.5);
        pla4.setRuoloSoccer(3);
        PlayersModel pla9 = new PlayersModel();
        pla1.setRatingSoccer(4.5);
        pla1.setRuoloSoccer(0);
        PlayersModel pla10 = new PlayersModel();
        pla2.setRatingSoccer(4.5);
        pla2.setRuoloSoccer(0);

        pl.add(pla1);
    }
}
