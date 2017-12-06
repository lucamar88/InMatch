package com.ready.sport.inmatch.Activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.franmontiel.fullscreendialog.FullScreenDialogFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ready.sport.inmatch.Fragments.BasketFragment;
import com.ready.sport.inmatch.Fragments.LoginFragment;
import com.ready.sport.inmatch.Fragments.ProgressFragment;
import com.ready.sport.inmatch.Fragments.SoccerFragment;
import com.ready.sport.inmatch.Fragments.TennisFragment;
import com.ready.sport.inmatch.Fragments.VolleyFragment;
import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.BasketModel;
import com.ready.sport.inmatch.RealmClass.CreatePlayerClass;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.RealmClass.SoccerModel;
import com.ready.sport.inmatch.RealmClass.TennisModel;
import com.ready.sport.inmatch.RealmClass.VolleyModel;
import com.ready.sport.inmatch.Tools.LockableViewPager;
import com.ready.sport.inmatch.Tools.NoSwipableViewPager;
import com.ready.sport.inmatch.Tools.ViewPagerAdapter;
import com.ready.sport.inmatch.util.ConfigUrls;
import com.ready.sport.inmatch.util.Constants;
import com.ready.sport.inmatch.util.EditTextPlus;
import com.ready.sport.inmatch.util.TextViewPlus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Date;

import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncManager;

import static android.R.attr.value;

/**
 * Created by l.martelloni on 29/08/2017.
 */

public class CreatePlayerActivity extends AppCompatActivity {
    private SoccerFragment socFrag;
    private BasketFragment basFrag;
    private TennisFragment tenFrag;
    private VolleyFragment volFrag;
    private EditTextPlus name;
    private EditTextPlus surName;
    private Realm realm;
    private PlayersModel model;
    private ProgressFragment frag;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private NoSwipableViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);
        realm= Realm.getDefaultInstance();
        mViewPager = (NoSwipableViewPager) findViewById(R.id.viewPagerNewPlayer);
        mViewPager.setOffscreenPageLimit(4);
        setupViewPager(mViewPager);


        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabsNewPlayer);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.soccer_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.basket_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.tennis_icon);
        tabLayout.getTabAt(3).setIcon(R.drawable.volley_icon);
        mViewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        name = (EditTextPlus) findViewById(R.id.nameNewPlayer);
        surName = (EditTextPlus) findViewById(R.id.surnameNewPlayer);

        AppCompatImageView back = (AppCompatImageView)findViewById(R.id.backBtnCreatePlayer);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        AppCompatImageView add = (AppCompatImageView)findViewById(R.id.add_player_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
                //createPlayer();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle args = new Bundle();
        args.putBoolean("isClick", true);

        socFrag=new SoccerFragment();
        basFrag=new BasketFragment();
        tenFrag=new TennisFragment();
        volFrag=new VolleyFragment();

        socFrag.setArguments(args);
        basFrag.setArguments(args);
        tenFrag.setArguments(args);
        volFrag.setArguments(args);

        adapter.addFragment(socFrag);
        adapter.addFragment(basFrag);
        adapter.addFragment(tenFrag);
        adapter.addFragment(volFrag);
        viewPager.setAdapter(adapter);
    }

    private void validate() {

        // Reset errors.
        name.setError(null);
        surName.setError(null);

        // Store values at the time of the login attempt.
        String nameStr = name.getText().toString();
        String surnameStr = surName.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(nameStr)) {
            Toast.makeText(this, getString(R.string.error_name_player), Toast.LENGTH_SHORT).show();
            //name.setError(getString(R.string.error_name_player));
            focusView = name;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            createPlayer();
        }
    }

    private void createPlayer(){
        //Loading Fragment

        frag = new ProgressFragment();
        frag.show(getSupportFragmentManager(), "OpenPopup");
        frag.setCancelable(false);

        SoccerModel soccerModel = socFrag.getDataSoccer();
        BasketModel basketModel = basFrag.getDataBasket();
        TennisModel tennisModel = tenFrag.getDataTennis();
        VolleyModel volleyModel = volFrag.getDataVolley();

        model = CreatePlayerClass.setPlayerModel(false, soccerModel,basketModel,tennisModel,volleyModel, name.getText().toString(), surName.getText().toString() );
        JSONObject obj = model.toJSON();

        AndroidNetworking.post(ConfigUrls.BASE_URL + ConfigUrls.PLAYER_CREATE)
                .addHeaders("Authorization", "bearer " + Constants.TOKEN)
                .addHeaders("contentType","application/json")
                .addJSONObjectBody(obj)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsParsed(new TypeToken<PlayersModel>() {}, new ParsedRequestListener<PlayersModel>() {
                    @Override
                    public void onResponse(PlayersModel user) {
                        // do anything with response
                        model.setIdPlayer(user.IdPlayer);

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                try {
                                    realm.copyToRealmOrUpdate(model);
                                } catch (Exception e) {
                                    Log.e("TAG", "ADD_USER: " + e.getMessage(), e);
                                } finally {
                                    Log.d("TAG", "ADD_USER: FINALLY");
                                    Toast.makeText(getBaseContext(), "Operazione eseguita", Toast.LENGTH_SHORT).show();
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
                            frag.dismiss();
                            JSONObject str = new JSONObject(anError.getErrorBody().toString());
                            Toast.makeText(getBaseContext(), "Errore: " + str.get("Message").toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("ErrorPost", e.getMessage());
                        }
                    }
                });

    }
    @Override
    public void onBackPressed() {
        if (frag != null && frag.isAdded()) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
    }


}
