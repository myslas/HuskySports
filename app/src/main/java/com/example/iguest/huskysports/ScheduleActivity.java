package com.example.iguest.huskysports;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScheduleActivity extends ActionBarActivity {

    ListView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        final Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        TextView sportName = (TextView) findViewById(R.id.sportName);
        sportName.setText(name);
        ArrayList<Schedule> schedule;
        Button b1 = (Button) findViewById(R.id.rosterbutton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(ScheduleActivity.this, Sport.class);
                nextActivity.putExtra("name", name);
                startActivity(nextActivity);
            }
        });
        final ArrayList<UWsports> uwsports = MySingleton.getInstance().getElements(getApplication().getApplicationContext());
        if (name.equals("Baseball")) {
            schedule = uwsports.get(2).getSchedule();
        } else if (name.equals("Basketball")) {
            schedule = uwsports.get(0).getSchedule();
        } else {
            schedule = uwsports.get(1).getSchedule();
        }


        ArrayList<String> opponents = new ArrayList<String>();
        ArrayList<String> details = new ArrayList<String>();

        for (int j = 0; j < schedule.size(); j++) {

            opponents.add(schedule.get(j).getOpponent() + " " + schedule.get(j).getResults());
            details.add(schedule.get(j).getDate() + " " + schedule.get(j).getTime() + " " + schedule.get(j).getLocation());
        }



        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for (int i = 0; i < opponents.size(); i++) {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("names", opponents.get(i));
            datum.put("descriptions", details.get(i));
            data.add(datum);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"names", "descriptions"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        myList = (ListView)findViewById(R.id.listView2);
        myList.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
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
