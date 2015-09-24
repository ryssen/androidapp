package com.example.eandreje.androidapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView activityList;
    ArrayAdapter<String> activityAdapter;
    ArrayList<String> activityList_array = new ArrayList<String>();
    int posChosen;
    String stringchosen;
    final String[] RemoveEdit = {"Ändra namn", "Ta bort Aktivitet"};
    boolean editName = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        activityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activityList_array);
        activityList = (ListView) findViewById(R.id.listView);
        activityList.setAdapter(activityAdapter);

        activityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            //Activates a onItemLongClick on all "Aktiviteter".
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                posChosen = position;
                stringchosen = activityAdapter.getItem(position).toString();
                {
                    Dialog();
                }
                return false;
            }
        });

    }
    //Checks if the user wants to change name on an existing "Aktivitet" or delete it.
    //If the user wants to change a name the function addOrChangeName is called.
    //If the user wants to delete an "Aktivitet", its deleted from the list and the Dialog is closed.
    public void Dialog()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(stringchosen);
        builder.setItems(RemoveEdit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    editName= true;

                    addOrChangeName();
                }
                if (which == 1) {
                    activityList_array.remove(stringchosen);
                    activityAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }
    //This function either adds or changes a name depending on if the boolean EditName = true/false.
    //The boolean is set in the function Dialog above and is set to true or false depending on if the user
    //clicked "Ändra namn" or "ta bort namn" in the RemoveEdit-string set in the Dialog.
    //The title on the Alert will also change depending on what the user has chosen.
    //Its not possible to enter a name which already exists.
    public void addOrChangeName()
    {
        LayoutInflater li = LayoutInflater.from(this);
        View add_activityView = li.inflate(R.layout.add_activity, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(add_activityView);

        final EditText userInput = (EditText) add_activityView.findViewById(R.id.user_input);

        builder.setCancelable(false);
        if(editName)
        {
            builder.setTitle(R.string.changeName);
        }
        if(!editName)
        {
            builder.setTitle(R.string.add_activity_title);
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(activityList_array.contains(userInput.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Namnet måste vara unikt", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(editName)
                    {
                        activityList_array.set(posChosen, userInput.getText().toString());
                        activityAdapter.notifyDataSetChanged();
                    }
                    if(!editName)
                    {
                        activityList_array.add(userInput.getText().toString());
                        activityAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle presses on the action bar items
        switch (item.getItemId())
        {
            case R.id.add_activity_icon:
                editName = false;
                addOrChangeName();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
