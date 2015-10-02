package com.example.eandreje.androidapp;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CreateDocumentFragment extends Fragment implements AddDocDialogFragment.Communicator {
    ArrayAdapter<DocItem> adapter;
    ListItem docItem = new ListItem();
    ListItem listItem;
    private String docName;

    public static final CreateDocumentFragment newInstance(ListItem listItem){
        CreateDocumentFragment fragment = new CreateDocumentFragment();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("key", listItem);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        listItem = getArguments().getParcelable("key");
    }

    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.document_layout, container, false);
        ListView listView = (ListView)view.findViewById(R.id.document_listview);
        adapter = new ArrayAdapter<DocItem>(getActivity(), R.layout.row_layout, listItem.getDocContainer());
        listView.setAdapter(adapter);

//        Button addDocButton = (Button)view.findViewById(R.id.add_doc_icon);
//        addDocButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        return view;
    }

    //Receives the docList from the caller object
    //in Activity Listview, and is used to initiate the adapter in onCreate()
    public void addToActivity(ListItem activity){
        docItem = activity;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.document_actionbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle presses on the action bar items
        switch (item.getItemId())
        {
            case R.id.add_doc_icon:
                AddDocDialogFragment addDocDialogFragment = new AddDocDialogFragment();
                addDocDialogFragment.show(getFragmentManager(), "NewDocDialog");
                //adapter.add(new DocItem("hej"));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void documentName(String name) {
        Toast.makeText(getActivity(), "hallooo", Toast.LENGTH_SHORT).show();
        adapter.add(new DocItem(name));
    }
}

