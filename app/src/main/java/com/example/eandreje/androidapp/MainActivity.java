package com.example.eandreje.androidapp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView activityList;
    ArrayAdapter<String> activityAdapter;
    ArrayList<String> activityList_array = new ArrayList<String>();
    TextView chosen;
    String hej = "ändrad";
    String temp;
    String stringchosen;
    final String[] RemoveEdit = {"Ändra namn", "Ta bort Aktivitet"};
    boolean editName = false;
    //String[] newActivity = {"Fotboll", "Konferens", "Möte"};
    FragmentManager fragmentManager = getFragmentManager();
    CreateActivityFragment documentFrag = new CreateActivityFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
        
        
        setContentView(R.layout.activity_main);
        activityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activityList_array);
        activityList = (ListView) findViewById(R.id.listView);
        activityList.setAdapter(activityAdapter);

        activityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
       //Listview item listener, creates fragment on click
        //AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                chosen = (TextView)view;

                stringchosen = activityAdapter.getItem(position).toString();
                Toast.makeText(MainActivity.this, stringchosen, Toast.LENGTH_SHORT).show();
                {
                    Dialog();
                }


                return false;
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.add(R.id.main_activity_layout, documentFrag, "documentFragment");
//                transaction.commit();
//            }
                //return builder.create();
            }
        });

    }

    public void Dialog()
    {
        //Toast.makeText(MainActivity.this, chosen, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(stringchosen);
        //builder.setNegativeButton("Cancel", null);
        builder.setItems(RemoveEdit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    editName= true;
                    addOrChangeName();
                }
                if (which == 1) {
                    Toast.makeText(MainActivity.this, stringchosen, Toast.LENGTH_SHORT).show();
                    activityList_array.remove(stringchosen);
                    activityAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }
    public void addOrChangeName()
    {
        LayoutInflater li = LayoutInflater.from(this);
        View add_activityView = li.inflate(R.layout.add_activity, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(add_activityView);

        final EditText userInput = (EditText) add_activityView.findViewById(R.id.user_input);

        builder.setCancelable(false);
        if(editName==true)
        {
            builder.setTitle(R.string.changeName);
        }
        if(editName==false)
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
                    if(editName==true)
                    {
                        Toast.makeText(MainActivity.this, "CHECK!!!!", Toast.LENGTH_SHORT).show();
                        temp = userInput.getText().toString();

                       chosen.setText("ÄNDRAD!!!");
                        activityAdapter.notifyDataSetChanged();
                    }
                    if(editName==false)
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

        //Item listener
//        AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                view.setBackgroundResource(R.color.main_text_color);
//            }
//        };
//        activityList.setOnItemClickListener(itemClick);



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
//                LayoutInflater li = LayoutInflater.from(this);
//                View add_activityView = li.inflate(R.layout.add_activity, null);
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setView(add_activityView);
//
//                final EditText userInput = (EditText) add_activityView.findViewById(R.id.user_input);
//
//                builder.setCancelable(false);
//                builder.setTitle("Lägg till Aktivitet");
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if(activityList_array.contains(userInput.getText().toString()))
//                        {
//                            dialog.cancel();
//                            Toast.makeText(MainActivity.this, "Namnet måste vara unikt", Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            activityList_array.add(userInput.getText().toString());
//                            activityAdapter.notifyDataSetChanged();
//                        }
//
//
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//


                        Toast.makeText(MainActivity.this, "Ny aktivitet tillagd", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
