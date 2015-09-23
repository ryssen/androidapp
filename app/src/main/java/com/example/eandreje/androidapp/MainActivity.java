package com.example.eandreje.androidapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView activityList;
    ArrayAdapter<String> activityAdapter;
    String[] newActivity = {"Fotboll", "Konferens", "Möte"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, newActivity);
        activityList = (ListView)findViewById(R.id.listView);
        activityList.setAdapter(activityAdapter);

        //Item listener
        AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundResource(R.color.main_text_color);
            }
        };
        activityList.setOnItemClickListener(itemClick);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.add_activity_icon:

                Toast.makeText(MainActivity.this, "Ny aktivitet tillagd", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
