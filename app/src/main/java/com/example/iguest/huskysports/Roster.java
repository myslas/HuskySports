package com.example.iguest.huskysports;


public class Roster {
    public String name;
    public String position;
    public int number;
    public String height;

    public Roster() {

    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public int getNumber() {
        return number;
    }

    public String getHeight() {
        return height;
    }

    public void setName(String s) {
        name = s;
    }

    public void setPosition(String s) {
        position = s;
    }

    public void setNumber(int n) {
        number = n;
    }

    public void setHeight(String s) {
        height = s;
    }
}
