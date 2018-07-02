package com.ready.sport.inmatch.RealmClass;

import io.realm.RealmObject;

/**
 * Created by l.martelloni on 30/08/2017.
 */

public class PlayerCardMatchModel extends RealmObject{

    public int PlayerId;

    public int    getPlayerId() { return PlayerId; }
    public void   setPlayerId(int playerId) { this.PlayerId = playerId; }

    public int MatchType;

    public int getMatchType() {return MatchType;}
    public void setMatchType(int matchType){this.MatchType = matchType;}

    public double RatingPlayer ;

    public double    getRatingPlayer() { return RatingPlayer; }
    public void   setRatingPlayer(double ratingPlayer) { this.RatingPlayer = ratingPlayer; }

    public String NamePlayer ;

    public String    getNamePlayer() { return NamePlayer; }
    public void   setNamePlayer(String namePlayer) { this.NamePlayer = namePlayer; }

    public String SurnamePlayer ;

    public String    getSurnamePlayer() { return SurnamePlayer; }
    public void   setSurnamePlayer(String surnamePlayer) { this.SurnamePlayer = surnamePlayer; }

    public String RoleSoccer ;

    public String    getRoleSoccer() { return RoleSoccer; }
    public void   setRoleSoccer(String roleSoccer) { this.RoleSoccer = roleSoccer; }

    public Boolean IsSelected;

    public Boolean getIsSelected() {return IsSelected; }
    public void setIsSelected(Boolean isSelected){ this.IsSelected = isSelected; }

}
