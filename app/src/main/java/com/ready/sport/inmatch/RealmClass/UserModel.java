package com.ready.sport.inmatch.RealmClass;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by l.martelloni on 10/11/2017.
 */

public class UserModel extends RealmObject {

//    private String IdUser;
//
//    public String    getIdUser() { return IdUser; }
//    public void   setIdUser(String idUser) { this.IdUser = idUser; }
    @PrimaryKey
    private String EmailUser;

    public String    getEmailUser() { return EmailUser; }
    public void   setEmailUser(String emailUser) { this.EmailUser = emailUser; }

    private String Token;

    public String    getToken() { return Token; }
    public void   setToken(String token) { this.Token = token; }

    private String UserName;

    public String    getUserName() { return UserName; }
    public void   setUserName(String userName) { this.UserName = userName; }
}
