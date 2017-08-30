package com.ready.sport.inmatch.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.PlayerCardMatchModel;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.Tools.CustomAdapterPlayers;
import com.ready.sport.inmatch.Tools.CustomAdaptersPlayersMatch;
import com.ready.sport.inmatch.util.AdapterInterface;
import com.ready.sport.inmatch.util.Constants;
import com.ready.sport.inmatch.util.TextViewPlus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class CreateMatchActivity extends AppCompatActivity implements AdapterInterface {
    private AppBarLayout bar;
    private TextViewPlus title;
    private TextViewPlus numSelectPl;

    private Realm realm;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static RealmResults<PlayersModel> data;
    private ArrayList<Integer> listaPlayer = new ArrayList<>();
    private static ArrayList<PlayerCardMatchModel> dataFin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);
        bar = (AppBarLayout)findViewById(R.id.appbarCreateMatch);
        title = (TextViewPlus)findViewById(R.id.titleNewMatch);
        numSelectPl = (TextViewPlus)findViewById(R.id.numberSelectPlayer);
        int type;
        Bundle extras = getIntent().getExtras();

        try {
            if(extras == null) {
                type= Constants.SOCCER_TYPE;
            } else {
                type= extras.getInt(Constants.MATCH_TYPE);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            type= Constants.SOCCER_TYPE;
        }
        setColorBar(type);
        AppCompatImageView back = (AppCompatImageView)findViewById(R.id.backBtnCreateMatch);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        NestedScrollView scroller = (NestedScrollView) findViewById(R.id.scrollNewMatch);

        realm= Realm.getDefaultInstance();

        recyclerView = (RecyclerView) findViewById(R.id.listPlayerNewMatch);
        //recyclerView.setHasFixedSize(true);
        dataFin = new ArrayList<PlayerCardMatchModel>();
        data = realm.where(PlayersModel.class).findAll();
        for (PlayersModel pl : data) {
            PlayerCardMatchModel plFin = new PlayerCardMatchModel();
            plFin.MatchType = type;
            plFin.PlayerId = pl.getIdPlayer();
            plFin.NamePlayer = pl.getName();
            plFin.SurnamePlayer = pl.getSurName();
            if(type == Constants.SOCCER_TYPE){
                plFin.RatingPlayer = pl.getRatingSoccer();
                plFin.RoleSoccer = "CENTROVAMPISTA";
            }else if(type == Constants.BASKET_TYPE){
                plFin.RatingPlayer = pl.getRatingBasket();
            }else if(type == Constants.TENNIS_TYPE){
                plFin.RatingPlayer = pl.getRatingTennis();
            }else if(type == Constants.VOLLEY_TYPE){
                plFin.RatingPlayer = pl.getRatingVolley();
            }
            dataFin.add(plFin);
        }
        setUpRecyclerView();
    }

    public void setColorBar(int index){
        Toast.makeText(getBaseContext(),"Tipo:"+index, Toast.LENGTH_LONG).show();
        switch (index){
            case Constants.SOCCER_TYPE:
                bar.setBackgroundColor(getResources().getColor(R.color.soccerColor));
                title.setText("Nuova partita - Calcio");
                return;
            case Constants.BASKET_TYPE:
                bar.setBackgroundColor(getResources().getColor(R.color.basketColor));
                title.setText("Nuova partita - Basket");
                return;
            case Constants.TENNIS_TYPE:
                bar.setBackgroundColor(getResources().getColor(R.color.tennisColor));
                title.setText("Nuova partita - Tennis");
                return;
            case Constants.VOLLEY_TYPE:
                bar.setBackgroundColor(getResources().getColor(R.color.volleyColor));
                title.setText("Nuova partita - Volley");
                return;
        }
    }

    private void setUpRecyclerView() {
        adapter = new CustomAdaptersPlayersMatch(this,dataFin);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        /*TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerView);*/

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    @Override
    public void setPlayerForMatch(int idPlayer,int type){
        /*if(type == 0){
            listaPlayer.remove(idPlayer);
        }else{
            //listaPlayer.add(idPlayer);
        }*/
        //numSelectPl.setText(String.valueOf(listaPlayer.size()));
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}


