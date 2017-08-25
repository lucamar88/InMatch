package com.ready.sport.inmatch.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ready.sport.inmatch.R;

/**
 * Created by l.martelloni on 25/08/2017.
 */

public class SignFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signin, container, false);
        return rootView;
    }
}
