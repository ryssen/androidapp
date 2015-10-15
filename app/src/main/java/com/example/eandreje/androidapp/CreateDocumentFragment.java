package com.example.eandreje.androidapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CreateDocumentFragment extends Fragment implements DefaultDialogFragment.DefaultDialogFragmentListener,
        OptionsDialogFragment.OptionsDialogFragmentListener{

    ArrayAdapter<DocItem> adapter;
    ListItem listItem;
    ListView listView;
    Activity context;
    //SharedPre sharedPre = new SharedPre();
    int key;
    int docItemID;
    CreateDocumentFragmentListener createDocumentFragmentListener;
    OptionsDialogFragment optionsDialogFragment;

    //newInstance factoring method, returns a new instance of this class
    // with custom parameter
    public static CreateDocumentFragment newInstance(ListItem listItem){
        CreateDocumentFragment fragment = new CreateDocumentFragment();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("listobject", listItem);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        listItem = getArguments().getParcelable("listobject");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Dokument");
    }

    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        key = getArguments().getInt("key");
        View view = inflater.inflate(R.layout.document_layout, container, false);
        context = getActivity();
//        sharedPre.loadDocItem(context, key);
//        listItem.getDocContainer().clear();
//        listItem.getDocContainer().addAll(sharedPre.secTemp);

        optionsDialogFragment = new OptionsDialogFragment();
        optionsDialogFragment.listener = this;

        listView = (ListView)view.findViewById(R.id.document_listview);
        //adapter = new ArrayAdapter<DocItem>(getActivity(), R.layout.row_layout, listItem.getDocContainer());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //createDocumentFragmentListener.docObjectClicked(listItem.getDocContainer().get(position));
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                optionsDialogFragment.show(getFragmentManager(), "docOptions");
                return true;
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try
        {
            createDocumentFragmentListener = (CreateDocumentFragmentListener)getActivity();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(getActivity().toString() + " must implement CreateActivityFragmentListener");
        }
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
                DefaultDialogFragment defaultDialogFragment = new DefaultDialogFragment();
                String title = "Lägg till ett nytt dokument";
                Bundle bundle = new Bundle();
                bundle.putString("addDocTitle", title);
                defaultDialogFragment.setArguments(bundle);
                defaultDialogFragment.listener = this; //interface gets its reference
                defaultDialogFragment.show(getFragmentManager(), "NewDocDialog");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void enteredText(String text) {
        adapter.add(new DocItem(text));
    }

    @Override
    public void getChoice(int pos) {
    switch (pos){
        case 0:
            DefaultDialogFragment docDialog = new DefaultDialogFragment();
            docDialog.listener = this;
            docDialog.show(getFragmentManager(), "editDocDialog");
        case 1:
            Toast.makeText(getActivity(), "you clicked 1", Toast.LENGTH_SHORT).show();
        default:
    }
    }

    public interface CreateDocumentFragmentListener{
        void docObjectClicked(DocItem doc);
    }
}

