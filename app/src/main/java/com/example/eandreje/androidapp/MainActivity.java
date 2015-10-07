package com.example.eandreje.androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CreateActivityFragment.CreateActivityFragmentListener,
        CreateDocumentFragment.CreateDocumentFragmentListener
{
    CreateActivityFragment activityFragment;
    CreateDocumentFragment documentFragment;
    LeftsideDocumentFragment leftsideDocumentFragment;
    RightsideDocumentFragment rightsideDocumentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityFragment = new CreateActivityFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_activity_layout, activityFragment)
                .commit();
    }

    //activeObject recieves the clicked listobject(activities) which is forwarded
    //to the static newInstance method.
    @Override
    public void activeObject(ListItem listItem) {
        documentFragment = CreateDocumentFragment.newInstance(listItem);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_layout, documentFragment)
                .addToBackStack(null).commit();
    }

    //docObject recieves the clicked listobject(document)
    @Override
    public void docObjectClicked(DocItem doc) {
        leftsideDocumentFragment = new LeftsideDocumentFragment().newInstance(doc);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_layout, leftsideDocumentFragment)
                .addToBackStack(null).commit();
    }
}
