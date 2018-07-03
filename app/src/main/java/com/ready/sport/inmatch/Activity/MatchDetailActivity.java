package com.ready.sport.inmatch.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.reflect.TypeToken;
import com.hlab.fabrevealmenu.listeners.OnFABMenuSelectedListener;
import com.hlab.fabrevealmenu.model.FABMenuItem;
import com.hlab.fabrevealmenu.view.FABRevealMenu;
import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.CounterMatchModel;
import com.ready.sport.inmatch.RealmClass.MatchModel;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.Tools.CustomAdapterListPlayerDetail;
import com.ready.sport.inmatch.Tools.CustomAdaptersPlayersMatch;
import com.ready.sport.inmatch.util.ButtonPlus;
import com.ready.sport.inmatch.util.ConfigUrls;
import com.ready.sport.inmatch.util.Constants;
import com.ready.sport.inmatch.util.TeamUtility;
import com.ready.sport.inmatch.util.TextViewPlus;
import com.ready.sport.inmatch.util.ToastCustom;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

import uk.co.markormesher.android_fab.SpeedDialMenuAdapter;
import uk.co.markormesher.android_fab.SpeedDialMenuItem;

public class MatchDetailActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private AppBarLayout bar;
    private ButtonPlus btn;
    private AppCompatImageView btnCheck;
    private Context baseContext;


    private int IdMatch = 0;
    private Realm realm;
    private MatchModel match;
    private List<PlayersModel> firstList;
    private List<PlayersModel> secondList;
    private List<PlayersModel> totList;
    private String[] firstTeamList;
    private String[] secondTeamList;
    private String[] totPlayerList;
    private String firstTeamStringList;
    private String secondTeamStringList;
    private String rating1;
    private String rating2;
    private RecyclerView recyclerViewFirst;
    private RecyclerView recyclerViewSecond;
    private static RecyclerView.Adapter adapterFirst;
    private static RecyclerView.Adapter adapterSecond;

    private ArrayList<FABMenuItem> items;
   /*private SpeedDialMenuAdapter speedDialMenuAdapter = new SpeedDialMenuAdapter() {
       @Override
       public int getCount() {
           return 3;
       }
       @Override
       public boolean onMenuItemClick(int i){
           if(i == 0){
               onClickWhatsApp();
           }else if(i == 1){
               Intent intent = new Intent(MatchDetailActivity.this, CreateMatchActivity.class);
               intent.putExtra(Constants.MATCH_TYPE, match.getMatchType());
               intent.putExtra("IdMatch",IdMatch);
               startActivity(intent);
               finish();
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
                   speedDialMenuItem = new SpeedDialMenuItem(context, android.R.drawable.ic_menu_share, "Condividi match");
                   break;
               case 1:
                   speedDialMenuItem = new SpeedDialMenuItem(context, android.R.drawable.ic_menu_edit, "Modifica match");
                   break;
               case 2:
                   speedDialMenuItem = new SpeedDialMenuItem(context, android.R.drawable.ic_menu_crop, "Item Three");
                   break;
           }
           return speedDialMenuItem;
       }
   };
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_match_detail);
        baseContext = getBaseContext();

        realm= Realm.getDefaultInstance();
        bar = (AppBarLayout)findViewById(R.id.appbarMatchDetail);

        btn = (ButtonPlus)findViewById(R.id.btn_generate_teams);

        btnCheck = (AppCompatImageView)findViewById(R.id.saveMatchDetail);

        fab = (FloatingActionButton)findViewById(R.id.fab);


        final FABRevealMenu fabMenu = findViewById(R.id.fabMenu);

        try {
            if (fab != null && fabMenu != null) {
                initItems();
                //attach menu to fab
                //setFabMenu(fabMenu);
                //set menu items from arrylist
                fabMenu.setMenuItems(items);
                //attach menu to fab
                fabMenu.bindAnchorView(fab);

                //set menu item selection
                fabMenu.setOnFABMenuSelectedListener(new OnFABMenuSelectedListener() {
                    @Override
                    public void onMenuItemSelected(View view, int id) {
                        if(id == 0){
                            onClickWhatsApp();
                        }else if(id == 1){
                            Intent intent = new Intent(MatchDetailActivity.this, CreateMatchActivity.class);
                            intent.putExtra(Constants.MATCH_TYPE, match.getMatchType());
                            intent.putExtra("IdMatch",IdMatch);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(MatchDetailActivity.this, "Click botton "+id, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //fab.setSpeedDialMenuAdapter(speedDialMenuAdapter);

        recyclerViewFirst = (RecyclerView) findViewById(R.id.listFirstTeamDetail);

        recyclerViewSecond = (RecyclerView) findViewById(R.id.listSecondTeamDetail);

        firstList = new ArrayList<>();
        secondList = new ArrayList<>();
        totList = new ArrayList<>();

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

        TextViewPlus firstTeamName = (TextViewPlus)findViewById(R.id.firstTeamName);
        TextViewPlus secondTeamName = (TextViewPlus)findViewById(R.id.secondTeamName);
        firstTeamName.setText(match.getNameFirstTeam());
        secondTeamName.setText(match.getNameSecondTeam());
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
            firstTeamList = match.getListPlayersFirstTeam().split("_");
            secondTeamList = match.getListPlayersSecondTeam().split("_");

            firstTeamStringList = match.getListPlayersFirstTeam();
            secondTeamStringList = match.getListPlayersSecondTeam();

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
                totPlayerList = match.getListTotalPlayers().split("_");
                List<PlayersModel> playerTot = new ArrayList<PlayersModel>();
                for (String str: totPlayerList
                     ) {
                    int id = Integer.parseInt(str);
                    PlayersModel pla1 = realm.where(PlayersModel.class).equalTo("IdPlayer", id ).findFirst();
                    playerTot.add(pla1);
                }
                List<String> list = TeamUtility.GenerateTeam(playerTot, 1);

                firstTeamStringList = list.get(0);
                secondTeamStringList = list.get(1);

                firstTeamList = list.get(0).split("_");
                secondTeamList = list.get(1).split("_");

                for (int i = 0;i<firstTeamList.length;i++
                        ) {
                    if(firstTeamList[i] != ""){
                        int id = Integer.parseInt(firstTeamList[i]);
                        PlayersModel pla1 = realm.where(PlayersModel.class).equalTo("IdPlayer", id ).findFirst();
                        firstList.add(pla1);
                    }
                }

                for (int i = 0;i<secondTeamList.length ;i++
                        ) {
                    if(secondTeamList[i] != ""){
                        int id = Integer.parseInt(secondTeamList[i]);
                        PlayersModel pla1 = realm.where(PlayersModel.class).equalTo("IdPlayer", id ).findFirst();
                        secondList.add(pla1);
                    }
                }
                setUpRecyclerView();
                ToastCustom toast = new ToastCustom(MatchDetailActivity.this, getResources().getDrawable(R.drawable.ic_icon_check),getString(R.string.teams_generated));
                toast.show();
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveMatchDetail();
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
            Toast.makeText(MatchDetailActivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
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

        setRatingTeam();


    }

    public void setRatingTeam(){
        DecimalFormat value = new DecimalFormat("#.#");
        Double ratingFirstTemp = 0.0;
        Double ratingSecondTemp = 0.0;
        for(int a = 0 ;a<firstList.size();a++){
            switch (match.getMatchType()){
                case Constants.SOCCER_TYPE:
                    ratingFirstTemp += firstList.get(a).i_RatingTotSoccer;
                    break;
                case Constants.BASKET_TYPE:
                    ratingFirstTemp += firstList.get(a).i_RatingTotBasket;
                    break;
                case Constants.TENNIS_TYPE:
                    ratingFirstTemp += firstList.get(a).i_RatingTotTennis;
                    break;
                case Constants.VOLLEY_TYPE:
                    ratingFirstTemp += firstList.get(a).i_RatingTotVolley;
                    break;
            }
        }
        for(int a = 0 ;a<secondList.size();a++){

            switch (match.getMatchType()){
                case Constants.SOCCER_TYPE:
                    ratingSecondTemp += secondList.get(a).i_RatingTotSoccer;
                    break;
                case Constants.BASKET_TYPE:
                    ratingSecondTemp += secondList.get(a).i_RatingTotBasket;
                    break;
                case Constants.TENNIS_TYPE:
                    ratingSecondTemp += secondList.get(a).i_RatingTotTennis;
                    break;
                case Constants.VOLLEY_TYPE:
                    ratingSecondTemp += secondList.get(a).i_RatingTotVolley;
                    break;
            }
        }
        rating1 = String.valueOf(value.format(ratingFirstTemp / firstList.size()));
        rating2 = String.valueOf(value.format(ratingSecondTemp / secondList.size()));


        TextViewPlus ratingFirstTeam = (TextViewPlus)findViewById(R.id.ratingFirstTeam);
        TextViewPlus ratingSecondTeam = (TextViewPlus)findViewById(R.id.ratingSecondTeam);

        ratingFirstTeam.setText(rating1);
        ratingSecondTeam.setText(rating2);

        CardView card = (CardView)findViewById(R.id.cardRatingTeam);
        card.setVisibility(View.VISIBLE);
    }

    public void SaveMatchDetail(){
        /*firstTeamStringList = firstTeamList[0].toString();
        for(int i = 1;i<firstTeamList.length;i++){
            firstTeamStringList += "_"+firstTeamList[i].toString();
        }
        secondTeamStringList = secondTeamList[0].toString();
        for(int i = 1;i<secondTeamList.length;i++){
            secondTeamStringList += "_"+secondTeamList[i].toString();
        }*/

        final MatchModel model = new MatchModel();
        model.setIdMatch(match.getIdMatch());
        model.setStartDateUtc(match.getStartDateUtc());
        model.setIdUser(match.getIdUser());
        model.setIsFinish(false);
        model.setResult("");
        model.setLocation(match.getLocation());
        model.setListTotalPlayers(match.getListTotalPlayers());
        model.setListPlayersFirstTeam(firstTeamStringList);
        model.setListPlayersSecondTeam(secondTeamStringList);
        model.setFirstTeamRating(Double.parseDouble(rating1.replace(",",".")));
        model.setSecondTeamRating(Double.parseDouble(rating2.replace(",",".")));
        model.setNumberForTeam(match.getNumberForTeam());
        model.setNameFirstTeam(match.getNameFirstTeam());
        model.setNameSecondTeam(match.getNameSecondTeam());
        model.setMatchType(match.getMatchType());

        JSONObject obj = model.toJSON();

        AndroidNetworking.post(ConfigUrls.BASE_URL + ConfigUrls.MATCH_EDIT)
            .addHeaders("Authorization", "bearer " + Constants.TOKEN)
                .addHeaders("contentType","application/json")
                .addJSONObjectBody(obj)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsParsed(new TypeToken<MatchModel>() {}, new ParsedRequestListener<MatchModel>() {
        @Override
        public void onResponse(final MatchModel match) {
            // do anything with response


            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    try {
                        realm.copyToRealmOrUpdate(model);
                    } catch (Exception e) {
                        Log.e("TAG", "ADD_MATCH: " + e.getMessage(), e);
                    } finally {
                        Log.d("TAG", "ADD_MATCH: FINALLY");
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                ToastCustom toast = new ToastCustom(MatchDetailActivity.this,  getResources().getDrawable(R.drawable.ic_icon_check),getString(R.string.operation_success));
                                toast.show();
                            }
                        }, 1000);

                    }
                }
            });
            realm.close();

            finish();
        }
        @Override
        public void onError(ANError anError) {
            // handle error
            try {
                JSONObject str = new JSONObject(anError.getResponse().toString());
                //Toast.makeText(getBaseContext(), "Errore: " + str.get("Message").toString(), Toast.LENGTH_SHORT).show();
                ToastCustom toast = new ToastCustom(MatchDetailActivity.this, getResources().getDrawable(R.drawable.ic_error_cloud),"Errore: " + str.get("Message").toString());
                toast.show();
            } catch (Exception e) {
                ToastCustom toast = new ToastCustom(MatchDetailActivity.this, getResources().getDrawable(R.drawable.ic_error_cloud),getString(R.string.error_default));
                toast.show();
                Log.e("ErrorPost", e.getMessage());
            }
        }
    });
}

    private void initItems() {
        items = new ArrayList<>();
        items.add(new FABMenuItem("Condividi match", AppCompatResources.getDrawable(this, android.R.drawable.ic_menu_share)));
        items.add(new FABMenuItem("Modifica match", AppCompatResources.getDrawable(this, android.R.drawable.ic_menu_edit)));
        items.add(new FABMenuItem("Item Three", AppCompatResources.getDrawable(this, android.R.drawable.ic_menu_crop)));

    }

}
