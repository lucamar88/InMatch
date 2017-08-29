package com.ready.sport.inmatch.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.ready.sport.inmatch.Fragments.BasketFragment;
import com.ready.sport.inmatch.Fragments.SoccerFragment;
import com.ready.sport.inmatch.Fragments.TennisFragment;
import com.ready.sport.inmatch.Fragments.VolleyFragment;
import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.Tools.LockableViewPager;
import com.ready.sport.inmatch.Tools.ViewPagerAdapter;

import static android.R.attr.value;

/**
 * Created by l.martelloni on 29/08/2017.
 */

public class CreatePlayerActivity extends AppCompatActivity {
    private SoccerFragment socFrag;
    private BasketFragment basFrag;
    private TennisFragment tenFrag;
    private VolleyFragment volFrag;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);

        mViewPager = (ViewPager) findViewById(R.id.viewPagerNewPlayer);

        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabsNewPlayer);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.soccer_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.basket_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.tennis_icon);
        tabLayout.getTabAt(3).setIcon(R.drawable.volley_icon);

        AppCompatImageView back = (AppCompatImageView)findViewById(R.id.backBtnCreatePlayer);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle args = new Bundle();
        args.putBoolean("isClick", true);

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

    private void createPlayer(){
        socFrag.getDataSoccer();
    }
}
