package com.ready.sport.inmatch.RealmClass;

import org.json.JSONObject;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by l.martelloni on 28/08/2017.
 */

public class PlayersModel extends RealmObject {
    @PrimaryKey
    public int IdPlayer;

    public int    getIdPlayer() { return IdPlayer; }
    public void   setIdPlayer(int idPlayer) { this.IdPlayer = idPlayer; }

    @Ignore
    public CreationUserModel CreationUser;

    public CreationUserModel    getCreationUser() { return CreationUser; }
    public void   setCreationUser(CreationUserModel creationUser) { this.CreationUser = creationUser; }

    public String d_CreationDateUtc ;

    public String    getCreationDateUTC() { return d_CreationDateUtc; }
    public void   setCreationDateUTC(String CreationDateUTC) { this.d_CreationDateUtc = CreationDateUTC; }

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


    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        try{

            jo.put("d_CreationDateUtc", d_CreationDateUtc);
            jo.put("s_Name", s_Name);
            jo.put("s_Surename", s_Surename);
            jo.put("b_ownPlayer",b_ownPlayer);
            //Soccer
            jo.put("i_RuoloSoccer", i_RuoloSoccer);
            jo.put("i_VelocitaSoccer", i_VelocitaSoccer);
            jo.put("i_PotenzaSoccer", i_PotenzaSoccer);
            jo.put("i_DribblingSoccer" , i_DribblingSoccer);
            jo.put("i_DifesaSoccer", i_DifesaSoccer);
            jo.put("i_AttaccoSoccer", i_AttaccoSoccer);
            jo.put("i_AgilitaSoccer", i_AgilitaSoccer);
            jo.put("i_RatingTotSoccer", i_RatingTotSoccer);
            //Basket
            jo.put("i_PassaggioBasket", i_PassaggioBasket);
            jo.put("i_FinalizzazioneBasket", i_FinalizzazioneBasket);
            jo.put("i_RatingTotBasket", i_RatingTotBasket);
            jo.put("i_AttaccoBasket", i_AttaccoBasket);
            jo.put("i_DifesaBasket", i_DifesaBasket);
            jo.put("i_VelocitaBasket", i_VelocitaBasket);
            jo.put("i_PotenzaBasket", i_PotenzaBasket);
            //Tennis
            jo.put("i_AgilitaTennis", i_AgilitaTennis);
            jo.put("i_BattutaTennis", i_BattutaTennis);
            jo.put("i_DrittoTennis", i_DrittoTennis);
            jo.put("i_PotenzaTennis", i_PotenzaTennis);
            jo.put("i_RatingTotTennis", i_RatingTotTennis);
            jo.put("i_RovescioTennis", i_RovescioTennis);
            jo.put("i_SchiacciataTennis", i_SchiacciataTennis);
            //Volley
            jo.put("i_BattutaVolley", i_BattutaVolley);
            jo.put("i_DifesaVolley", i_DifesaVolley);
            jo.put("i_PotenzaVolley", i_PotenzaVolley);
            jo.put("i_PrecisioneVolley", i_PrecisioneVolley);
            jo.put("i_RatingTotVolley", i_RatingTotVolley);
            jo.put("i_RicezioneVolley", i_RicezioneVolley);
            jo.put("i_SchiacciataVolley", i_SchiacciataVolley);


        }catch(Exception e){}

        return jo;
    }
}

