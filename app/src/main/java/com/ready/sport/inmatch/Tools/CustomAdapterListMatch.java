package com.ready.sport.inmatch.Tools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ready.sport.inmatch.Activity.MainActivity;
import com.ready.sport.inmatch.Activity.MatchDetailActivity;
import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.MatchModel;
import com.ready.sport.inmatch.RealmClass.PlayerCardMatchModel;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.util.AdapterInterface;
import com.ready.sport.inmatch.util.CircularProgressBar;
import com.ready.sport.inmatch.util.Constants;
import com.ready.sport.inmatch.util.TextViewPlus;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by l.martelloni on 14/11/2017.
 */

public class CustomAdapterListMatch extends RealmRecyclerViewAdapter<MatchModel, CustomAdapterListMatch.MyViewHolder> {
    private Context mContext;
    private OrderedRealmCollection<MatchModel> lista;

    public CustomAdapterListMatch(OrderedRealmCollection<MatchModel> data, Context context) {
        super(data, true);
        this.mContext = context;
        this.lista = data;
        setHasStableIds(true);
    }


    @Override
    public CustomAdapterListMatch.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.card_match_layout, parent, false);
        return new CustomAdapterListMatch.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomAdapterListMatch.MyViewHolder holder, final int position) {
        final MatchModel obj = lista.get(position);

        holder.firstTeam.setText(obj.getNameFirstTeam());

        holder.secondTeam.setText(obj.getNameSecondTeam());

        switch (obj.getMatchType()) {
            case Constants.SOCCER_TYPE:
                holder.selectLayout.setBackground(mContext.getDrawable(R.drawable.shape_match_list_soccer));
                break;
            case Constants.BASKET_TYPE:
                holder.selectLayout.setBackground(mContext.getDrawable(R.drawable.shape_match_list_basket));
                break;
            case Constants.TENNIS_TYPE:
                holder.selectLayout.setBackground(mContext.getDrawable(R.drawable.shape_match_list_tennis));
                break;
            case Constants.VOLLEY_TYPE:
                holder.selectLayout.setBackground(mContext.getDrawable(R.drawable.shape_match_list_volley));
                break;
        }

        if(obj.getResult() != null && obj.getResult().toString() != ""){
            String[] risultati = obj.getResult().split("_");

            holder.secondResult.setText(risultati[1]);
            holder.firstResult.setText(risultati[0]);
        }else{
            holder.secondResult.setText("-");
            holder.firstResult.setText("-");
        }


        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        try {
            Date date = dateFormat.parse(obj.getStartDateUtc().replace("-","/"));//You will get date object relative to server/client timezone wherever it is parsed
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String time = sdf.format(date);
            holder.dateMatch.setText(dateStr + " ore " + time);
        } catch (Exception e) {
            Log.e("Error Data:", e.getMessage());
        }

        holder.content.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent ap = new Intent(mContext, MatchDetailActivity.class);
                ap.putExtra("idMatch",(int)getItemId(position));
                mContext.startActivity(ap);
            }
        });
//        holder.itemPlayer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int a = holder.selectLayout.getVisibility();
//                if (a == View.INVISIBLE) {
//                    holder.selectLayout.setVisibility(View.VISIBLE);
//                    ((AdapterInterface) mContext).setPlayerForMatch((int)getItemId(position),1);
//
//                }else{
//                    holder.selectLayout.setVisibility(View.INVISIBLE);
//                    ((AdapterInterface) mContext).setPlayerForMatch((int)getItemId(position),0);
//                }
//
//            }
//        });
    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return lista.get(index).IdMatch;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        CircularProgressBar playerRating;
        TextViewPlus firstTeam;
        TextViewPlus secondTeam;
        TextViewPlus firstResult;
        TextViewPlus secondResult;
        TextViewPlus dateMatch;
        LinearLayoutCompat selectLayout;
        CardView content;

        MyViewHolder(View view) {
            super(view);
            firstTeam = (TextViewPlus) view.findViewById(R.id.firstTeamName);
            secondTeam = (TextViewPlus) view.findViewById(R.id.secondTeamName);
            firstResult = (TextViewPlus) view.findViewById(R.id.firstResult);
            secondResult = (TextViewPlus) view.findViewById(R.id.secondResult);
            selectLayout = (LinearLayoutCompat) view.findViewById(R.id.typeMatchList);
            dateMatch = (TextViewPlus) view.findViewById(R.id.dataMatch);
            content = (CardView)view.findViewById(R.id.cardPlayers);
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}