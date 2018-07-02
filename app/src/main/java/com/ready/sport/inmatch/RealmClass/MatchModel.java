package com.ready.sport.inmatch.RealmClass;

import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by l.martelloni on 14/11/2017.
 */

public class MatchModel extends RealmObject {

    @PrimaryKey
    public int IdMatch;

    public int    getIdMatch() { return IdMatch; }
    public void   setIdMatch(int idMatch) { this.IdMatch = idMatch; }

    public String d_CreationDateUtc;

    public String    getCreationDateUtc() { return d_CreationDateUtc; }
    public void   setCreationDateUtc(String creationDateUtc) { this.d_CreationDateUtc = creationDateUtc; }

    public String d_StartDateUtc;

    public String    getStartDateUtc() { return d_StartDateUtc; }
    public void   setStartDateUtc(String startDateUtc) { this.d_StartDateUtc = startDateUtc; }

    public Boolean b_IsFinish;

    public Boolean    getIsFinish() { return b_IsFinish; }
    public void   setIsFinish(Boolean isFinish) { this.b_IsFinish = isFinish; }

    public int i_MatchType;

    public int    getMatchType() { return i_MatchType; }
    public void   setMatchType(int matchType) { this.i_MatchType = matchType; }

    public String s_Location;

    public String    getLocation() { return s_Location; }
    public void   setLocation(String location) { this.s_Location = location; }

    public int i_NumberForTeam;

    public int    getNumberForTeam() { return i_NumberForTeam; }
    public void   setNumberForTeam(int numberForTeam) { this.i_NumberForTeam = numberForTeam; }

    public String s_NameFirstTeam;

    public String    getNameFirstTeam() { return s_NameFirstTeam; }
    public void   setNameFirstTeam(String nameFirstTeam) { this.s_NameFirstTeam = nameFirstTeam; }

    public String s_NameSecondTeam;

    public String    getNameSecondTeam() { return s_NameSecondTeam; }
    public void   setNameSecondTeam(String nameSecondTeam) { this.s_NameSecondTeam = nameSecondTeam; }

    public String s_ListPlayersFirstTeam;

    public String    getListPlayersFirstTeam() { return s_ListPlayersFirstTeam; }
    public void   setListPlayersFirstTeam(String listPlayersFirstTeam) { this.s_ListPlayersFirstTeam = listPlayersFirstTeam; }

    public String s_ListPlayersSecondTeam;

    public String    getListPlayersSecondTeam() { return s_ListPlayersSecondTeam; }
    public void   setListPlayersSecondTeam(String listPlayersSecondTeam) { this.s_ListPlayersSecondTeam = listPlayersSecondTeam; }

    public double d_FirstTeamRating;

    public double    getFirstTeamRating() { return d_FirstTeamRating; }
    public void   setFirstTeamRating(double firstTeamRating) { this.d_FirstTeamRating = firstTeamRating; }

    public double d_SecondTeamRating;

    public double    getSecondTeamRating() { return d_SecondTeamRating; }
    public void   setSecondTeamRating(double secondTeamRating) { this.d_SecondTeamRating = secondTeamRating; }

    public String s_ListTotalPlayers;

    public String    getListTotalPlayers() { return s_ListTotalPlayers; }
    public void   setListTotalPlayers(String listTotalPlayers) { this.s_ListTotalPlayers = listTotalPlayers; }

    public String s_Result;

    public String    getResult() { return s_Result; }
    public void   setResult(String result) { this.s_Result = result; }

    public String s_IdUser;

    public String    getIdUser() { return s_IdUser; }
    public void   setIdUser(String idUser) { this.s_IdUser = idUser; }

    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("d_CreationDateUtc", d_CreationDateUtc);
            jo.put("d_StartDateUtc", d_StartDateUtc);
            jo.put("b_IsFinish", b_IsFinish);
            jo.put("i_MatchType", i_MatchType);
            jo.put("s_Location", s_Location);
            jo.put("i_NumberForTeam", i_NumberForTeam);
            jo.put("s_NameFirstTeam", s_NameFirstTeam);
            jo.put("s_NameSecondTeam", s_NameSecondTeam);
            jo.put("s_ListPlayersFirstTeam", s_ListPlayersFirstTeam);
            jo.put("s_ListPlayersSecondTeam", s_ListPlayersSecondTeam);
            jo.put("d_FirstTeamRating", d_FirstTeamRating);
            jo.put("d_SecondTeamRating", d_SecondTeamRating);
            jo.put("s_ListTotalPlayers", s_ListTotalPlayers);
            jo.put("s_Result", s_Result);
            jo.put("IdMatch", IdMatch);
        } catch (Exception e) {
        }
        return jo;
    }
}
