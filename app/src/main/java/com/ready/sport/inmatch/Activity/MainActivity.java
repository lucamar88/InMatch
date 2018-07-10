package com.ready.sport.inmatch.Activity;

import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
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
import com.ready.sport.inmatch.RealmClass.MatchModel;
import com.ready.sport.inmatch.RealmClass.UserModel;
import com.ready.sport.inmatch.Tools.NoSwipableViewPager;
import com.ready.sport.inmatch.Tools.ViewPagerAdapter;
import com.ready.sport.inmatch.util.TextViewPlus;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity {


    private NoSwipableViewPager mViewPager;

    private BottomNavigationViewEx navigation;

    private MenuItem prevMenuItem;
    private HomeFragment homeFrg;
    private ListMatchFragment listFrg;
    private PlayersFragment playerFrg;
    private ProfileFragment profileFrg;
    private FloatingActionButton fab;
    private Realm realm;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    fab.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_match:
                    mViewPager.setCurrentItem(1);
                    fab.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_players:
                    mViewPager.setCurrentItem(2);
                    fab.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_player:
                    mViewPager.setCurrentItem(3);
                    fab.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextViewPlus userName = (TextViewPlus)findViewById(R.id.userNameBar);
        realm = Realm.getDefaultInstance();

        UserModel model = realm.where(UserModel.class).findFirst();

        userName.setText(model.getUserName());
        //Initializing viewPager
        mViewPager = (NoSwipableViewPager) findViewById(R.id.viewpager_home);
        mViewPager.setOffscreenPageLimit(4);
        navigation = (BottomNavigationViewEx) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setTextVisibility(false);
        navigation.enableAnimation(true);
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
        fab = (FloatingActionButton) findViewById(R.id.add_player_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, CreatePlayerActivity.class);
                startActivity(in);
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
