package com.example.iguest.huskysports;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private boolean alarmUp;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.button);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean("switch", false)) {
            if (sharedPreferences.getBoolean("basketball", false)) {
                runAlerts(0);
            }
            if (sharedPreferences.getBoolean("football", false)) {
                runAlerts(1);
            }
            if (sharedPreferences.getBoolean("baseball", false)) {
                runAlerts(2);
            }
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Sports.class));
            }
        });

        final Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        alarmUp = (PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent,PendingIntent.FLAG_NO_CREATE) != null);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
        if (!alarmUp) {
            start();
        }


        if (isAirplaneModeOn(MainActivity.this)) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setMessage("Airplane mode is on. Click okay to go to the settings screen");
            alert.setTitle("Airplane mode");
            alert.setIcon(R.drawable.ic_launcher);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS));
                }

            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }

            });
            alert.create();
            alert.show();
        }

        if(!isNetworkAvailable()) {
            Toast.makeText(MainActivity.this,
                    "No Internet Connection",
                    Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent nextActivity = new Intent(MainActivity.this, Preferences.class);
            startActivity(nextActivity);
        }


        return super.onOptionsItemSelected(item);
    }

    public void runAlerts(int num) {
        final ArrayList<UWsports> uwsports = MySingleton.getInstance().getElements(getApplicationContext());

        String s;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(c.getTime());



        for (int j = 0; j < uwsports.get(num).getSchedule().size(); j++) {
            try {
                String str = uwsports.get(num).getSchedule().get(j).getDate();

                Date today = df.parse(formattedDate);
                Date date = df.parse(str);

                if (today.equals(date)) {
                    String msg = "There is a " + uwsports.get(num).getSportName() + " game today, " + str + ", at " + uwsports.get(num).getSchedule().get(j).getTime() + " against " + uwsports.get(num).getSchedule().get(j).getOpponent() + "!";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }

    }

    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval =  1000 * 60 * 60 * 24;
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private static boolean isAirplaneModeOn(Context context) {

        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;

    }
}
