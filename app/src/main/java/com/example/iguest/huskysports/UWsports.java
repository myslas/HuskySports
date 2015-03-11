package com.example.iguest.huskysports;


import java.io.Serializable;
import java.util.ArrayList;

public class UWsports implements Serializable {
    private String sportName;
    private String record;
    private ArrayList<Roster> roster;
    private ArrayList schedule;
    private String coach;

    public UWsports() {
        //questions = new ArrayList<Quiz>();
    }

    public String getSportName() {
        return sportName;
    }

    public String getRecord() { return  record; }

    public ArrayList<Roster> getRoster() {
        return roster;
    }

    public ArrayList<Schedule> getSchedule() {return schedule; }

    public String getCoach() { return coach; }

    public void setSportName(String title) {
        this.sportName = title;
    }

    public void setRoster(ArrayList<Roster> r) {
        this.roster = r;
    }

    public void setSchedule(ArrayList<Schedule> s) {this.schedule = s; }

    public void setRecord(String s) { this.record = s; }

    public void setCoach(String s) { this.coach = s; }


}
