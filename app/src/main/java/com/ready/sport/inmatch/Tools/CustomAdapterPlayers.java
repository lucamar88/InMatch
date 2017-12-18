package com.ready.sport.inmatch.Tools;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ready.sport.inmatch.Activity.MainActivity;
import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.util.PopupDialogFragmentBasket;
import com.ready.sport.inmatch.util.PopupDialogFragmentSoccer;
import com.ready.sport.inmatch.util.PopupDialogFragmentTennis;
import com.ready.sport.inmatch.util.PopupDialogFragmentVolley;
import com.ready.sport.inmatch.util.TextViewPlus;
import com.ready.sport.inmatch.util.ToastCustom;


import java.text.DecimalFormat;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by l.martelloni on 28/08/2017.
 */

public class CustomAdapterPlayers extends RealmRecyclerViewAdapter<PlayersModel, CustomAdapterPlayers.MyViewHolder> {
    private Context mContext;
    private DecimalFormat value;

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
        final Bundle args = new Bundle();
        value = new DecimalFormat("#.#");
        args.putInt("idPlayer", (int)getItemId(position));
        holder.soccerRating.setText(String.valueOf(value.format(obj.i_RatingTotSoccer)));
        holder.basketRating.setText(String.valueOf(value.format(obj.i_RatingTotBasket)));
        holder.tennisRating.setText(String.valueOf(value.format(obj.i_RatingTotTennis)));
        holder.volleyRating.setText(String.valueOf(value.format(obj.i_RatingTotVolley)));
        holder.listPlayersTitle.setText(obj.s_Name + " " + obj.s_Surename);

        holder.cardSoccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupDialogFragmentSoccer dialogFragment = new PopupDialogFragmentSoccer();

                dialogFragment.setArguments(args);
                dialogFragment.show(((FragmentActivity)mContext).getSupportFragmentManager(), "OpenPopup");
            }
        });
        holder.cardBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupDialogFragmentBasket dialogFragment = new PopupDialogFragmentBasket();
                dialogFragment.setArguments(args);
                dialogFragment.show(((FragmentActivity)mContext).getSupportFragmentManager(), "OpenPopup");
            }
        });
        holder.cardTennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupDialogFragmentTennis dialogFragment = new PopupDialogFragmentTennis();
                dialogFragment.setArguments(args);
                dialogFragment.show(((FragmentActivity)mContext).getSupportFragmentManager(), "OpenPopup");
            }
        });
        holder.cardVolley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupDialogFragmentVolley dialogFragment = new PopupDialogFragmentVolley();
                dialogFragment.setArguments(args);
                dialogFragment.show(((FragmentActivity)mContext).getSupportFragmentManager(), "OpenPopup");
            }
        });

    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).IdPlayer;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextViewPlus soccerRating ;
        TextViewPlus basketRating ;
        TextViewPlus tennisRating ;
        TextViewPlus volleyRating ;
        TextViewPlus listPlayersTitle;
        CardView cardPl;
        CardView cardSoccer;
        CardView cardBasket;
        CardView cardTennis;
        CardView cardVolley;

        MyViewHolder(View view) {
            super(view);
            soccerRating = (TextViewPlus) view.findViewById(R.id.soccerLabel);
            basketRating = (TextViewPlus) view.findViewById(R.id.basketLabel);
            tennisRating = (TextViewPlus) view.findViewById(R.id.tennisLabel);
            volleyRating = (TextViewPlus) view.findViewById(R.id.volleyLabel);
            listPlayersTitle = (TextViewPlus) view.findViewById(R.id.listPlayersTitle);
            cardPl = (CardView)view.findViewById(R.id.cardPlayers);
            cardPl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {


                    new AlertDialog.Builder(mContext, R.style.AlertDialogCustom)
                            .setTitle(mContext.getString(R.string.delete))
                            .setMessage(mContext.getString(R.string.delete_player))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ToastCustom toast = new ToastCustom(mContext,  mContext.getResources().getDrawable(R.drawable.ic_icon_check),mContext.getString(R.string.operation_success));
                                    toast.show();
                                }})
                            .setNegativeButton(android.R.string.no, null).show();

                    return true;// returning true instead of false, works for me
                }
            });

            cardSoccer = (CardView)view.findViewById(R.id.cardSoccer);
            cardBasket = (CardView)view.findViewById(R.id.cardBasket);
            cardTennis = (CardView)view.findViewById(R.id.cardTennis);
            cardVolley = (CardView)view.findViewById(R.id.cardVolley);
        }
    }
}