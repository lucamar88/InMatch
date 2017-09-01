package com.ready.sport.inmatch.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.util.CountDownView;
import com.ready.sport.inmatch.util.TimerListener;

import cn.iwgang.countdownview.CountdownView;
import cn.iwgang.countdownview.DynamicConfig;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class ListMatchFragment extends Fragment  implements CountdownView.OnCountdownEndListener {
    private CountdownView codv;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_match_layout, container, false);

        codv = (CountdownView)rootView.findViewById(R.id.cv_countdownViewTest1);
        DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
        dynamicConfigBuilder.setShowDay(false);
        dynamicConfigBuilder.setShowHour(true);
        codv.dynamicShow(dynamicConfigBuilder.build());
        codv.start(10000); // Millisecond

        return rootView;
    }
   @Override
   public void onEnd(CountdownView cv) {
       Object tag = cv.getTag();
       if (null != tag) {
           Log.i("wg", "tag = " + tag.toString());
       }
   }
}
