package com.example.iguest.huskysports;


public class Schedule {
    private String date;
    private String location;
    private String opponent;
    private String time;
    private String results;

    public Schedule() {

    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getOpponent() {
        return opponent;
    }

    public String getResults() {
        return results;
    }

    public String getTime() {
        return time;
    }
}
