package com.example.iguest.huskysports;


import java.io.Serializable;
import java.util.ArrayList;

public class UWsports implements Serializable {
    private String sportName;
    private ArrayList<Roster> roster;
    private ArrayList schedule;

    public UWsports() {
        //questions = new ArrayList<Quiz>();
    }

    public String getSportName() {
        return sportName;
    }

    public ArrayList<Roster> getRoster() {
        return roster;
    }

    public void setSportName(String title) {
        this.sportName = title;
    }

    public void setRoster(ArrayList<Roster> r) {
        this.roster = r;
    }


}
