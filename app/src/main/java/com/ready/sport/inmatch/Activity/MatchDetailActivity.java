package com.ready.sport.inmatch.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.MatchModel;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.Tools.CustomAdapterListPlayerDetail;
import com.ready.sport.inmatch.Tools.CustomAdaptersPlayersMatch;
import com.ready.sport.inmatch.util.ButtonPlus;
import com.ready.sport.inmatch.util.TeamUtility;
import com.ready.sport.inmatch.util.TextViewPlus;
import com.ready.sport.inmatch.util.ToastCustom;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import uk.co.markormesher.android_fab.FloatingActionButton;
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter;
import uk.co.markormesher.android_fab.SpeedDialMenuItem;

public class MatchDetailActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private AppBarLayout bar;
    private ButtonPlus btn;
    private int IdMatch = 0;
    private Realm realm;
    private MatchModel match;
    private List<PlayersModel> firstList;
    private List<PlayersModel> secondList;

    private List<PlayersModel> totList;
    private RecyclerView recyclerViewFirst;
    private RecyclerView recyclerViewSecond;
    private static RecyclerView.Adapter adapterFirst;
    private static RecyclerView.Adapter adapterSecond;
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
        Intent intent = getIntent();
        setContentView(R.layout.activity_match_detail);
        realm= Realm.getDefaultInstance();
        bar = (AppBarLayout)findViewById(R.id.appbarMatchDetail);

        btn = (ButtonPlus)findViewById(R.id.btn_generate_teams);

        fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setSpeedDialMenuAdapter(speedDialMenuAdapter);

        recyclerViewFirst = (RecyclerView) findViewById(R.id.listFirstTeamDetail);

        recyclerViewSecond = (RecyclerView) findViewById(R.id.listSecondTeamDetail);

        firstList = new ArrayList<>();
        secondList = new ArrayList<>();

        AppCompatImageView back = (AppCompatImageView)findViewById(R.id.backBtnDetailMatch);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TextViewPlus location = (TextViewPlus)findViewById(R.id.locationMatchDetail);
        TextViewPlus data = (TextViewPlus)findViewById(R.id.dateMatchDetail);

        try{
            IdMatch = intent.getExtras().getInt("idMatch");
        }catch(Exception e){
            Toast.makeText(this,"Errore di rete",Toast.LENGTH_SHORT).show();
            finish();
        }

        if(IdMatch != 0){
            match = realm.where(MatchModel.class).equalTo("IdMatch",IdMatch).findFirst();
        }else{
            finish();
        }

        location.setText(match.getLocation());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        try {
            Date date = dateFormat.parse(match.getStartDateUtc());//You will get date object relative to server/client timezone wherever it is parsed
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String time = sdf.format(date);
            data.setText(dateStr + " ore " + time);
        } catch (Exception e) {
            Log.e("Error Data:", e.getMessage());
        }

        if(match.getListPlayersFirstTeam() != null && match.getListPlayersSecondTeam() != null){
            String[] firstTeamList = match.getListPlayersFirstTeam().split("_");
            String[] secondTeamList = match.getListPlayersSecondTeam().split("_");

            for(String str:firstTeamList){
                int id = Integer.parseInt(str);
                PlayersModel player = realm.where(PlayersModel.class).equalTo("IdPlayer", id ).findFirst();
                firstList.add(player);
                totList.add(player);
            }

            for(String str:secondTeamList){
                int id = Integer.parseInt(str);
                PlayersModel player = realm.where(PlayersModel.class).equalTo("IdPlayer", id ).findFirst();
                secondList.add(player);
                totList.add(player);
            }


        }
        if(firstList.size() != 0 || secondList.size()!= 0){
            setUpRecyclerView();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstList.clear();
                secondList.clear();
                String[] totPlayerList = match.getListTotalPlayers().split("_");
                List<PlayersModel> playerTot = new ArrayList<PlayersModel>();
                for (String str: totPlayerList
                     ) {
                    int id = Integer.parseInt(str);
                    PlayersModel pla1 = realm.where(PlayersModel.class).equalTo("IdPlayer", id ).findFirst();
                    playerTot.add(pla1);
                }
                List<String> list = TeamUtility.GenerateTeam(playerTot, 1);

                String[] firstTeam = list.get(0).split("_");
                String[] secondTeam = list.get(1).split("_");

                for (int i = 0;i<(firstTeam.length -1);i++
                        ) {
                    if(firstTeam[i] != ""){
                        int id = Integer.parseInt(firstTeam[i]);
                        PlayersModel pla1 = realm.where(PlayersModel.class).equalTo("IdPlayer", id ).findFirst();
                        firstList.add(pla1);
                    }
                }

                for (int i = 0;i<(secondTeam.length -1);i++
                        ) {
                    if(secondTeam[i] != ""){
                        int id = Integer.parseInt(secondTeam[i]);
                        PlayersModel pla1 = realm.where(PlayersModel.class).equalTo("IdPlayer", id ).findFirst();
                        secondList.add(pla1);
                    }
                }
                setUpRecyclerView();

            }
        });
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

    private void setUpRecyclerView() {


        adapterFirst = new CustomAdapterListPlayerDetail(firstList, match.getMatchType(),getBaseContext());
        adapterSecond = new CustomAdapterListPlayerDetail(secondList, match.getMatchType(),getBaseContext());
        //First list

        recyclerViewFirst.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerViewFirst.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFirst.setAdapter(adapterFirst);

        recyclerViewFirst.setHasFixedSize(true);
        recyclerViewFirst.setNestedScrollingEnabled(false);


        //Second list
        recyclerViewSecond.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerViewSecond.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSecond.setAdapter(adapterSecond);

        recyclerViewSecond.setHasFixedSize(true);
        recyclerViewSecond.setNestedScrollingEnabled(false);

        ToastCustom toast = new ToastCustom(this, getResources().getDrawable(R.drawable.ic_icon_check),getString(R.string.teams_generated));
        toast.show();
    }
}
