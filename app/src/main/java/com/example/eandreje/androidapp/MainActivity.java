package com.example.eandreje.androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CreateActivityFragment.Communicator,
        CustomDialogFragment.Communicator, AddDocDialogFragment.Communicator
{
    CreateActivityFragment activityFragment;
    CreateDocumentFragment documentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityFragment = new CreateActivityFragment();
        //FragmentManager fm = getFragmentManager();
        //docFragment.addToActivity(activityList.get(position));
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.add(R.id.main_activity_layout, docFragment, "docFragment");
//                ft.addToBackStack(null);
//                ft.commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_activity_layout, activityFragment)
                .addToBackStack(null).commit();
    }


    @Override
    public void activeObject(ListItem listItem) {
        documentFragment = new CreateDocumentFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_layout, CreateDocumentFragment.newInstance(listItem))
                .addToBackStack(null).commit();
    }

    //From dialog OK button
    @Override
    public void activityName(String name) {
        activityFragment.activityName(name);
    }

    @Override
    public void documentName(String name) {

    }
}
