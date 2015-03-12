package com.example.iguest.huskysports;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


public class AlarmReceiver extends BroadcastReceiver {

    SharedPreferences preferenceManager;
    private DownloadManager dm;
    String Download_ID = "DOWNLOAD_ID";
    private String TAG = "AlarmReceiver.java";

    @Override
    public void onReceive(Context context, Intent intent) {

         Log.i("AlarmReceiver.java", "Received");

        preferenceManager = PreferenceManager.getDefaultSharedPreferences(context);
        final Context c= context;
        try{
            dm = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://students.washington.edu/kyang126/Info498MobileDev/HuskySport.json"));

            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                    | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle("update3.json")
                    .setDescription("Sport Data for HuskySport application")
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                            "update3.json");

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File direct = new File(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(path, "update3.json");
            if (!direct.exists()) {
                if (file.exists()) {
                    context.deleteFile("update3.json");
                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

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


                if(status == DownloadManager.STATUS_SUCCESSFUL){



                }else if(status == DownloadManager.STATUS_FAILED){
                    Toast.makeText(context,
                            "FAILED!\n" + "reason of " + reason,
                            Toast.LENGTH_LONG).show();
                }else if(status == DownloadManager.STATUS_PAUSED){
                    Toast.makeText(context,
                            "PAUSED!\n" + "reason of " + reason,
                            Toast.LENGTH_LONG).show();
                }else if(status == DownloadManager.STATUS_PENDING){

                }else if(status == DownloadManager.STATUS_RUNNING){


                }
            }

        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(context,
                    "Unable to update",
                    Toast.LENGTH_LONG).show();
        }
    }
}

