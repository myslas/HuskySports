package com.example.iguest.huskysports;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;


public class Preferences extends ActionBarActivity {

    SharedPreferences preferenceManager;
    CheckBox baseball;
    CheckBox basketball;
    CheckBox football;
    Switch notifications;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        notifications = (Switch) findViewById(R.id.switch1);
        baseball = (CheckBox) findViewById(R.id.baseball);
        basketball = (CheckBox) findViewById(R.id.basketball);
        football = (CheckBox) findViewById(R.id.football);

        if (!notifications.isChecked()) {
            baseball.setEnabled(false);
            basketball.setEnabled(false);
            football.setEnabled(false);
        } else {
            baseball.setEnabled(true);
            basketball.setEnabled(true);
            football.setEnabled(true);
        }

        notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!notifications.isChecked()) {
                    baseball.setEnabled(false);
                    basketball.setEnabled(false);
                    football.setEnabled(false);
                } else {
                    baseball.setEnabled(true);
                    basketball.setEnabled(true);
                    football.setEnabled(true);
                }

            }
        });

        loadSavedPreferences();

        preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);

        final Button downloadButton = (Button) this.findViewById(R.id.button2);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveData();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }

        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void saveData() {
        savePreferences("baseball", baseball.isChecked());
        savePreferences("basketball", basketball.isChecked());
        savePreferences("football", football.isChecked());
        savePreferences("switch", notifications.isChecked());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void savePreferences(String key, boolean b) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, b);
        editor.commit();
    }

    public void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        baseball.setChecked(sharedPreferences.getBoolean("baseball", false));
        basketball.setChecked(sharedPreferences.getBoolean("basketball", false));
        football.setChecked(sharedPreferences.getBoolean("football", false));
        notifications.setChecked(sharedPreferences.getBoolean("switch", false));
    }


}
