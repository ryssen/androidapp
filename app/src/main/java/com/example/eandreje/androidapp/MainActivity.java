package com.example.eandreje.androidapp;

import android.support.v4.app.FragmentTransaction;
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
        this.setTitle("Aktiviteter");
    }

    //activeObject recieves the clicked listobject(activities) which is forwarded
    //to the static newInstance method.
    @Override
    public void activeObject(ListItem listItem) {
        documentFragment = CreateDocumentFragment.newInstance(listItem);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_layout, documentFragment)
                .addToBackStack(null).commit();
        this.setTitle("Dokument");
    }

    //docObject recieves the clicked listobject(document)
    @Override
    public void docObjectClicked(DocItem doc) {
        leftsideDocumentFragment = new LeftsideDocumentFragment().newInstance(doc);
        rightsideDocumentFragment = new RightsideDocumentFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.remove(documentFragment);
        transaction.replace(R.id.left_side, leftsideDocumentFragment).addToBackStack(null);
        transaction.replace(R.id.right_side, rightsideDocumentFragment).addToBackStack(null);
        transaction.commit();

    }
}
