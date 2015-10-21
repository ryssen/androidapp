package com.example.eandreje.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomBoolAdapter extends ArrayAdapter {
    private String name;

    public CustomBoolAdapter(Context context, String[] names) {
        super(context, R.layout.custom_row_boolean, names);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_row_boolean, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.person_name);
        name = (String) getItem(position);
        textView.setText(name);

        return view;
    }
}
