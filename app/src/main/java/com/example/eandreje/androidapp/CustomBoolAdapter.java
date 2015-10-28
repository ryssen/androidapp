package com.example.eandreje.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomBoolAdapter extends ArrayAdapter {
    private ArrayList<ColumnContent> list;

    public CustomBoolAdapter(Context context, ArrayList<ColumnContent> values, LeftsideDocumentFragment leftsideDocumentFragment) {
        super(context, R.layout.custom_row_boolean, values);
        list = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_row_boolean, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.person_name);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkbox_value);

        textView.setText(list.get(position).getParentPerson().toString());
        //if(list.get(position).)

        return view;
    }
}
