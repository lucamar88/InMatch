package com.ready.sport.inmatch.util;

/**
 * Created by l.martelloni on 25/08/2017.
 */

public class ConfigUrls {

    /*Base URL*/

    //public static final String BASE_URL = "http://192.168.1.26:8090/";
    public static final String BASE_URL = "http://inmatchweb.azurewebsites.net/";
    /*public static final String BASE_URL = "http://feedreader.suroot.com:8090/";*/

    /*User URL*/

    public static final String REGISTER = "api/account/register";
    public static final String TOKEN = "token";
    public static final String USER_DETAIL = "api/UserDetails/GetUserDetails";

    /*Player URL*/

    public static final String PLAYER_GET_ALL = "api/Players/getPlayers";
    public static final String PLAYER_CREATE = "api/Players/createPlayer";
    public static final String PLAYER_DETAIL = "api/Players/getPlayerDetail";

    /*Match URL*/

    public static final String MATCH_LIST = "api/Match/getMatchList";
    public static final String MATCH_DETAIL = "api/Match/getMatchDetail";
    public static final String MATCH_CREATE = "api/Match/createMatch";
    public static final String MATCH_EDIT = "api/Match/editMatch";

    /*Complete url*/

    public static String getBASE_URL() {return BASE_URL;}
    public static String userRegister() {return BASE_URL + REGISTER;}
    public static String getToken() {return BASE_URL + TOKEN;}
    public static String getUserDetail() {return BASE_URL + USER_DETAIL;}

    public static String getAllPlayer() {return BASE_URL + PLAYER_GET_ALL;}
    public static String createPlayer() {return BASE_URL + PLAYER_CREATE;}
    public static String getPlayerDetail() {return BASE_URL + PLAYER_DETAIL;}

    public static String getMatchList() {return BASE_URL + MATCH_LIST;}
    public static String getMatchDetail() {return BASE_URL + MATCH_DETAIL;}
    public static String createMatch() {return BASE_URL + MATCH_CREATE;}
    public static String editMatch() {return BASE_URL + MATCH_EDIT;}
}
