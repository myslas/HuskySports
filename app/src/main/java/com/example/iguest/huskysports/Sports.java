package com.example.iguest.huskysports;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Sports extends ActionBarActivity {

    String TAG = "Sports.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.i(TAG, "" + sharedPreferences.getBoolean("baseball", false));
        Log.i(TAG, "" + sharedPreferences.getBoolean("basketball", false));
        Log.i(TAG, "" + sharedPreferences.getBoolean("football", false));
        Log.i(TAG, "" + sharedPreferences.getBoolean("notifications", false));
        runAlerts();

        final ListView listView = (ListView) findViewById(R.id.listView);

        String[] names = {"Baseball", "Football", "Basketball"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, names);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent nextActivity = new Intent(Sports.this, Sport.class);
                nextActivity.putExtra("name", listView.getItemAtPosition(position).toString());
                startActivity(nextActivity);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sports, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.preferences) {
            Intent nextActivity = new Intent(Sports.this, Preferences.class);
            startActivity(nextActivity);
        }

        return super.onOptionsItemSelected(item);
    }

    public void runAlerts() {
        final ArrayList<UWsports> uwsports = MySingleton.getInstance().getElements(getApplicationContext());

        String s;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(c.getTime());


        for (int i = 0; i < uwsports.size(); i++) {
            for (int j = 0; j < uwsports.get(i).getSchedule().size(); j++) {
                try {
                    String str = uwsports.get(i).getSchedule().get(j).getDate();

                    Date today = df.parse(formattedDate);
                    Log.i("getDates", "today: " + today.toString());
                    Date date = df.parse(str);
                    Log.i("getDates", "date: " + date.toString());

                    if (today.equals(date)) {
                        String msg = "There is a " + uwsports.get(i).getSportName() + " game today, " + str + ", at " + uwsports.get(i).getSchedule().get(j).getTime() + " against " + uwsports.get(i).getSchedule().get(j).getOpponent() + "!";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.i("getDates", "sent sms");
                    }
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
