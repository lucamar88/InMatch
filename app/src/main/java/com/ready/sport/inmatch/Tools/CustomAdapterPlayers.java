package com.ready.sport.inmatch.Tools;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.util.PopupDialogFragment;
import com.ready.sport.inmatch.util.TextViewPlus;


import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by l.martelloni on 28/08/2017.
 */

public class CustomAdapterPlayers extends RealmRecyclerViewAdapter<PlayersModel, CustomAdapterPlayers.MyViewHolder> {
    private Context mContext;

    public CustomAdapterPlayers(OrderedRealmCollection<PlayersModel> data, Context context) {
        super(data, true);
        this.mContext = context;
        setHasStableIds(true);
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.card_players_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final PlayersModel obj = getItem(position);

        holder.soccerRating.setText(String.valueOf(obj.i_RatingTotSoccer));
        holder.basketRating.setText(String.valueOf(obj.i_RatingTotBasket));
        holder.tennisRating.setText(String.valueOf(obj.i_RatingTotTennis));
        holder.volleyRating.setText(String.valueOf(obj.i_RatingTotVolley));
        holder.listPlayersTitle.setText(obj.s_Name + " " + obj.s_Surename);

        holder.soccerRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).IdPlayer;
    }

    public void showPopup(View view) {
        PopupDialogFragment dialogFragment = new PopupDialogFragment();
        dialogFragment.show(((FragmentActivity)mContext).getSupportFragmentManager(), "OpenPopup");
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextViewPlus soccerRating ;
        TextViewPlus basketRating ;
        TextViewPlus tennisRating ;
        TextViewPlus volleyRating ;
        TextViewPlus listPlayersTitle;

        MyViewHolder(View view) {
            super(view);
            soccerRating = (TextViewPlus) view.findViewById(R.id.soccerLabel);
            basketRating = (TextViewPlus) view.findViewById(R.id.basketLabel);
            tennisRating = (TextViewPlus) view.findViewById(R.id.tennisLabel);
            volleyRating = (TextViewPlus) view.findViewById(R.id.volleyLabel);
            listPlayersTitle = (TextViewPlus) view.findViewById(R.id.listPlayersTitle);

        }
    }
}