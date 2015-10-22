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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class LeftsideDocumentFragment extends Fragment implements CreateDocumentFragment.CreateDocumentFragmentListener{
    private DocItem document;
    private String inDocTitle;
    private String[] names = {"Kalle", "Anna", "Ola", "Bengt", "Lisa", "Ulf", "Johan"};
    private Adapter boolAdapter;
    private Adapter stringAdapter;
    private ArrayAdapter spinnerAdapt;
    private String[] spinnerColumns = {"Närvaro", "Mobilnr"};
    private ListView listView;
    private Spinner spinner;

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
        document = getArguments().getParcelable("activeDocument");
        inDocTitle = getArguments().getString("inDocTitle");
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
        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinnerAdapt = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerColumns);
        boolAdapter = new CustomBoolAdapter(getActivity(), names);
        stringAdapter = new CustomStringAdapter(getActivity(), names);
        spinner.setAdapter(spinnerAdapt);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerAdapt.getItem(position).toString() == "Närvaro"){
                    listView.setAdapter((CustomBoolAdapter) boolAdapter);
//                    ((CustomBoolAdapter) listView.getAdapter()).notifyDataSetChanged();
//                    Toast.makeText(getActivity(), "Bool", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    listView.setAdapter((CustomStringAdapter) stringAdapter);

//                    Toast.makeText(getActivity(), "String", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.in_document_actionbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void docObjectClicked(DocItem doc) {
    }
}
