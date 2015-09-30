package com.example.eandreje.androidapp;

import android.app.AlertDialog;
import android.app.FragmentManager;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView activityListView;
    static ArrayAdapter<ListItem> activityAdapter;
    static List<ListItem> activityList = new ArrayList<ListItem>();

    int posChosen;
    String stringChosen;
    final String[] RemoveEdit = {"Ändra namn", "Ta bort aktivitet"};
    boolean editName = false;

    //How does it work?
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityAdapter = new ArrayAdapter<ListItem>(this, android.R.layout.simple_list_item_1, activityList);
        activityListView = (ListView) findViewById(R.id.listView);
        activityListView.setAdapter(activityAdapter);


        activityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CreateDocumentFragment docFragment = new CreateDocumentFragment();
                FragmentManager fm = getFragmentManager();
                docFragment.addToActivity(activityList.get(position));
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.add(R.id.main_activity_layout, docFragment, "docFragment");
//                ft.addToBackStack(null);
//                ft.commit();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_activity_layout, docFragment)
                        .addToBackStack(null).commit();
            }
        });

        //Menu that activates on longclick
        activityListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                posChosen = position;
                stringChosen = activityAdapter.getItem(position).toString();
                {
                    Dialog(position);
                }
                return true;
            }
        });
    }

    public static List<ListItem> getActivityList() {
        return activityList;
    }

    //Checks if the user wants to change name on an existing "Aktivitet" or delete it.
    //If the user wants to change a name the function addOrChangeName is called.
    //If the user wants to delete an "Aktivitet", its deleted from the list and the Dialog is closed.
    public void Dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                            ListItem itemToRemove = activityAdapter.getItem(position);
                            activityAdapter.remove(itemToRemove);
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
    public void addOrChangeName() {
        LayoutInflater li = LayoutInflater.from(this);
        View add_activityView = li.inflate(R.layout.add_activity, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(add_activityView);
        final EditText userInput = (EditText) add_activityView.findViewById(R.id.user_input);
        builder.setCancelable(false);
        if (editName) {
            builder.setTitle(R.string.changeName);
        }
        if (!editName) {
            builder.setTitle(R.string.add_activity_title);
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (activityList.contains(userInput.getText().toString())
                        || userInput.getText().toString().equals("")
                        || userInput.getText().toString().contains("\n")) {
                    Toast.makeText(MainActivity.this,
                            "Aktiviteten måste vara unik, ej innehålla mellanslag eller ny rad",
                            Toast.LENGTH_LONG).show();
                } else {
                    if (editName) {
                        activityAdapter.getItem(posChosen).setId(userInput.getText().toString());
                    }
                    if (!editName) {
                        activityAdapter.add(new ListItem(userInput.getText().toString()));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.add_activity_icon:
                editName = false;
                addOrChangeName();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
