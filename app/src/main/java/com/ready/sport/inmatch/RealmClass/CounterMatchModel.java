package com.ready.sport.inmatch.RealmClass;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by l.martelloni on 18/12/2017.
 */

public class CounterMatchModel extends RealmObject {
    @PrimaryKey
    public int IdMatch;

    public int    getIdMatch() { return IdMatch; }
    public void   setIdMatch(int idMatch) { this.IdMatch = idMatch; }

    public String s_IdUser;

    public String    getIdUser() { return s_IdUser; }
    public void   setIdUser(String idUser) { this.s_IdUser = idUser; }

    public Date d_CreationDateUtc;

    public Date    getCreationDateUtc() { return d_CreationDateUtc; }
    public void   setCreationDateUtc(Date creationDateUtc) { this.d_CreationDateUtc = creationDateUtc; }

    public Date d_StartDateUtc;

    public Date    getStartDateUtc() { return d_StartDateUtc; }
    public void   setStartDateUtc(Date startDateUtc) { this.d_StartDateUtc = startDateUtc; }

    @Ignore
    public int IdCounter;

    public int    getIdCounter() { return IdCounter; }
    public void   setIdCounter(int idCounter) { this.IdCounter = idCounter; }


}
