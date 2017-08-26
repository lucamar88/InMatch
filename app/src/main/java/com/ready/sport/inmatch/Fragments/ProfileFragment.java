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

import com.ready.sport.inmatch.R;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class ProfileFragment extends Fragment {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_layout, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewPagerProfile);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabsProfile);
        tabLayout.setupWithViewPager(mViewPager);
        return rootView;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    SoccerFragment tab1 = new SoccerFragment();
                    return tab1;
                case 1:
                    BasketFragment tab2 = new BasketFragment();
                    return tab2;
                case 2:
                    TennisFragment tab3 = new TennisFragment();
                    return tab3;
                case 3:
                    VolleyFragment tab4 = new VolleyFragment();
                    return tab4;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CALCIO";
                case 1:
                    return "BASKET";
                case 2:
                    return "TENNIS";
                case 3:
                    return "VOLLEY";
            }
            return null;
        }
    }
}
