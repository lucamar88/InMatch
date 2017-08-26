package com.ready.sport.inmatch.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.ready.sport.inmatch.Activity.CreateMatchActivity;
import com.ready.sport.inmatch.Activity.SignLoginActivity;
import com.ready.sport.inmatch.Activity.SplashActivity;
import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.util.AutoCompleteTextViewPlus;
import com.ready.sport.inmatch.util.ButtonPlus;
import com.ready.sport.inmatch.util.EditTextPlus;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class HomeFragment extends Fragment {
    private CardView card1;
    private CardView card2;
    private CardView card3;
    private CardView card4;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        card1 = (CardView)rootView.findViewById(R.id.cardSoccer);
        card2 = (CardView)rootView.findViewById(R.id.cardBasket);
        card3 = (CardView)rootView.findViewById(R.id.cardTennis);
        card4 = (CardView)rootView.findViewById(R.id.cardVolley);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateMatchActivity.class));
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateMatchActivity.class));
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateMatchActivity.class));
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateMatchActivity.class));
            }
        });
        return rootView;
    }

}
