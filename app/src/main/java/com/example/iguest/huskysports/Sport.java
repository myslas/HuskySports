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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
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
        String name = intent.getStringExtra("name");
        TextView sportName = (TextView) findViewById(R.id.sportName);
        sportName.setText(name);
        final ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<Roster> roster;
        final ArrayList<UWsports> uwsports = MySingleton.getInstance().getElements();
        if (name.equals("baseball")) {
            roster = uwsports.get(2).getRoster();
        } else if (name.equals("basketball")) {
            roster = uwsports.get(0).getRoster();
        } else {
            roster = uwsports.get(1).getRoster();
        }

        Log.i(TAG, "" + roster);
        HashMap<String, String> item;
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> details = new ArrayList<>();

        for (int j = 0; j < roster.size(); j++) {

            names.add(roster.get(j).getName());
            details.add(roster.get(j).getPosition() + " " + roster.get(j).getNumber() + " " + roster.get(j).getHeight());
        }



        for(int i=0;i<names.size();i++){

            item = new HashMap<String,String>();

            item.put( "line1", names.get(i));

            item.put( "line2", details.get(i));

            list.add( item );

        }

        sa = new SimpleAdapter(this, list,android.R.layout.two_line_list_item , new String[] { "line1","line2" }, new int[] {android.R.id.text1, android.R.id.text2});

        myList = (ListView)findViewById(R.id.listView);
        myList.setAdapter(sa);


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
