package com.ready.sport.inmatch.Activity;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.util.Constants;
import com.ready.sport.inmatch.util.TextViewPlus;

public class CreateMatchActivity extends AppCompatActivity {
    private AppBarLayout bar;
    private TextViewPlus title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);
        bar = (AppBarLayout)findViewById(R.id.appbarCreateMatch);
        title = (TextViewPlus)findViewById(R.id.titleNewMatch);
        int type;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                type= Constants.SOCCER_TYPE;
            } else {
                type= extras.getInt(Constants.MATCH_TYPE);
            }
        } else {
            type= (int) savedInstanceState.getSerializable(Constants.MATCH_TYPE);
        }
        setColorBar(type);
        AppCompatImageView back = (AppCompatImageView)findViewById(R.id.backBtnCreateMatch);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void setColorBar(int index){
        switch (index){
            case Constants.SOCCER_TYPE:
                bar.setBackgroundColor(getResources().getColor(R.color.soccerColor));
                title.setText("Nuova partita - "+ R.string.soccer_label);
            case Constants.BASKET_TYPE:
                bar.setBackgroundColor(getResources().getColor(R.color.soccerColor));
                title.setText("Nuova partita - "+ R.string.basket_label);
            case Constants.TENNIS_TYPE:
                bar.setBackgroundColor(getResources().getColor(R.color.soccerColor));
                title.setText("Nuova partita - "+ R.string.tennis_label);
            case Constants.VOLLEY_TYPE:
                bar.setBackgroundColor(getResources().getColor(R.color.soccerColor));
                title.setText("Nuova partita - "+ R.string.volley_label);
        }
    }
}


