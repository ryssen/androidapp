package com.example.eandreje.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomStringAdapter extends ArrayAdapter {
    private String name;

    public CustomStringAdapter(Context context, String[] names) {
        super(context, R.layout.custom_row_string, names);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_row_string, parent, false);
        TextView textView1 = (TextView) view.findViewById(R.id.person_name);
        TextView textView2 = (TextView) view.findViewById(R.id.column_name);
        name = (String) getItem(position);
        textView1.setText(name);
        textView2.setText("Ja");

        return view;
    }
}
