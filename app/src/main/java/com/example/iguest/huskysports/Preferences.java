package com.example.iguest.huskysports;

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
import android.widget.Toast;


public class Preferences extends ActionBarActivity {

    SharedPreferences preferenceManager;
    CheckBox baseball;
    CheckBox basketball;
    CheckBox football;
    private DownloadManager dm;
    String Download_ID = "DOWNLOAD_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        baseball = (CheckBox) findViewById(R.id.baseball);
        basketball = (CheckBox) findViewById(R.id.basketball);
        football = (CheckBox) findViewById(R.id.football);

        loadSavedPreferences();

        preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);

        final Button downloadButton = (Button) this.findViewById(R.id.button2);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isAirplaneModeOn(Preferences.this)) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(Preferences.this);
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
                    Toast.makeText(Preferences.this,
                            "No Internet Connection",
                            Toast.LENGTH_LONG).show();
                }


            try{
                dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://students.washington.edu/kyang126/Info498MobileDev/HuskySport.json"));

                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle("testHuskysport2.json")
                        .setDescription("Quiz Data for application")
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                                "testhuskysport2.json");


                long download_id = dm.enqueue(request);
                //Save the download id
                SharedPreferences.Editor PrefEdit = preferenceManager.edit();
                PrefEdit.putLong(Download_ID, download_id);
                PrefEdit.commit();
                Log.i("alarm", "it works");

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(preferenceManager.getLong(Download_ID, 0));
                Cursor cursor = dm.query(query);
                if(cursor.moveToFirst()){
                    int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    int status = cursor.getInt(columnIndex);
                    int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
                    int reason = cursor.getInt(columnReason);


                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        Toast.makeText(getApplicationContext(),
                                "DONE!",
                                Toast.LENGTH_LONG).show();
                    } else if (status == DownloadManager.STATUS_FAILED) {
                        Toast.makeText(getApplicationContext(),
                                "FAILED!\n" + "reason of " + reason,
                                Toast.LENGTH_LONG).show();
                    } else if (status == DownloadManager.STATUS_PAUSED) {
                        Toast.makeText(getApplicationContext(),
                                "PAUSED!\n" + "reason of " + reason,
                                Toast.LENGTH_LONG).show();
                    } else if (status == DownloadManager.STATUS_PENDING) {
                        Toast.makeText(getApplicationContext(),
                                "PENDING!",
                                Toast.LENGTH_LONG).show();
                    } else if (status == DownloadManager.STATUS_RUNNING) {
                        Toast.makeText(getApplicationContext(),
                                "RUNNING!",
                                Toast.LENGTH_LONG).show();
                    }

                }


            } catch(Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Make sure it is a valid URL",
                        Toast.LENGTH_LONG).show();
            }


                Toast.makeText(getApplicationContext(),
                        "Please restart the application to see the updated the data!",
                        Toast.LENGTH_LONG).show();
        }

        });

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
