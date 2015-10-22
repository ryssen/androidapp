package com.example.eandreje.androidapp;

import android.app.Activity;
import android.graphics.Color;
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

import java.util.ArrayList;

public class CreateDocumentFragment extends Fragment implements DefaultDialogFragment.DefaultDialogFragmentListener,
        OptionsDialogFragment.OptionsDialogFragmentListener{

    private static final String DIALOG_TITLE = "Nytt namn";
    private boolean state = false;
    private ArrayAdapter<DocItem> adapter;
    private ArrayList<DocItem> docList;

    private ListItem listItem;
    private DocItem docClicked;
    private ListView listView;

    public CreateDocumentFragmentListener createDocumentFragmentListener;
    private OptionsDialogFragment optionsDialogFragment;

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
        getActivity().setTitle(listItem.name);
    }

    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.document_layout, container, false);
        docList = new ArrayList<>();
        docList = Queries.getDocuments(listItem);

        optionsDialogFragment = new OptionsDialogFragment();
        optionsDialogFragment.listener = this;

        listView = (ListView)view.findViewById(R.id.document_listview);
        adapter = new ArrayAdapter<DocItem>(getActivity(), R.layout.row_layout, docList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                createDocumentFragmentListener.docObjectClicked(docList.get(position));
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                docClicked = adapter.getItem(position);
                optionsDialogFragment.show(getFragmentManager(), "docOptions");
                return true;
            }
        });
        return view;
    }


    @Override
    public void onAttach(Activity context) {
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
            case R.id.second_view_up_cloud:
                listView.setBackgroundColor(Color.LTGRAY);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void enteredText(String text) {
        if(text.toString().contentEquals("") || text.toString().contains("\n"))
        {
            Toast.makeText(getActivity(), "Aktiviteten måste vara unik, ej innehålla mellanslag eller ny rad", Toast.LENGTH_LONG).show();
        }
        else
        if(!state)
        {
            DocItem document = new DocItem(text);
            document.parentActivity = listItem;
            document.save();
            UpdateAndSave();
        }
        else
        {
            docClicked.load(DocItem.class, docClicked.getId());
            docClicked.name = text;
            docClicked.save();
            UpdateAndSave();
            state = false;
        }
    }

    public void UpdateAndSave()
    {
        docList = Queries.getDocuments(listItem);
        adapter.clear();
        adapter.addAll(docList);
    }

    @Override
    public void getChoice(int pos) {
    switch (pos){
        case 0:
            state = true;
            DefaultDialogFragment docDialog = new DefaultDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("addDocTitle", DIALOG_TITLE);
            docDialog.setArguments(bundle);
            docDialog.listener = this;
            docDialog.show(getFragmentManager(), "editDocDialog");
            break;
        case 1:
            docClicked.delete();
            UpdateAndSave();
            break;
        default:
    }
    }

    public interface CreateDocumentFragmentListener{
        void docObjectClicked(DocItem doc);
    }
}

