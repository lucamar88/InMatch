package com.ready.sport.inmatch.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.Tools.CustomAdapterPlayers;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class PlayersFragment extends Fragment {

     private Realm realm;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<PlayersModel> data;
    static View.OnClickListener myOnClickListener;

     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     View rootView = inflater.inflate(R.layout.fragment_players_layout, container, false);

     realm= Realm.getDefaultInstance();

         recyclerView = (RecyclerView) rootView.findViewById(R.id.listPlayerRecycle);
         //recyclerView.setHasFixedSize(true);
         data = new ArrayList<PlayersModel>();
         //Creo dati
         realm.executeTransaction(new Realm.Transaction() {
             @Override
             public void execute(Realm realm) {
                 for(int i = 0; i<10 ;i++){
                     PlayersModel l = realm.createObject(PlayersModel.class);
                     l.setSurName("Martelloni");
                     l.setName("Luca");
                     l.setRatingSoccer(7.5);
                     l.setRatingBasket(5.5);
                     l.setRatingTennis(4.5);
                     l.setRatingVolley(3.5);
                 }


             }
         });


         /*layoutManager = new LinearLayoutManager(getActivity());
         recyclerView.setLayoutManager(layoutManager);
         recyclerView.setItemAnimator(new DefaultItemAnimator());

         myOnClickListener = new MyOnClickListener(getActivity());

         adapter = new CustomAdapterPlayers(data);
         recyclerView.setAdapter(adapter);*/
         setUpRecyclerView();
     return rootView;
}
    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Click", Toast.LENGTH_LONG);
        }


    }


    private class TouchHelperCallback extends ItemTouchHelper.SimpleCallback {

        TouchHelperCallback() {
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            //DataHelper.deleteItemAsync(realm, viewHolder.getItemId());
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }
    }

    private void setUpRecyclerView() {
        adapter = new CustomAdapterPlayers(realm.where(PlayersModel.class).findAll(),getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        /*TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerView);*/

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }
}
