package com.example.eandreje.androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CategoryFragment.CategoryFragmentListener,
        EventFragment.EventFragmentListener
{
    private Event activeObject;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("ParentAct", activeObject);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            CategoryFragment categoryFragment = new CategoryFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_activity_layout, categoryFragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    //activeObject recieves the clicked listobject(activities) which is forwarded
    //to the static newInstance method.
    @Override
    public void activeObject(Category category) {
        EventFragment eventFragment = EventFragment.newInstance(category);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_layout, eventFragment)
                .addToBackStack(null).commit();
    }

    //docObject recieves the clicked listobject(document)
    @Override
    public void eventObjectClicked(Event event) {
        activeObject = event;
        PresenceFragment presenceFragment = PresenceFragment.newInstance(event);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_layout, presenceFragment)
                .addToBackStack(null).commit();

    }
}
