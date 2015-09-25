package com.example.eandreje.androidapp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
    String stringChosen;
    final String[] RemoveEdit = {"Ändra namn", "Ta bort aktivitet"};
    boolean editName = false;
    FragmentManager fragmentManager = getFragmentManager();
    CreateActivityFragment documentFrag = new CreateActivityFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activityList_array);
        activityList = (ListView) findViewById(R.id.listView);
        activityList.setAdapter(activityAdapter);
        if(activityAdapter != null)
           // activityAdapter.add(loadFromSharedPref());

        activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Activates a onItemLongClick on all "Aktiviteter".
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.main_activity_layout, documentFrag, "documentFragment");
                transaction.commit();
            }
        });

        //Menu that activates on longclick
        activityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                posChosen = position;
                stringChosen = activityAdapter.getItem(position).toString();
                {
                    Dialog();
                }
                return true;
            }
        });

    }
    //Checks if the user wants to change name on an existing "Aktivitet" or delete it.
    //If the user wants to change a name the function addOrChangeName is called.
    //If the user wants to delete an "Aktivitet", its deleted from the list and the Dialog is closed.
    public void Dialog()
    {
       // LayoutInflater li_2 = LayoutInflater.from(this);
       // View removeEditView = li_2.inflate(R.layout.remove_edit, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        //setTheme(android.R.style.Theme_Holo_Light);
       // builder.setView(removeEditView);

        builder.setTitle(stringChosen);
        builder.setItems(RemoveEdit,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    editName = true;
                    addOrChangeName();
                }
                if (which == 1) {
                    activityList_array.remove(stringChosen);
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
                if (activityList_array.contains(userInput.getText().toString()) || userInput.getText().toString().equals("") || userInput.getText().toString().contains("\n"))  {
                    Toast.makeText(MainActivity.this, "Aktiviteten måste vara unik, ej innehålla mellanslag eller ny rad", Toast.LENGTH_LONG).show();
                } else {
                    if (editName) {
                        activityList_array.set(posChosen, userInput.getText().toString());
                        //saveToSharedPref();
                        activityAdapter.notifyDataSetChanged();
                    }
                    if (!editName) {
                        activityList_array.add(userInput.getText().toString());
                        activityAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        builder.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
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
//
   //Handles saving of data to shared preferences
   public void saveToSharedPref(){
        int i = 0;
       SharedPreferences saveData = getSharedPreferences("activities", Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = saveData.edit();
        while(activityList != null) {
            editor.putString("ActivityName", activityList.getItemAtPosition(i).toString());
            editor.commit();
            i++;
        }
    }
//
//    //Handles saving of data to shared preferences
//    public String loadFromSharedPref(){
//        SharedPreferences loadData = getSharedPreferences("activities", Context.MODE_PRIVATE);
//        String activityName = loadData.getString("ActivityName", "default");
//        return activityName;
//    }
}
