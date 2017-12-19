package com.ready.sport.inmatch.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.reflect.TypeToken;
import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.Tools.CustomAdapterPlayers;
import com.ready.sport.inmatch.util.ConfigUrls;
import com.ready.sport.inmatch.util.Constants;
import com.ready.sport.inmatch.util.ToastCustom;

import org.json.JSONObject;

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
        adapter = new CustomAdapterPlayers(realm.where(PlayersModel.class).equalTo("b_ownPlayer",Boolean.FALSE).findAllSorted("IdPlayer", Sort.ASCENDING),getContext(), PlayersFragment.this);
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

        adapter = new CustomAdapterPlayers(realm.where(PlayersModel.class).equalTo("b_ownPlayer",Boolean.FALSE).findAllSorted("IdPlayer", Sort.ASCENDING),getContext(),PlayersFragment.this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public void deletePlayer(int id){
        AndroidNetworking.get(ConfigUrls.BASE_URL + ConfigUrls.PLAYER_DELETE + "?idPlayer=" + id)
                .addHeaders("Authorization", "bearer " + Constants.TOKEN)
                .addHeaders("contentType","application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String idPlayer) {
                        // do anything with response
                        int idToRemove = Integer.parseInt(idPlayer);
                        final PlayersModel model = realm.where(PlayersModel.class).equalTo("IdPlayer",idToRemove).findFirst();

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                try {
                                    model.deleteFromRealm();
                                } catch (Exception e) {
                                    Log.e("TAG", "DELETE_PLAYER: " + e.getMessage(), e);
                                } finally {
                                    Log.d("TAG", "DELETE_PLAYER: FINALLY");

                                    ToastCustom toast = new ToastCustom(getContext(), getResources().getDrawable(R.drawable.ic_icon_check),getString(R.string.operation_success));
                                    toast.show();

                                }
                            }
                        });
                        realm.close();

                    }
                    @Override
                    public void onError(ANError anError) {
                        // handle error
                        try {
                            JSONObject str = new JSONObject(anError.getErrorBody().toString());
                            //Toast.makeText(getBaseContext(), "Errore: " + str.get("Message").toString(), Toast.LENGTH_SHORT).show();
                            ToastCustom toast = new ToastCustom(getContext(), getResources().getDrawable(R.drawable.ic_error_cloud),"Errore: " + str.get("Message").toString());
                            toast.show();
                        } catch (Exception e) {
                            ToastCustom toast = new ToastCustom(getContext(), getResources().getDrawable(R.drawable.ic_error_cloud),getString(R.string.error_default));
                            toast.show();
                            Log.e("ErrorPost", e.getMessage());
                        }
                    }
                });
    }
}
