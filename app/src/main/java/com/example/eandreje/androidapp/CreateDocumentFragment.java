package com.example.eandreje.androidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CreateDocumentFragment extends Fragment {
    ArrayAdapter<DocItem> adapter;
    ListItem docItem = new ListItem();

    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.document_layout, container, false);
        ListView listView = (ListView)view.findViewById(R.id.document_listview);
        adapter = new ArrayAdapter<DocItem>(getActivity(), R.layout.row_layout, docItem.getDocContainer());
        listView.setAdapter(adapter);

        Button add = (Button)view.findViewById(R.id.newButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //EditText text = (EditText)v.findViewById(R.id.editText);
                //name = text.getText().toString();
//                docItem.getDocContainer().add(new DocItem("hej"));
//                adapter.notifyDataSetChanged();
                adapter.add(new DocItem("hej"));
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    //Receives the docList from the caller object
    //in Activity Listview, and is used to initiate the adapter in onCreate()
    public void addToActivity(ListItem activity){
        docItem = activity;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // inflater.inflate(R.menu.document_actionbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle presses on the action bar items
        switch (item.getItemId())
        {
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

