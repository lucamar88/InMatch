package com.ready.sport.inmatch.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.ready.sport.inmatch.Fragments.HomeFragment;
import com.ready.sport.inmatch.Fragments.ListMatchFragment;
import com.ready.sport.inmatch.Fragments.PlayersFragment;
import com.ready.sport.inmatch.Fragments.ProfileFragment;
import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.Tools.NoSwipableViewPager;
import com.ready.sport.inmatch.Tools.ViewPagerAdapter;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {


    private NoSwipableViewPager mViewPager;

    private BottomNavigationViewEx navigation;

    private FragmentManager manager = getSupportFragmentManager();

    private MenuItem prevMenuItem;
    private HomeFragment homeFrg;
    private ListMatchFragment listFrg;
    private PlayersFragment playerFrg;
    private ProfileFragment profileFrg;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_match:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_players:
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_player:
                    mViewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing viewPager
        mViewPager = (NoSwipableViewPager) findViewById(R.id.viewpager_home);

        navigation = (BottomNavigationViewEx) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setTextVisibility(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setIconSize(30, 30);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

         //Disable ViewPager Swipe
       mViewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFrg=new HomeFragment();
        listFrg=new ListMatchFragment();
        playerFrg=new PlayersFragment();
        profileFrg=new ProfileFragment();
        adapter.addFragment(homeFrg);
        adapter.addFragment(listFrg);
        adapter.addFragment(playerFrg);
        adapter.addFragment(profileFrg);
        viewPager.setAdapter(adapter);
    }

}
