package com.ready.sport.inmatch.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ready.sport.inmatch.R;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class ListMatchFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_match_layout, container, false);

        return rootView;
    }
}
