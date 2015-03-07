package com.example.iguest.huskysports;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;


public class Preferences extends ActionBarActivity {

    SharedPreferences preferenceManager;
    CheckBox baseball;
    CheckBox basketball;
    CheckBox football;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);


        baseball = (CheckBox) findViewById(R.id.baseball);
        basketball = (CheckBox) findViewById(R.id.basketball);
        football = (CheckBox) findViewById(R.id.football);

        loadSavedPreferences();

        preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
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

    }

    @Override
    public void onDestroy() {
        saveData();
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
    }
}
