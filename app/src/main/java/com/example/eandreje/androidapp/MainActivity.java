package com.example.eandreje.androidapp;

import android.app.FragmentManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CreateActivityFragment.Communicator,
        CustomDialogFragment.Communicator
{
    CreateActivityFragment activityFragment;

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
                .add(R.id.main_activity_layout, activityFragment, "CreateActivityFragment")
                .addToBackStack("CreateAct").commit();
    }


    @Override
    public void activeObject(CreateActivityFragment frag) {

    }

    //From dialog OK button
    @Override
    public void activityName(String name) {
        activityFragment.activityName(name);
    }
}
