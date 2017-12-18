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
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Luca Martelloni on 26/08/2017.
 */

public class PlayersFragment extends Fragment {

    private Realm realm;

    private static RecyclerView.Adapter adapter;
    private static RecyclerView recyclerView;
    private static ArrayList<PlayersModel> data;

     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     View rootView = inflater.inflate(R.layout.fragment_players_layout, container, false);

     realm= Realm.getDefaultInstance();

         recyclerView = (RecyclerView) rootView.findViewById(R.id.listPlayerRecycle);
         //recyclerView.setHasFixedSize(true);
         data = new ArrayList<PlayersModel>();
         //Creo dati


         /*realm.executeTransaction(new Realm.Transaction() {
             @Override
             public void execute(Realm realm) {
                 for(int i = 1; i<10 ;i++){
                     PlayersModel model = new PlayersModel();
                     model.setSurName("Martelloni"+i);
                     model.setName("Luca");
                     model.setIdPlayer(i+30);
                     model.setRatingSoccer(7.5);
                     model.setRatingBasket(5.5);
                     model.setRatingTennis(4.5);
                     model.setRatingVolley(3.5);

                     //realm.createObject(PlayersModel.class,i+30);
                     realm.copyToRealmOrUpdate(model);

                 }


             }
         });*/



         setUpRecyclerView();
     return rootView;
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
        adapter = new CustomAdapterPlayers(realm.where(PlayersModel.class).equalTo("b_ownPlayer",Boolean.FALSE).findAllSorted("IdPlayer", Sort.ASCENDING),getContext());
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
    @Override
    public void onResume(){
        super.onResume();

        adapter = new CustomAdapterPlayers(realm.where(PlayersModel.class).equalTo("b_ownPlayer",Boolean.FALSE).findAllSorted("IdPlayer", Sort.ASCENDING),getContext());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
