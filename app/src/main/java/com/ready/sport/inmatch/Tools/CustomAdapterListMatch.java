package com.ready.sport.inmatch.Tools;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.MatchModel;
import com.ready.sport.inmatch.RealmClass.PlayerCardMatchModel;
import com.ready.sport.inmatch.util.AdapterInterface;
import com.ready.sport.inmatch.util.CircularProgressBar;
import com.ready.sport.inmatch.util.Constants;
import com.ready.sport.inmatch.util.TextViewPlus;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by l.martelloni on 14/11/2017.
 */

public class CustomAdapterListMatch extends RecyclerView.Adapter<CustomAdapterListMatch.MyViewHolder> {
    private Context mContext;
    private ArrayList<MatchModel> lista;

    public CustomAdapterListMatch(Context mContext, ArrayList<MatchModel> albumList) {
        this.mContext = mContext;
        this.lista = albumList;
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

        String firstRes = obj.getResult().substring(0, obj.getResult().indexOf('_'));
        String secondRes = obj.getResult().substring(obj.getResult().indexOf('_'), obj.getResult().length() - 1);

        holder.secondResult.setText(secondRes);
        holder.firstResult.setText(firstRes);

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


        MyViewHolder(View view) {
            super(view);
            firstTeam = (TextViewPlus) view.findViewById(R.id.firstTeamName);
            secondTeam = (TextViewPlus) view.findViewById(R.id.secondTeamName);
            firstResult = (TextViewPlus) view.findViewById(R.id.firstResult);
            secondResult = (TextViewPlus) view.findViewById(R.id.secondResult);
            selectLayout = (LinearLayoutCompat) view.findViewById(R.id.typeMatchList);
            dateMatch = (TextViewPlus) view.findViewById(R.id.dataMatch);
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}