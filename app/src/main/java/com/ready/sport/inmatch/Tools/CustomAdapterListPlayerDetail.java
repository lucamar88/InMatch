package com.ready.sport.inmatch.Tools;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.util.Constants;
import com.ready.sport.inmatch.util.PopupDialogFragmentBasket;
import com.ready.sport.inmatch.util.PopupDialogFragmentSoccer;
import com.ready.sport.inmatch.util.PopupDialogFragmentTennis;
import com.ready.sport.inmatch.util.PopupDialogFragmentVolley;
import com.ready.sport.inmatch.util.TextViewPlus;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by l.martelloni on 30/11/2017.
 */

public class CustomAdapterListPlayerDetail extends RealmRecyclerViewAdapter<PlayersModel ,CustomAdapterListPlayerDetail.MyViewHolder> {
    private Context mContext;
    private int mTypeMatch;

    public CustomAdapterListPlayerDetail(OrderedRealmCollection<PlayersModel> data, int typeMatch,Context context) {
        super(data, true);
        this.mContext = context;
        this.mTypeMatch = typeMatch;
        setHasStableIds(true);
    }



    @Override
    public CustomAdapterListPlayerDetail.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_player_detail_match, parent, false);
        return new CustomAdapterListPlayerDetail.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomAdapterListPlayerDetail.MyViewHolder holder, int position) {
        final PlayersModel obj = getItem(position);
        String rating = "";

        switch (mTypeMatch){
            case Constants.SOCCER_TYPE:
                rating = String.valueOf(obj.i_RatingTotSoccer);
                break;
            case Constants.BASKET_TYPE:
                rating = String.valueOf(obj.i_RatingTotBasket);
                break;
            case Constants.TENNIS_TYPE:
                rating = String.valueOf(obj.i_RatingTotTennis);
                break;
            case Constants.VOLLEY_TYPE:
                rating = String.valueOf(obj.i_RatingTotVolley);
                break;

        }

        holder.ratingPlayer.setText(rating);

        holder.namePlayer.setText(obj.s_Name + " " + obj.s_Surename);

    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).IdPlayer;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextViewPlus ratingPlayer ;
        TextViewPlus namePlayer;

        MyViewHolder(View view) {
            super(view);
            ratingPlayer = (TextViewPlus) view.findViewById(R.id.ratingPlayerItemDetail);
            namePlayer = (TextViewPlus)view.findViewById(R.id.namePlayerItemDetail);
        }
    }
}