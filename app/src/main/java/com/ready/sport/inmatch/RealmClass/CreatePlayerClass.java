package com.ready.sport.inmatch.RealmClass;

import java.util.Date;
import java.util.Random;

/**
 * Created by l.martelloni on 01/09/2017.
 */

public class CreatePlayerClass {
    private static PlayersModel playersModel = new PlayersModel();

    public CreatePlayerClass (){}

    public static PlayersModel setPlayerModel(boolean isOwner, SoccerModel soc, BasketModel bas, TennisModel ten , VolleyModel vol, String name, String surName){

        playersModel.setIdPlayer(new Random().nextInt());
        playersModel.setCreationDateUTC(new Date());

        playersModel.setName(name);

        playersModel.setSurName(surName);

        playersModel.setIsOwn(isOwner);

        playersModel.setRuoloSoccer(soc.getRuoloSoccer());

        playersModel.setVelocitaSoccer(soc.getVelocitaSoccer());


        playersModel.setPotenzaSoccer(soc.getPotenzaSoccer());


        playersModel.setDribblingSoccer(soc.getDribblingSoccer());


        playersModel.setDifesaSoccer(soc.getDifesaSoccer());


        playersModel.setAttaccoSoccer(soc.getAttaccoSoccer());


        playersModel.setAgilitaSoccer(soc.getAgilitaSoccer());


        playersModel.setRatingSoccer(soc.getRatingSoccer());


        playersModel.setVelocitaBasket(bas.getVelocitaBasket());


        playersModel.setPotenzaBasket(bas.getPotenzaBasket());


        playersModel.setPassaggioBasket(bas.getPassaggioBasket());


        playersModel.setDifesaBasket(bas.getDifesaBasket());


        playersModel.setAttaccoBasket(bas.getAttaccoBasket());


        playersModel.setFinalizzazioneBasket(bas.getFinalizzazioneBasket());

        playersModel.setRatingBasket(bas.getRatingBasket());


        playersModel.setAgilitaTennis(ten.getAgilitaTennis());


        playersModel.setPotenzaTennis(ten.getPotenzaTennis());


        playersModel.setBattutaTennis(ten.getBattutaTennis());


        playersModel.setDrittoTennis(ten.getDrittoTennis());


        playersModel.setRovescioTennis(ten.getRovescioTennis());


        playersModel.setSchiacciataTennis(ten.getSchiacciataTennis());


        playersModel.setRatingTennis(ten.getRatingTennis());


        playersModel.setBattutaVolley(vol.getBattutaVolley());

        playersModel.setPotenzaVolley(vol.getPotenzaVolley());

        playersModel.setRicezioneVolley(vol.getRicezioneVolley());


        playersModel.setPrecisioneVolley(vol.getPrecisioneVolley());


        playersModel.setDifesaVolley(vol.getDifesaVolley());


        playersModel.setSchiacciataVolley(vol.getSchiacciataVolley());


        playersModel.setRatingVolley(vol.getRatingVolley());
        return playersModel;
    }
}
