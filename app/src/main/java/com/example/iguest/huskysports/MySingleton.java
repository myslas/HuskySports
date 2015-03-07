package com.example.iguest.huskysports;


import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
    public ArrayList<UWsports> getElements() {

        ArrayList<UWsports> UWsportss = new ArrayList<UWsports>();
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, "Rosterdata.json");

        try {
            String json = "";
            InputStream is = new FileInputStream(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONObject obj = new JSONObject(json);
            JSONObject baseball = obj.getJSONObject("Baseball");
            UWsports mathematics = loadRoster(baseball);
            JSONObject basketball = obj.getJSONObject("Basketball");
            UWsports physicsUWsports = loadRoster(basketball);
            JSONObject football = obj.getJSONObject("Football");
            UWsports marvelUWsports = loadRoster(football);
            UWsportss.add(mathematics);
            UWsportss.add(physicsUWsports);
            UWsportss.add(marvelUWsports);
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
                String name = UWsports.getString("name");
                String position = UWsports.getString("position");
                int number = UWsports.getInt("number");
                String height = UWsports.getString("height");
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
