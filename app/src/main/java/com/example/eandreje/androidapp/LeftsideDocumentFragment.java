package com.example.eandreje.androidapp;

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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class LeftsideDocumentFragment extends Fragment implements CreateDocumentFragment.CreateDocumentFragmentListener,
        CustomStringAdapter.CustomStringAdapterListener, DefaultDialogFragment.DefaultDialogFragmentListener{

    private DocItem document;
    private String inDocTitle;
    private String[] names = {"Kalle Henriksson", "Johannes Erikssonsonson", "Erik Andrejenko"};
    private ArrayList<Person> personList;
    private Adapter boolAdapter;
    private CustomStringAdapter stringAdapter;
    private ArrayAdapter spinnerAdapt;
    private String[] spinnerColumns = {"Närvaro", "Mobilnr"};
    private ListView listView;
    private Spinner spinner;
    ListItem listitem;

    public static LeftsideDocumentFragment newInstance(DocItem docItem) {
        LeftsideDocumentFragment leftsideDocumentFragment = new LeftsideDocumentFragment();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("activeDocument", docItem);
        leftsideDocumentFragment.setArguments(bundle);
        return leftsideDocumentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        inDocTitle = getArguments().getString("inDocTitle");
        document = getArguments().getParcelable("activeDocument");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(inDocTitle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leftside_layout, container, false);
        listView = (ListView) view.findViewById(R.id.listView4);

        personList = new ArrayList<>();
        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinnerAdapt = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerColumns);
        boolAdapter = new CustomBoolAdapter(getContext(), names);
        stringAdapter = new CustomStringAdapter(getContext(), personList, this);
        spinner.setAdapter(spinnerAdapt);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerAdapt.getItem(position).toString() == "Närvaro"){
                    listView.setAdapter((CustomBoolAdapter) boolAdapter);

                }
                else
                {
                    listView.setAdapter((CustomStringAdapter) stringAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.in_document_actionbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add_person_icon:
                DefaultDialogFragment defaultDialogFragment = new DefaultDialogFragment();
                String title = "Lägg till en ny person";
                Bundle bundle = new Bundle();
                bundle.putString("addDocTitle", title);
                defaultDialogFragment.setArguments(bundle);
                defaultDialogFragment.listener = this; //interface gets its reference
                defaultDialogFragment.show(getFragmentManager(), "newPersDialog");
                //Toast.makeText(getActivity(), document.parentActivity.toString(), Toast.LENGTH_SHORT).show();

            case R.id.add_column_icon:
                //listView.setBackgroundColor(Color.LTGRAY);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void docObjectClicked(DocItem doc) {
        //document = doc;
    }

    @Override
    public void buttonPressed(View v) {
        DefaultDialogFragment dialog = new DefaultDialogFragment();
        Bundle bundle = new Bundle();
        String title = "Ändra text";
        bundle.putString("addDocTitle", title);
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "changeCustomAdaptAttr");
    }

    @Override
    public void enteredText(String text) {
        Person person = new Person(text, document.parentActivity);
        person.save();
        personList = Queries.getPersons(document.parentActivity);
        stringAdapter.clear();
        stringAdapter.addAll(personList);
        stringAdapter.notifyDataSetChanged();

    }
}
