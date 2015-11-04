package com.example.eandreje.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomBoolAdapter extends ArrayAdapter {
    private ArrayList<ColumnContent> list;
    public CustomBoolAdapterListener listener;

    public CustomBoolAdapter(Context context, ArrayList<ColumnContent> values, LeftsideDocumentFragment leftsideDocumentFragment) {
        super(context, R.layout.custom_row_boolean, values);
        listener = leftsideDocumentFragment;
        list = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_row_boolean, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.person_name);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkbox_value);

        textView.setText(list.get(position).getParentPerson().toString());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.textboxClick(v, list.get(position).getParentPerson().getId());
            }
        });
        checkBox.setChecked(Boolean.parseBoolean(list.get(position).value));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.checkboxChange(buttonView, list.get(position).getParentPerson().getId(), isChecked);
            }
        });
        return view;
    }

    public interface CustomBoolAdapterListener{
        void checkboxChange(View v, Long position, boolean bool);
        void textboxClick(View v, Long position);
    }
}
