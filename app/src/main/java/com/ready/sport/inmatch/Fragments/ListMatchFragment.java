package com.ready.sport.inmatch.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.MatchModel;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.Tools.CustomAdapterListMatch;
import com.ready.sport.inmatch.Tools.CustomAdapterPlayers;
import com.ready.sport.inmatch.util.Constants;
import com.ready.sport.inmatch.util.CountDownView;
import com.ready.sport.inmatch.util.TextViewPlus;
import com.ready.sport.inmatch.util.TimerListener;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cn.iwgang.countdownview.CountdownView;
import cn.iwgang.countdownview.DynamicConfig;
import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class ListMatchFragment extends Fragment implements CountdownView.OnCountdownEndListener {
    private CountdownView codv;
    private Realm realm;

    private static RecyclerView.Adapter adapter;
    private static RecyclerView recyclerView;
    private TextViewPlus tv_countdown;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_match_layout, container, false);
        tv_countdown = (TextViewPlus) rootView.findViewById(R.id.tv_countdown);
//        codv = (CountdownView) rootView.findViewById(R.id.cv_countdownViewTest1);
//        DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
//        dynamicConfigBuilder.setShowDay(false);
//        dynamicConfigBuilder.setShowHour(true);
//        codv.dynamicShow(dynamicConfigBuilder.build());
//        codv.start(10000); // Millisecond
        Calendar start_calendar = Calendar.getInstance();
        Calendar end_calendar = Calendar.getInstance();
        end_calendar.set(2017,11,15);
        long start_millis = start_calendar.getTimeInMillis(); //get the start time in milliseconds
        long end_millis = end_calendar.getTimeInMillis(); //get the end time in milliseconds
        long total_millis = (end_millis - start_millis); //total time in milliseconds



        CountDownTimer cdt = new CountDownTimer(total_millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                millisUntilFinished -= TimeUnit.DAYS.toMillis(days);

                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                tv_countdown.setText(days + " g " + hours + " h " + minutes + " m " + seconds + " s"); //You can compute the millisUntilFinished on hours/minutes/seconds
            }

            @Override
            public void onFinish() {
                tv_countdown.setText("Finish!");
            }
        };
        cdt.start();

        realm = Realm.getDefaultInstance();
        //Creo dati

        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    for (int i = 1; i < 10; i++) {
                        MatchModel model = new MatchModel();
                        model.setIdMatch(i + 10);
                        model.setIsFinish(false);
                        model.setMatchType(Constants.TENNIS_TYPE);
                        model.setNameFirstTeam("Squadra1");
                        model.setNameSecondTeam("Squadra2");
                        model.setResult("4_6");
                        model.setStartDateUtc("2017-07-13T08:51:47.503");


                        realm.copyToRealmOrUpdate(model);

                    }

                } catch (Exception e) {
                    Log.e("TAG", "ADD_USER: " + e.getMessage(), e);
                } finally {
                    Log.d("TAG", "ADD_USER: FINALLY");

                }

            }
        });
        realm.close();*/
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listMatch);
        setUpRecyclerView();
        return rootView;
    }

    @Override
    public void onEnd(CountdownView cv) {
        Object tag = cv.getTag();
        if (null != tag) {
            Log.i("wg", "tag = " + tag.toString());
        }
    }

    private void setUpRecyclerView() {
        adapter = new CustomAdapterListMatch(realm.where(MatchModel.class).findAll(), getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        /*TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerView);*/

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter = new CustomAdapterListMatch(realm.where(MatchModel.class).findAll(), getContext());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.smoothScrollToPosition(0);
    }
}
