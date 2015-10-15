package com.example.eandreje.androidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class RightsideDocumentFragment extends Fragment {
    private ArrayAdapter<String> adapter;
    private ArrayList<String> columnList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rightside_layout, container, false);
        Spinner columns = (Spinner)view.findViewById(R.id.right_spinner);
        columnList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, columnList);
        columns.setAdapter(adapter);
        adapter.add("Närvaro");
        adapter.add("Mobilnr");
        adapter.add("Betalt");

        return view;
    }
}
