package com.ready.sport.inmatch.Tools;

import android.content.Context;
import android.support.transition.Visibility;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ready.sport.inmatch.Activity.CreateMatchActivity;
import com.ready.sport.inmatch.R;
import com.ready.sport.inmatch.RealmClass.PlayerCardMatchModel;
import com.ready.sport.inmatch.RealmClass.PlayersModel;
import com.ready.sport.inmatch.util.AdapterInterface;
import com.ready.sport.inmatch.util.CircularProgressBar;
import com.ready.sport.inmatch.util.Constants;
import com.ready.sport.inmatch.util.PopupDialogFragmentBasket;
import com.ready.sport.inmatch.util.PopupDialogFragmentSoccer;
import com.ready.sport.inmatch.util.PopupDialogFragmentTennis;
import com.ready.sport.inmatch.util.PopupDialogFragmentVolley;
import com.ready.sport.inmatch.util.TextViewPlus;
import com.xw.repo.BubbleSeekBar;

import java.text.DecimalFormat;
import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by l.martelloni on 30/08/2017.
 */

public class CustomAdaptersPlayersMatch extends  RecyclerView.Adapter<CustomAdaptersPlayersMatch.MyViewHolder>  {
    private Context mContext;
    private ArrayList<PlayerCardMatchModel> lista;

    public CustomAdaptersPlayersMatch(Context mContext, ArrayList<PlayerCardMatchModel> albumList) {
        this.mContext = mContext;
        this.lista = albumList;
        setHasStableIds(true);
    }



    @Override
    public CustomAdaptersPlayersMatch.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.card_player, parent, false);
        return new CustomAdaptersPlayersMatch.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomAdaptersPlayersMatch.MyViewHolder holder, final int position) {
        final PlayerCardMatchModel obj = lista.get(position);

        holder.playerName.setText(obj.getNamePlayer());

        holder.playerSurname.setText(obj.getSurnamePlayer());

        if (obj.MatchType == Constants.SOCCER_TYPE) {
            holder.playerSoccerRole.setVisibility(View.VISIBLE);
            holder.playerSoccerRole.setText(obj.getRoleSoccer());
        } else {
            holder.playerSoccerRole.setVisibility(View.INVISIBLE);
        }
        DecimalFormat value = new DecimalFormat("#.#");
        holder.playerRating.setTitle(value.format(obj.getRatingPlayer()));
        holder.playerRating.setSizeTitle(35);
        holder.playerRating.setProgress((int) obj.getRatingPlayer() * 10);
        holder.itemPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = holder.selectLayout.getVisibility();
                if (a == View.INVISIBLE) {
                    if(((CreateMatchActivity)mContext).numberPlayerRemain() != 0) {
                        holder.selectLayout.setVisibility(View.VISIBLE);
                        ((AdapterInterface) mContext).setPlayerForMatch((int) getItemId(position), 1);
                    }else{
                        Toast.makeText(mContext, R.string.error_max_player,Toast.LENGTH_SHORT).show();
                    }

                }else{
                    holder.selectLayout.setVisibility(View.INVISIBLE);
                    ((AdapterInterface) mContext).setPlayerForMatch((int)getItemId(position),0);
                }
            }
        });
    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return lista.get(index).PlayerId;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        CircularProgressBar playerRating;
        TextViewPlus playerName;
        TextViewPlus playerSurname;
        TextViewPlus playerSoccerRole;
        LinearLayoutCompat selectLayout;
        LinearLayout itemPlayer;

        MyViewHolder(View view) {
            super(view);
            playerName = (TextViewPlus) view.findViewById(R.id.namePlayerList);
            playerSurname = (TextViewPlus) view.findViewById(R.id.surnamePlayerList);
            playerSoccerRole = (TextViewPlus) view.findViewById(R.id.rolePlayerList);
            playerRating = (CircularProgressBar) view.findViewById(R.id.circularprogressbarPlayer);
            selectLayout = (LinearLayoutCompat) view.findViewById(R.id.selectPlayer);
            itemPlayer = (LinearLayout) view.findViewById(R.id.cardPlayerNewMatch);
        }
    }
    @Override
    public int getItemCount() {
        return lista.size();
    }
}