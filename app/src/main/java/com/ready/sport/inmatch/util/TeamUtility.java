package com.ready.sport.inmatch.util;

import android.support.v4.util.Pair;

import com.ready.sport.inmatch.RealmClass.PlayersModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import io.realm.RealmResults;

/**
 * Created by l.martelloni on 27/11/2017.
 */

public class TeamUtility {
    private static List<Pair<Integer,Double>> listKey;
    private static List<Pair<Integer,Double>> firstTeam;
    private static List<Pair<Integer,Double>> secondTeam;
    private static double rateAllPlayers = 0;
    private static double rateFirstTeam = 0;
    private static double rateSecondTeam = 0;
    //private static List<PlayersModel> totalListPlayers;

    private static double ratioTeamTemp = 0;

    public static List<String> GenerateTeam(List<PlayersModel> totalListPlayers, int typeMatch)
    {
        //totalListPlayers = players.subList(0,players.size()-1);
        List<String> listPlayers = new ArrayList<String>(); //lista di stringhe contenenti gli ID delle squadre divisi da '_'
        listKey = new ArrayList<Pair<Integer,Double>>();

        firstTeam = new ArrayList<Pair<Integer,Double>>();
        secondTeam = new ArrayList<Pair<Integer,Double>>();

        if (typeMatch == Constants.SOCCER_TYPE)
        {
            List<PlayersModel> golKeaper = new ArrayList<PlayersModel>();
            //RealmResults<PlayersModel> golKeaper = players.where().equalTo("i_RuoloSoccer", Constants.Role.PORTIERE.getValue()).findAll();

            for(PlayersModel pla:totalListPlayers){
                if(pla.i_RuoloSoccer == Constants.Role.PORTIERE.getValue()){
                    golKeaper.add(pla);
                }
                rateAllPlayers = rateAllPlayers + pla.i_RatingTotSoccer;
            }
            rateAllPlayers = rateAllPlayers/totalListPlayers.size();
            if (golKeaper.size() >= 2) // aggiungo i due portieri se sono presenti tra i giocatori
            {
                firstTeam.add(new Pair<Integer, Double>(golKeaper.get(0).IdPlayer, golKeaper.get(0).i_RatingTotSoccer));
                secondTeam.add(new Pair<Integer, Double>(golKeaper.get(1).IdPlayer, golKeaper.get(1).i_RatingTotSoccer));
                totalListPlayers.remove(golKeaper.get(1));
                totalListPlayers.remove(golKeaper.get(0));
            }else // se non ci sono portieri aggiungo due players casuali
            {
                int indexRand = new Random().nextInt(totalListPlayers.size());
                firstTeam.add(new Pair<Integer, Double>(totalListPlayers.get(indexRand).IdPlayer, totalListPlayers.get(indexRand).i_RatingTotSoccer));
                totalListPlayers.remove(totalListPlayers.get(indexRand));
                indexRand = new Random().nextInt(totalListPlayers.size());
                secondTeam.add(new Pair<Integer, Double>(totalListPlayers.get(indexRand).IdPlayer, totalListPlayers.get(indexRand).i_RatingTotSoccer));
                totalListPlayers.remove(totalListPlayers.get(indexRand));
            }

            //totalListPlayers = totalListPlayers.OrderBy(s => s.i_RatingTotSoccer).ToList();

            Collections.sort(totalListPlayers, new Comparator<PlayersModel>()
            {
                @Override
                public int compare(PlayersModel o1, PlayersModel o2)
                {
                    return Double.compare(o1.getRatingSoccer(),o2.getRatingSoccer());
                /* other checks not about being teacher or student goes here... */
                }
            });


            for (PlayersModel p:
                 totalListPlayers) {
                Pair<Integer, Double> tmpP = new Pair<Integer, Double>(p.IdPlayer, p.i_RatingTotSoccer);
                listKey.add(tmpP);
            }

        }
        else if (typeMatch == Constants.VOLLEY_TYPE)
        {
            for(PlayersModel pla:totalListPlayers){

                rateAllPlayers = rateAllPlayers + pla.i_RatingTotVolley;
            }
            rateAllPlayers = rateAllPlayers/totalListPlayers.size();
            //aggiungo due players casuali
            int indexRand = new Random().nextInt(totalListPlayers.size());
            firstTeam.add(new Pair<Integer, Double>(totalListPlayers.get(indexRand).IdPlayer, totalListPlayers.get(indexRand).i_RatingTotVolley));
            totalListPlayers.remove(totalListPlayers.get(indexRand));
            indexRand = new Random().nextInt(totalListPlayers.size());
            secondTeam.add(new Pair<Integer, Double>(totalListPlayers.get(indexRand).IdPlayer, totalListPlayers.get(indexRand).i_RatingTotVolley));
            totalListPlayers.remove(totalListPlayers.get(indexRand));

           /* players = players.OrderBy(s => s.i_RatingTotVolley).ToList();
            foreach (var p in players)
            {
                Pair<Integer, Double> tmpP = new Pair<Integer, Double>(p.IdPlayer, p.i_RatingTotVolley);
                listKey.Add(tmpP);
            }*/
            Collections.sort(totalListPlayers, new Comparator<PlayersModel>()
            {
                @Override
                public int compare(PlayersModel o1, PlayersModel o2)
                {
                    return Double.compare(o1.getRatingVolley(),o2.getRatingVolley());
                /* other checks not about being teacher or student goes here... */
                }
            });


            for (PlayersModel p:
                    totalListPlayers) {
                Pair<Integer, Double> tmpP = new Pair<Integer, Double>(p.IdPlayer, p.i_RatingTotVolley);
                listKey.add(tmpP);
            }
        }
        else if (typeMatch == Constants.BASKET_TYPE)
        {
            for(PlayersModel pla:totalListPlayers){

                rateAllPlayers = rateAllPlayers + pla.i_RatingTotBasket;
            }
            rateAllPlayers = rateAllPlayers/totalListPlayers.size();
            //aggiungo due players casuali
            int indexRand = new Random().nextInt(totalListPlayers.size());
            firstTeam.add(new Pair<Integer, Double>(totalListPlayers.get(indexRand).IdPlayer, totalListPlayers.get(indexRand).i_RatingTotBasket));
            totalListPlayers.remove(totalListPlayers.get(indexRand));
            indexRand = new Random().nextInt(totalListPlayers.size());
            secondTeam.add(new Pair<Integer, Double>(totalListPlayers.get(indexRand).IdPlayer, totalListPlayers.get(indexRand).i_RatingTotBasket));
            totalListPlayers.remove(totalListPlayers.get(indexRand));

            Collections.sort(totalListPlayers, new Comparator<PlayersModel>()
            {
                @Override
                public int compare(PlayersModel o1, PlayersModel o2)
                {
                    return Double.compare(o1.getRatingBasket(),o2.getRatingBasket());
                /* other checks not about being teacher or student goes here... */
                }
            });


            for (PlayersModel p:
                    totalListPlayers) {
                Pair<Integer, Double> tmpP = new Pair<Integer, Double>(p.IdPlayer, p.i_RatingTotBasket);
                listKey.add(tmpP);
            }
        }
        else if (typeMatch == Constants.TENNIS_TYPE)
        {
            for(PlayersModel pla:totalListPlayers){

                rateAllPlayers = rateAllPlayers + pla.i_RatingTotTennis;
            }
            rateAllPlayers = rateAllPlayers/totalListPlayers.size();
            //aggiungo due players casuali
            int indexRand = new Random().nextInt(totalListPlayers.size());
            firstTeam.add(new Pair<Integer, Double>(totalListPlayers.get(indexRand).IdPlayer, totalListPlayers.get(indexRand).i_RatingTotTennis));
            totalListPlayers.remove(totalListPlayers.get(indexRand));
            indexRand = new Random().nextInt(totalListPlayers.size());
            secondTeam.add(new Pair<Integer, Double>(totalListPlayers.get(indexRand).IdPlayer, totalListPlayers.get(indexRand).i_RatingTotTennis));
            totalListPlayers.remove(totalListPlayers.get(indexRand));

            /*players = players.OrderBy(s => s.i_RatingTotTennis).ToList();
            foreach (var p in players)
            {
                Pair<Integer, Double> tmpP = new Pair<Integer, Double>(p.IdPlayer, p.i_RatingTotTennis);
                listKey.Add(tmpP);
            }*/

            Collections.sort(totalListPlayers, new Comparator<PlayersModel>()
            {
                @Override
                public int compare(PlayersModel o1, PlayersModel o2)
                {
                    return Double.compare(o1.getRatingTennis(),o2.getRatingTennis());
                /* other checks not about being teacher or student goes here... */
                }
            });


            for (PlayersModel p:
                    totalListPlayers) {
                Pair<Integer, Double> tmpP = new Pair<Integer, Double>(p.IdPlayer, p.i_RatingTotTennis);
                listKey.add(tmpP);
            }
        }

        recursiveMethod();
        String listStringFirst = "";
        String listStringSecond = "";
        for (Pair<Integer,Double> el : firstTeam)
        {
            listStringFirst = listStringFirst + el.first.toString() + '_';
            rateFirstTeam = rateFirstTeam + el.second;
        }

        for (Pair<Integer,Double> el : secondTeam)
        {
            listStringSecond = listStringSecond + el.first.toString()+ '_';
            rateSecondTeam = rateSecondTeam + el.second;
        }
        rateFirstTeam = rateFirstTeam / firstTeam.size();
        rateSecondTeam = rateSecondTeam / secondTeam.size();
        //Calcolo percentuale di precisione media

        listStringFirst = listStringFirst  + PercentageRate(rateFirstTeam, rateAllPlayers);
        listStringSecond = listStringSecond + PercentageRate(rateSecondTeam, rateAllPlayers);


        listPlayers.add(listStringFirst);
        listPlayers.add(listStringSecond);

        return listPlayers;
    }

    public static void recursiveMethod()
    {
        if (Sum(firstTeam) > Sum(secondTeam))
        {
            int keyTemp = 0;

            firstTeam.add(listKey.get(0));
            listKey.remove(listKey.get(0));

            ratioTeamTemp = Math.abs(Sum(firstTeam) - Sum(secondTeam));

            for (int i = 0; i < listKey.size(); i++)
            {

                double ratio = Math.abs(Sum(firstTeam) - (Sum(secondTeam) + listKey.get(i).second));
                if (ratio <= ratioTeamTemp)
                {
                    keyTemp = i;
                    if (ratio == 0) break;
                }
            }

            secondTeam.add(listKey.get(keyTemp));
            listKey.remove(listKey.get(keyTemp));

        }
            else
        {
            int keyTemp = 0;

            secondTeam.add(listKey.get(0));
            listKey.remove(listKey.get(0));

            ratioTeamTemp = Math.abs(Sum(firstTeam) - Sum(secondTeam));

            for (int i = 0; i < listKey.size(); i++)
            {

                double ratio = Math.abs(Sum(secondTeam) - (Sum(firstTeam) + listKey.get(i).second));
                if (ratio <= ratioTeamTemp)
                {
                    keyTemp = i;
                    if (ratio == 0) break;
                }
            }

            firstTeam.add(listKey.get(keyTemp));
            listKey.remove(listKey.get(keyTemp));
        }

        //ratioTeamTemp = Math.Abs(firstTeam.Sum(s => s.Value) - secondTeam.Sum(s => s.Value));

        if(listKey.size() > 0) recursiveMethod();
    }

    private static double Sum(List<Pair<Integer,Double>> el){
        double res = 0;
        for(Pair<Integer,Double> p : el){
            res = res + p.second;
        }
        return res;
    }

    private static double PercentageRate(double rate1, double rate2){
        double res = 0;
        if(rate1 > rate2){
            res = 100-(100*(rate1-rate2)/rate2);
        }else{
            res = 100-(100*(rate2-rate1)/rate2);
        }

        return res;
    }
}
