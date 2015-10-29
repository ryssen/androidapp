package com.example.eandreje.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomBoolAdapter extends ArrayAdapter {
    private ArrayList<ColumnContent> list;
    CustomBoolAdapterListener listener;
    private int listItemPos;

    public CustomBoolAdapter(Context context, ArrayList<ColumnContent> values, LeftsideDocumentFragment leftsideDocumentFragment) {
        super(context, R.layout.custom_row_boolean, values);
        list = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_row_boolean, parent, false);
        listItemPos = position;
        TextView textView = (TextView) view.findViewById(R.id.person_name);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkbox_value);

        textView.setText(list.get(position).getParentPerson().toString());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.textboxClick(v, listItemPos);
            }
        });
        checkBox.setChecked(Boolean.parseBoolean(list.get(position).value));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.checkboxChange(buttonView, listItemPos, isChecked);
            }
        });
        return view;
    }

    public interface CustomBoolAdapterListener{
        void checkboxChange(View v, int position, boolean bool);
        void textboxClick(View v, int position);
    }
}
