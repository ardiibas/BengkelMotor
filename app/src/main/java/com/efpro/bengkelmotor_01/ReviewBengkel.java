package com.efpro.bengkelmotor_01;

/**
 * Created by rzapalupi on 11/30/2017.
 */

public class ReviewBengkel {

    private String username;
    private String comment;
    private int rate;
    private String date;


    public ReviewBengkel(){

    }

    public ReviewBengkel(String username, String comment, int rate, String date) {
        this.username = username;
        this.comment = comment;
        this.rate = rate;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
