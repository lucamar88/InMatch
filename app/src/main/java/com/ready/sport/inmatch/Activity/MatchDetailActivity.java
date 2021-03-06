package com.ready.sport.inmatch.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
//import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.reflect.TypeToken;
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

import uk.co.markormesher.android_fab.FloatingActionButton;
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter;
import uk.co.markormesher.android_fab.SpeedDialMenuItem;

public class MatchDetailActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private AppBarLayout bar;
    private ButtonPlus btn;
    private AppCompatImageView btnCheck;
    private Context baseContext;
    private Activity activity;
    private RelativeLayout btnTeam1;
    private RelativeLayout btnTeam2;
    private ButtonPlus btnAddGoal1;
    private ButtonPlus btnAddGoal2;
    private ButtonPlus btnLessGoal1;
    private ButtonPlus btnLessGoal2;

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


   private SpeedDialMenuAdapter speedDialMenuAdapter = new SpeedDialMenuAdapter() {
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
           }else if(i == 2){
               LayoutInflater inflater = activity.getLayoutInflater();

               View titleView = inflater.inflate(R.layout.layout_title_alert, null);
               new AlertDialog.Builder(activity, R.style.AlertDialogCustom)
                       //.setTitle(activity.getString(R.string.delete))
                       .setCustomTitle(titleView)
                       .setMessage(activity.getString(R.string.delete_match))
                       .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                           public void onClick(DialogInterface dialog, int whichButton) {

                               deleteMatch();

                           }})
                       .setNegativeButton(android.R.string.no, null).show();


           }else if(i==3){
               btnTeam1.setVisibility(View.VISIBLE);
               btnTeam2.setVisibility(View.VISIBLE);
           }
           return true;
       }
       @NotNull
       @Override
       public SpeedDialMenuItem getMenuItem(Context context, int i) {
           SpeedDialMenuItem speedDialMenuItem = null;
           switch (i){
               case 0:
                   speedDialMenuItem = new SpeedDialMenuItem(context, getResources().getDrawable(R.drawable.icon_fab_share), getResources().getString(R.string.share_match_fab));
                   break;
               case 1:
                   speedDialMenuItem = new SpeedDialMenuItem(context, getResources().getDrawable(R.drawable.icon_fab_edit), getResources().getString(R.string.edit_match_fab));
                   break;
               case 2:
                   speedDialMenuItem = new SpeedDialMenuItem(context, getResources().getDrawable(R.drawable.icon_fab_delete), getResources().getString(R.string.delete_match_fab));
                   break;
               case 3:
                   speedDialMenuItem = new SpeedDialMenuItem(context, getResources().getDrawable(R.drawable.team2_icon_rid), getResources().getString(R.string.insert_result_fab));
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
        baseContext = getBaseContext();
        activity = this;

        realm= Realm.getDefaultInstance();
        bar = (AppBarLayout)findViewById(R.id.appbarMatchDetail);

        btn = (ButtonPlus)findViewById(R.id.btn_generate_teams);

        btnCheck = (AppCompatImageView)findViewById(R.id.saveMatchDetail);

        fab = (FloatingActionButton)findViewById(R.id.fab);

        btnTeam1 = (RelativeLayout)findViewById(R.id.btn_goal_team_1);
        btnTeam2 = (RelativeLayout)findViewById(R.id.btn_goal_team_2);
        btnAddGoal1 = (ButtonPlus)findViewById(R.id.add_goals_teams_1);
        btnAddGoal2 = (ButtonPlus)findViewById(R.id.add_goals_teams_2);
        btnLessGoal1  = (ButtonPlus)findViewById(R.id.less_goals_teams_1);
        btnLessGoal2  = (ButtonPlus)findViewById(R.id.less_goals_teams_2);
        /*final FABRevealMenu fabMenu = findViewById(R.id.fabMenu);

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
                            deleteMatch();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        fab.setSpeedDialMenuAdapter(speedDialMenuAdapter);

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
            Toast.makeText(this,getResources().getString(R.string.error_default),Toast.LENGTH_SHORT).show();
            finish();
        }

        if(IdMatch != 0){
            match = realm.where(MatchModel.class).equalTo("IdMatch",IdMatch).findFirst();
            //setColorFab();
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
            boolean isDeleted = false;
            for(String str:firstTeamList){
                int id = Integer.parseInt(str);
                PlayersModel player = realm.where(PlayersModel.class).equalTo("IdPlayer", id ).findFirst();
                if(player == null){
                    player = new PlayersModel();
                    isDeleted = true;
                }
                firstList.add(player);
                totList.add(player);
            }

            for(String str:secondTeamList){
                int id = Integer.parseInt(str);
                PlayersModel player = realm.where(PlayersModel.class).equalTo("IdPlayer", id ).findFirst();
                if(player == null){
                    player = new PlayersModel();
                    isDeleted = true;
                }
                secondList.add(player);
                totList.add(player);
            }

            if(isDeleted){
                ToastCustom toast = new ToastCustom(MatchDetailActivity.this, getResources().getDrawable(R.drawable.ic_error_cloud),getResources().getString(R.string.error_players_deleted));
                toast.show();
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
                    if(pla1 == null)pla1 = new PlayersModel();
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
                        if(pla1 == null)pla1 = new PlayersModel();
                        firstList.add(pla1);
                    }
                }

                for (int i = 0;i<secondTeamList.length ;i++
                        ) {
                    if(secondTeamList[i] != ""){
                        int id = Integer.parseInt(secondTeamList[i]);
                        PlayersModel pla1 = realm.where(PlayersModel.class).equalTo("IdPlayer", id ).findFirst();
                        if(pla1 == null)pla1 = new PlayersModel();
                        secondList.add(pla1);
                    }
                }
                setUpRecyclerView();
                ToastCustom toast = new ToastCustom(MatchDetailActivity.this, getResources().getDrawable(R.drawable.ic_icon_check),getString(R.string.teams_generated));
                toast.show();
                SaveMatchDetail(false);
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveMatchDetail(true);
            }
        });
    }

    public void setColorFab(){
        switch (match.getMatchType()){
            case Constants.SOCCER_TYPE:
                fab.setBackgroundColor(getResources().getColor(R.color.soccerColor));

                return;
            case Constants.BASKET_TYPE:
                fab.setBackgroundColor(getResources().getColor(R.color.basketColor));

                return;
            case Constants.TENNIS_TYPE:
                fab.setBackgroundColor(getResources().getColor(R.color.tennisColor));

                return;
            case Constants.VOLLEY_TYPE:
                fab.setBackgroundColor(getResources().getColor(R.color.volleyColor));

                return;
        }
    }

    public void onClickWhatsApp() {

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = ConfigUrls.getBASE_URL()+"Home/Index?id="+match.getIdMatch();

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

    public void SaveMatchDetail(boolean isToClose){
        final boolean isClose = isToClose;
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
                                if(isClose){
                                    ToastCustom toast = new ToastCustom(MatchDetailActivity.this,  getResources().getDrawable(R.drawable.ic_icon_check),getString(R.string.operation_success));
                                    toast.show();
                                }

                            }
                        }, 1000);

                    }
                }
            });
            realm.close();
            if(isClose){finish();}

        }
        @Override
        public void onError(ANError anError) {
            // handle error
            try {
                JSONObject str = new JSONObject(anError.getResponse().toString());
                //Toast.makeText(getBaseContext(), "Errore: " + str.get("Message").toString(), Toast.LENGTH_SHORT).show();
                ToastCustom toast = new ToastCustom(activity, getResources().getDrawable(R.drawable.ic_error_cloud),"Errore: " + str.get("Message").toString());
                toast.show();
            } catch (Exception e) {
                ToastCustom toast = new ToastCustom(activity, getResources().getDrawable(R.drawable.ic_error_cloud),getString(R.string.error_default));
                toast.show();
                Log.e("ErrorPost", e.getMessage());
            }
        }
    });
}


    private void deleteMatch(){
        AndroidNetworking.get(ConfigUrls.BASE_URL + ConfigUrls.MATCH_DELETE + "?idMatch=" + match.getIdMatch())
                .addHeaders("Authorization", "bearer " + Constants.TOKEN)
                .addHeaders("contentType","application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String idMatch) {
                        // do anything with response
                        int idToRemove = Integer.parseInt(idMatch);
                        final MatchModel model = realm.where(MatchModel.class).equalTo("IdMatch",idToRemove).findFirst();

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                try {
                                    model.deleteFromRealm();
                                } catch (Exception e) {
                                    Log.e("TAG", "DELETE_MATCH: " + e.getMessage(), e);
                                } finally {
                                    Log.d("TAG", "DELETE_MATCH: FINALLY");

                                    ToastCustom toast = new ToastCustom(activity, getResources().getDrawable(R.drawable.ic_icon_check),getString(R.string.operation_success));
                                    toast.show();

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

}
