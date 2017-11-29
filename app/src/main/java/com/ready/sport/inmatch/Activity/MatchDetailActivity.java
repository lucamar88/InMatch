package com.ready.sport.inmatch.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.util.ButtonPlus;
import com.ready.sport.inmatch.util.TeamUtility;
import com.ready.sport.inmatch.util.TextViewPlus;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import uk.co.markormesher.android_fab.FloatingActionButton;
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter;
import uk.co.markormesher.android_fab.SpeedDialMenuItem;

public class MatchDetailActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private AppBarLayout bar;
    private ButtonPlus btn;
    private SpeedDialMenuAdapter speedDialMenuAdapter = new SpeedDialMenuAdapter() {
        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public boolean onMenuItemClick(int i){
            if(i == 0){
                onClickWhatsApp();
            }else{
                Toast.makeText(MatchDetailActivity.this, "Click botton "+i, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        @NotNull
        @Override
        public SpeedDialMenuItem getMenuItem(Context context, int i) {
            SpeedDialMenuItem speedDialMenuItem = null;
            switch (i){
                case 0:
                    speedDialMenuItem = new SpeedDialMenuItem(context, android.R.drawable.ic_media_play, "Item One");
                    break;
                case 1:
                    speedDialMenuItem = new SpeedDialMenuItem(context, android.R.drawable.ic_menu_edit, "Item Two");
                    break;
                case 2:
                    speedDialMenuItem = new SpeedDialMenuItem(context, android.R.drawable.ic_menu_crop, "Item Three");
                    break;
            }
            return speedDialMenuItem;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        bar = (AppBarLayout)findViewById(R.id.appbarMatchDetail);

        btn = (ButtonPlus)findViewById(R.id.btn_generate_teams);

        fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setSpeedDialMenuAdapter(speedDialMenuAdapter);

        List<PlayersModel> pl = new ArrayList<PlayersModel>();
        PlayersModel pla1 = new PlayersModel();
        pla1.setRatingSoccer(4.5);
        pla1.setRuoloSoccer(0);
        PlayersModel pla2 = new PlayersModel();
        pla2.setRatingSoccer(7.5);
        pla2.setRuoloSoccer(0);
        PlayersModel pla3 = new PlayersModel();
        pla3.setRatingSoccer(5.1);
        pla3.setRuoloSoccer(1);
        PlayersModel pla4 = new PlayersModel();
        pla4.setRatingSoccer(7.8);
        pla4.setRuoloSoccer(1);
        PlayersModel pla5 = new PlayersModel();
        pla5.setRatingSoccer(8.9);
        pla5.setRuoloSoccer(2);
        PlayersModel pla6 = new PlayersModel();
        pla6.setRatingSoccer(8.2);
        pla6.setRuoloSoccer(2);
        PlayersModel pla7 = new PlayersModel();
        pla7.setRatingSoccer(7.0);
        pla7.setRuoloSoccer(3);
        PlayersModel pla8 = new PlayersModel();
        pla8.setRatingSoccer(9.2);
        pla8.setRuoloSoccer(3);
        PlayersModel pla9 = new PlayersModel();
        pla9.setRatingSoccer(5.7);
        pla9.setRuoloSoccer(1);
        PlayersModel pla10 = new PlayersModel();
        pla10.setRatingSoccer(6.7);
        pla10.setRuoloSoccer(1);

        pl.add(pla1);
        pl.add(pla2);
        pl.add(pla3);
        pl.add(pla4);
        pl.add(pla5);
        pl.add(pla6);
        pl.add(pla7);
        pl.add(pla8);
        pl.add(pla9);
        pl.add(pla10);


        List<String> list = new ArrayList<String>();
        list = TeamUtility.GenerateTeam(pl, 1);

    }

    public void onClickWhatsApp() {

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "YOUR TEXT HERE";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }
}
