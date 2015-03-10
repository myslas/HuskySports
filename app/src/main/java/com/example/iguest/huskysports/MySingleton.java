package com.example.iguest.huskysports;


import android.content.Context;
import android.os.Environment;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MySingleton implements SportsRepository {
    public ArrayList<UWsports> Sports;
    private static MySingleton instance;

    public String customVar;

    public static void initInstance()
    {
        if (instance == null)
        {
            // Create the instance
            instance = new MySingleton();
        }
    }

    public static MySingleton getInstance()
    {
        // Return the instance
        return instance;
    }

    private MySingleton()
    {
        // Constructor hidden because this is a singleton
    }

    public void customSingletonMethod()
    {
        // Custom method
    }

    @Override
    public ArrayList<UWsports> getElements(Context context) {

        ArrayList<UWsports> UWsportss = new ArrayList<UWsports>();
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, "HuskySport.json");

        try {
            String json = "";
            InputStream is = context.getAssets().open("HuskySport.json");
            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
            reader.beginArray();
            while(reader.hasNext()) {
                UWsports uw = new UWsports();
                reader.beginObject();
                while(reader.hasNext()) {
                    String temp = reader.nextName();
                    if (temp.equals("sport")) {
                        uw.setSportName(reader.nextString());
                    }
                    if (temp.equals("roster")) {
                        ArrayList<Roster> roster = new ArrayList<Roster>();
                        reader.beginArray();
                        while (reader.hasNext()) {
                            Roster r = new Roster();
                            reader.beginObject();
                            while(reader.hasNext()) {
                                String next = reader.nextName();
                                if (next.equals("student")) {
                                    r.setName(reader.nextString());
                                } else if (next.equals("position")) {
                                    r.setPosition(reader.nextString());
                                } else if (next.equals("number")) {
                                    r.setNumber(reader.nextString());
                                } else {
                                    r.setHeight(reader.nextString());
                                }
                            }
                            reader.endObject();
                            roster.add(r);
                        }
                        uw.setRoster(roster);
                        reader.endArray();

                    }
                    if (temp.equals("schedule")) {
                        ArrayList<Schedule> schedules = new ArrayList<Schedule>();
                        reader.beginArray();
                        while(reader.hasNext()) {
                            Schedule s = new Schedule();
                            reader.beginObject();
                            while (reader.hasNext()) {
                                String next = reader.nextName();
                                if (next.equals("date")) {
                                    s.setDate(reader.nextString());
                                } else if (next.equals("location")) {
                                    s.setLocation(reader.nextString());
                                } else if (next.equals("opponent")) {
                                    s.setOpponent(reader.nextString());
                                } else if (next.equals("results")) {
                                    s.setResults(reader.nextString());
                                } else {
                                    s.setTime(reader.nextString());
                                }
                            }
                            reader.endObject();
                            schedules.add(s);
                        }
                        reader.endArray();
                        uw.setSchedule(schedules);
                    }
                }
                reader.endObject();
                UWsportss.add(uw);
            }
            reader.endArray();
            /*int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONObject obj = new JSONObject(json);
            JSONObject baseball = obj.getJSONObject("Baseball");
            Log.i("MySingleton.java", "" + baseball);
            UWsports mathematics = loadRoster(baseball);
            JSONObject basketball = obj.getJSONObject("Basketball");
            UWsports physicsUWsports = loadRoster(basketball);
            JSONObject football = obj.getJSONObject("Football");
            UWsports marvelUWsports = loadRoster(football);
            UWsportss.add(mathematics);
            UWsportss.add(physicsUWsports);
            UWsportss.add(marvelUWsports);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UWsportss;
    }

    private UWsports loadRoster(JSONObject UWsports){
        UWsports general = new UWsports();
        try {
            ArrayList<Roster>  r1 = new ArrayList<Roster>();
            JSONArray Roster = UWsports.getJSONArray("roster");
            String sport = UWsports.getString("sport");
            for (int i = 0; i < Roster.length(); i++) {
                JSONObject c = Roster.getJSONObject(i);
                Roster r = new Roster();
                String name = c.getString("name");
                String position = c.getString("position");
                String number = c.getString("number");
                String height = c.getString("height");
                r.setName(name);
                r.setPosition(position);
                r.setNumber(number);
                r.setHeight(height);
                r1.add(r);
            }
            general.setSportName(sport);
            general.setRoster(r1);
        }
        catch (JSONException x) {
            x.printStackTrace();
        }
        return general;
    }
}
