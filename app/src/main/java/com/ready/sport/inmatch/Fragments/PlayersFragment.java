package com.ready.sport.inmatch.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ready.sport.inmatch.Activity.SignLoginActivity;
import com.ready.sport.inmatch.R;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class PlayersFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_players_layout, container, false);

    return rootView;
}


}
