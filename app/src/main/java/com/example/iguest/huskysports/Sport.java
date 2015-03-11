package com.example.iguest.huskysports;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Sport extends ActionBarActivity {
    ListView myList;
    private Toolbar toolbar;
    private SimpleAdapter sa;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    final String TAG = "Sport.java";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        TextView sportName = (TextView) findViewById(R.id.sportName);
        Button b1 = (Button) findViewById(R.id.schedulebutton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(Sport.this, ScheduleActivity.class);
                nextActivity.putExtra("name", name);
                startActivity(nextActivity);
            }
        });
        sportName.setText(name);
        TextView record = (TextView) findViewById(R.id.Record);
        TextView coach = (TextView) findViewById(R.id.coach);
        final ArrayList<Roster> roster;
        final ArrayList<UWsports> uwsports = MySingleton.getInstance().getElements(getApplication().getApplicationContext());
        Log.i(TAG, "" + uwsports);
        if (name.equals("Baseball")) {
            roster = uwsports.get(2).getRoster();
            record.setText("Record: " + uwsports.get(2).getRecord());
            coach.setText("Coach: " + uwsports.get(2).getCoach());
        } else if (name.equals("Basketball")) {
            roster = uwsports.get(0).getRoster();
            record.setText("Record: " + uwsports.get(0).getRecord());
            coach.setText("Coach: " + uwsports.get(0).getCoach());
        } else {
            roster = uwsports.get(1).getRoster();
            record.setText("Record: " + uwsports.get(1).getRecord());
            coach.setText("Coach: " + uwsports.get(1).getCoach());
        }


        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> details = new ArrayList<String>();

        for (int j = 0; j < roster.size(); j++) {

            names.add(roster.get(j).getName());
            details.add(roster.get(j).getPosition() + " " + roster.get(j).getNumber() + " " + roster.get(j).getHeight());
        }



        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for (int i = 0; i < names.size(); i++) {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("names", names.get(i));
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

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent nextActivity = new Intent(Sport.this, Player.class);
                nextActivity.putExtra("sport", name);
                nextActivity.putExtra("name", roster.get(position).getName());
                nextActivity.putExtra("position", roster.get(position).getPosition());
                nextActivity.putExtra("number", roster.get(position).getNumber());
                nextActivity.putExtra("height", roster.get(position).getHeight());
                nextActivity.putExtra("year", roster.get(position).getYear());
                startActivity(nextActivity);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sport, menu);
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
