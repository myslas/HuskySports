package com.example.iguest.huskysports;


public class Roster {
    public String name;
    public String position;
    public String number;
    public String height;
    public String picURL;

    public Roster() {

    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getNumber() {
        return number;
    }

    public String getHeight() {
        return height;
    }

    public String getPicURL() { return picURL; }

    public void setName(String s) {
        name = s;
    }

    public void setPosition(String s) {
        position = s;
    }

    public void setNumber(String n) {
        number = n;
    }

    public void setHeight(String s) {
        height = s;
    }

    public void setPicURL(String s) { picURL = s; }
}
