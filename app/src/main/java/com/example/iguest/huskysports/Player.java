package com.example.iguest.huskysports;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;


public class Player extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        String sport = intent.getStringExtra("sport");
        String name = intent.getStringExtra("name");
        String position = intent.getStringExtra("position");
        String number = intent.getStringExtra("number");
        String height = intent.getStringExtra("height");

        TextView sportTV = (TextView) findViewById(R.id.sport);
        sportTV.setText("Sport: " + sport);
        TextView positionTV = (TextView) findViewById(R.id.position);
        positionTV.setText("Position: " + position);
        TextView nameTV = (TextView) findViewById(R.id.name);
        nameTV.setText(name);
        TextView numberTV = (TextView) findViewById(R.id.number);
        numberTV.setText("Number: " + number);
        TextView heightTV = (TextView) findViewById(R.id.height);
        heightTV.setText("Height: " + height);
        ImageView image = (ImageView) findViewById(R.id.imageView2);
        if (sport.equals("Baseball")) {
            image.setImageResource(R.drawable.baseball);
        } else if (sport.equals("Basketball")) {
            image.setImageResource(R.drawable.basketball);
        } else {
            image.setImageResource(R.drawable.football);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);
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
}
