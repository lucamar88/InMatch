package com.ready.sport.inmatch.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.util.CountDownView;
import com.ready.sport.inmatch.util.TimerListener;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class ListMatchFragment extends Fragment implements TimerListener{
    private CountDownView cdv;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_match_layout, container, false);
        cdv = (CountDownView) rootView.findViewById(R.id.countdownview);
        cdv.setInitialTime(100005000); // Initial time of 30 seconds.
        cdv.start();
        cdv.setListener(this);
        return rootView;
    }

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cdv.setListener(this);
    }*/

    @Override
    public void timerElapsed() {
        //Do something here
        cdv.stop();
        cdv.reset();
    }
}
