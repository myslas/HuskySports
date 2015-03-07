package com.example.iguest.huskysports;


import android.app.Application;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SportApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("QuizApp", "App is running");
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, "HuskySport.json");

        if(!file.exists()) {
            try{
                AssetManager am = getAssets();
                InputStream inputStream = am.open("quizdata.json");
                File local = createFileFromInputStream(path, inputStream);
                // Log.i("json", "not in: " + local.toString() + " ");
            } catch(Exception e){
                Log.i("json", "error: " + e.getMessage());
            }


        }
        initializeSingletons();
    }

    private File createFileFromInputStream(File path, InputStream inputStream) {

        try{
            File f = new File(path, "HuskySport.json");
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while((length=inputStream.read(buffer)) > 0) {
                outputStream.write(buffer,0,length);
            }
            outputStream.close();
            inputStream.close();

            return f;
        }catch (IOException e) {
            //Logging exception
            Log.i("json", "create file: " + e.getMessage());
        }
        return null;
    }

    protected void initializeSingletons() {
        MySingleton.initInstance();
    }

}
