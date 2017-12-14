package com.ready.sport.inmatch.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.RealmClass.UserModel;
import com.ready.sport.inmatch.util.ConfigUrls;
import com.ready.sport.inmatch.util.Constants;
import com.ready.sport.inmatch.util.ToastCustom;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import org.json.JSONArray;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.Sort;


public class SplashActivity extends AwesomeSplash {
    private Realm realm;
    private UserModel model;
    private String token;
    private PlayersModel pl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        model = realm.where(UserModel.class).findFirst();
        token = model != null ? model.getToken(): "" ;
    }

    @Override
    public void initSplash(ConfigSplash configSplash) {

   /* you don't have to override every property */

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorSecond); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1500); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP


        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo

        configSplash.setLogoSplash(R.drawable.ic_splash); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.SlideInUp); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


        //Customize Path
        //configSplash.setPathSplash(Constants.DROID_LOGO); //set path String
        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        //configSplash.setAnimPathStrokeDrawingDuration(3000);
        //configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        //configSplash.setPathSplashStrokeColor(R.color.fillColor); //any color you want form colors.xml
        //configSplash.setAnimPathFillingDuration(3000);
        //configSplash.setPathSplashFillColor(R.color.fillColor); //path object filling color


        //Customize Title
        configSplash.setTitleSplash("");
        configSplash.setTitleTextColor(R.color.fillColor);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
        //configSplash.setTitleFont("fonts/myfont.ttf"); //provide string to your font located in assets/fonts/

    }

    @Override
    public void animationsFinished() {

        //transit to another activity here
        //or do whatever you want
        if(token != ""){
            Constants.TOKEN = token;
            GetDataUser();
//            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//            overridePendingTransition(R.anim.enter, R.anim.exit);
//            this.finish();
        }else{
            startActivity(new Intent(SplashActivity.this, SignLoginActivity.class));
            overridePendingTransition(R.anim.enter, R.anim.exit);
            this.finish();
        }

    }
    public void GetDataUser() {
        AndroidNetworking.get(ConfigUrls.BASE_URL + ConfigUrls.PLAYER_GET_ALL)
                .addHeaders("Authorization", "bearer " + token)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                try{
                                    JSONObject player = response.getJSONObject(i);
                                    Gson gson = new GsonBuilder().create();
                                    pl = gson.fromJson(player.toString(), PlayersModel.class);

                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            try {
                                                realm.copyToRealmOrUpdate(pl);
                                            } catch (Exception e) {
                                                Log.e("TAG", "ADD_USER: " + e.getMessage(), e);
                                            } finally {
                                                Log.d("TAG", "ADD_USER: FINALLY");

                                            }

                                        }
                                    });
                                }catch(Exception e){
                                    Log.e("ErrorParse", e.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            Log.e("ErrorParse", e.getMessage());
                        }
                        realm.close();
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                        finish();
                        // do anything with response
                    }

                    @Override
                    public void onError(ANError error) {
                        try {
                            JSONObject str = new JSONObject(error.getErrorBody().toString());
                            startActivity(new Intent(SplashActivity.this, SignLoginActivity.class));
                            ToastCustom toast = new ToastCustom(getBaseContext(), getResources().getDrawable(R.drawable.ic_error_cloud),"Errore: " + str.get("Message").toString());
                            toast.show();
                            //JSONObject str = new JSONObject(error.getErrorBody().toString());
                            //Toast.makeText(getBaseContext(), "Errore: " + str.get("error_description"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            ToastCustom toast = new ToastCustom(getBaseContext(), getResources().getDrawable(R.drawable.ic_error_cloud),getString(R.string.error_default));
                            toast.show();
                            Log.e("ErrorPost", e.getMessage());
                        }
                        // handle error
                    }
                });
    }
}