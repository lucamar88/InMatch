package com.ready.sport.inmatch.RealmClass;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by l.martelloni on 28/08/2017.
 */

public class PlayersModel extends RealmObject {

    public int IdPlayer;

    public int    getIdPlayer() { return IdPlayer; }
    public void   setIdPlayer(int idPlayer) { this.IdPlayer = idPlayer; }

    public Date d_CreationDateUtc ;

    public Date    getCreationDateUTC() { return d_CreationDateUtc; }
    public void   setCreationDateUTC(Date CreationDateUTC) { this.d_CreationDateUtc = CreationDateUTC; }

    public String s_Name ;

    public String    getName() { return s_Name; }
    public void   setName(String name) { this.s_Name = name; }

    public String s_Surename;

    public String    getSurName() { return s_Surename; }
    public void   setSurName(String surName) { this.s_Surename = surName; }

    public boolean b_ownPlayer ;

    public boolean    getIsOwn() { return b_ownPlayer; }
    public void   setIsOwn(boolean isOwn) { this.b_ownPlayer = isOwn; }

    public int i_RuoloSoccer;

    public int    getRuoloSoccer() { return i_RuoloSoccer; }
    public void   setRuoloSoccer(int ruoloSoccer) { this.i_RuoloSoccer = ruoloSoccer; }

    // Soccer values

    public double i_VelocitaSoccer ;

    public double    getVelocitaSoccer() { return i_VelocitaSoccer; }
    public void   setVelocitaSoccer(double velocitaSoccer) { this.i_VelocitaSoccer = velocitaSoccer; }

    public double i_PotenzaSoccer;

    public double    getPotenzaSoccer() { return i_PotenzaSoccer; }
    public void   setPotenzaSoccer(double potenzaSoccer) { this.i_PotenzaSoccer = potenzaSoccer; }

    public double i_DribblingSoccer;

    public double    getDribblingSoccer() { return i_DribblingSoccer; }
    public void   setDribblingSoccer(double dribblingSoccer) { this.i_DribblingSoccer = dribblingSoccer; }

    public double i_DifesaSoccer;

    public double    getDifesaSoccer() { return i_DifesaSoccer; }
    public void   setDifesaSoccer(double difesaSoccer) { this.i_DifesaSoccer = difesaSoccer; }

    public double i_AttaccoSoccer;

    public double    getAttaccoSoccer() { return i_AttaccoSoccer; }
    public void   setAttaccoSoccer(double attaccoSoccer) { this.i_AttaccoSoccer = attaccoSoccer; }

    public double i_AgilitaSoccer;

    public double    getAgilitaSoccer() { return i_AgilitaSoccer; }
    public void   setAgilitaSoccer(double agilitaSoccer) { this.i_AgilitaSoccer = agilitaSoccer; }

    public double i_RatingTotSoccer;

    public double    getRatingSoccer() { return i_RatingTotSoccer; }
    public void   setRatingSoccer(double ratingSoccer) { this.i_RatingTotSoccer = ratingSoccer; }

    // Basket values

    public double i_VelocitaBasket;

    public double    getVelocitaBasket() { return i_VelocitaBasket; }
    public void   setVelocitaBasket(double velocitaBasket) { this.i_VelocitaBasket = velocitaBasket; }

    public double i_PotenzaBasket;

    public double    getPotenzaBasket() { return i_PotenzaBasket; }
    public void   setPotenzaBasket(double potenzaBasket) { this.i_PotenzaBasket = potenzaBasket; }

    public double i_PassaggioBasket;

    public double    getPassaggioBasket() { return i_PassaggioBasket; }
    public void   setPassaggioBasket(double passaggioBasket) { this.i_PassaggioBasket = passaggioBasket; }

    public double i_DifesaBasket;

    public double    getDifesaBasket() { return i_DifesaBasket; }
    public void   setDifesaBasket(double difesaBasket) { this.i_DifesaBasket = difesaBasket; }

    public double i_AttaccoBasket ;

    public double    getAttaccoBasket() { return i_AttaccoBasket; }
    public void   setAttaccoBasket(double attaccoBasket) { this.i_AttaccoBasket = attaccoBasket; }

    public double i_FinalizzazioneBasket;

    public double    getFinalizzazioneBasket() { return i_FinalizzazioneBasket; }
    public void   setFinalizzazioneBasket(double finalizzazioneBasket) { this.i_FinalizzazioneBasket = finalizzazioneBasket; }

    public double i_RatingTotBasket ;

    public double    getRatingBasket() { return i_RatingTotBasket; }
    public void   setRatingBasket(double ratingBasket) { this.i_RatingTotBasket = ratingBasket; }

    // Tennis values

    public double i_AgilitaTennis ;

    public double    getAgilitaTennis() { return i_AgilitaTennis; }
    public void   setAgilitaTennis(double agilitaTennis) { this.i_AgilitaTennis = agilitaTennis; }

    public double i_PotenzaTennis ;

    public double    getPotenzaTennis() { return i_PotenzaTennis; }
    public void   setPotenzaTennis(double potenzaTennis) { this.i_PotenzaTennis = potenzaTennis; }

    public double i_BattutaTennis ;

    public double    getBattutaTennis() { return i_BattutaTennis; }
    public void   setBattutaTennis(double battutaTennis) { this.i_BattutaTennis = battutaTennis; }

    public double i_DrittoTennis ;

    public double    getDrittoTennis() { return i_DrittoTennis; }
    public void   setDrittoTennis(double drittoTennis) { this.i_DrittoTennis = drittoTennis; }

    public double i_RovescioTennis ;

    public double    getRovescioTennis() { return i_RovescioTennis; }
    public void   setRovescioTennis(double rovescioTennis) { this.i_RovescioTennis = rovescioTennis; }

    public double i_SchiacciataTennis ;

    public double    getSchiacciataTennis() { return i_SchiacciataTennis; }
    public void   setSchiacciataTennis(double schiacciataTennis) { this.i_SchiacciataTennis = schiacciataTennis; }

    public double i_RatingTotTennis ;

    public double    getRatingTennis() { return i_RatingTotTennis; }
    public void   setRatingTennis(double ratingTennis) { this.i_RatingTotTennis = ratingTennis; }

    // Volley values

    public double i_BattutaVolley ;

    public double    getBattutaVolley() { return i_BattutaVolley; }
    public void   setBattutaVolley(double battutaVolley) { this.i_BattutaVolley = battutaVolley; }

    public double i_PotenzaVolley ;

    public double    getPotenzaVolley() { return i_PotenzaVolley; }
    public void   setPotenzaVolley(double potenzaVolley) { this.i_PotenzaVolley = potenzaVolley; }

    public double i_RicezioneVolley ;

    public double    getRicezioneVolley() { return i_RicezioneVolley; }
    public void   setRicezioneVolley(double ricezioneVolley) { this.i_RicezioneVolley = ricezioneVolley; }

    public double i_PrecisioneVolley ;

    public double    getPrecisioneVolley() { return i_PrecisioneVolley; }
    public void   setPrecisioneVolley(double precisioneVolley) { this.i_PrecisioneVolley = precisioneVolley; }

    public double i_DifesaVolley ;

    public double    getDifesaVolley() { return i_DifesaVolley; }
    public void   setDifesaVolley(double difesaVolley) { this.i_DifesaVolley = difesaVolley; }

    public double i_SchiacciataVolley ;

    public double    getSchiacciataVolley() { return i_SchiacciataVolley; }
    public void   setSchiacciataVolley(double schiacciataVolley) { this.i_SchiacciataVolley = schiacciataVolley; }

    /*public double i_AttaccoVolley ;

    public double    getAttaccoVolley() { return i_AttaccoVolley; }
    public void   setAttaccoVolley(double attaccoVolley) { this.i_AttaccoVolley = attaccoVolley; }*/

    public double i_RatingTotVolley ;

    public double    getRatingVolley() { return i_RatingTotVolley; }
    public void   setRatingVolley(double ratingVolley) { this.i_RatingTotVolley = ratingVolley; }
}

