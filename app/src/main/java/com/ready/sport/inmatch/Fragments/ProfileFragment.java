package com.ready.sport.inmatch.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ready.sport.inmatch.Activity.CreatePlayerActivity;
import com.ready.sport.inmatch.Activity.MainActivity;
import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.Tools.LockableViewPager;
import com.ready.sport.inmatch.Tools.ViewPagerAdapter;

import io.realm.Realm;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class ProfileFragment extends Fragment {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private SoccerFragment socFrag;
    private BasketFragment basFrag;
    private TennisFragment tenFrag;
    private VolleyFragment volFrag;
    private int IdPlayerOwn;
    private Realm realm;
    private FloatingActionButton floatBtn;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private LockableViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm= Realm.getDefaultInstance();
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_layout, container, false);
        //mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        IdPlayerOwn = realm.where(PlayersModel.class).equalTo("b_ownPlayer", true).findFirst().IdPlayer;
        // Set up the ViewPager with the sections adapter.
        mViewPager = (LockableViewPager) rootView.findViewById(R.id.viewPagerProfile);

        mViewPager.setSwipeable(false);
        mViewPager.setOffscreenPageLimit(4);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabsProfile);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.soccer_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.basket_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.tennis_icon);
        tabLayout.getTabAt(3).setIcon(R.drawable.volley_icon);

        floatBtn = (FloatingActionButton)rootView.findViewById(R.id.edit_player_own);

        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getActivity(), CreatePlayerActivity.class);
                in.putExtra("IdPlayer",IdPlayerOwn);
                startActivity(in);
            }
        });

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


    }
    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Bundle args = new Bundle();
        args.putBoolean("isClick", false);
        args.putInt("idPlayer", IdPlayerOwn);
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

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

    }
}
