package com.example.eandreje.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomStringAdapter extends ArrayAdapter {
    private List<AdapterObject> list;
    public CustomStringAdapterListener listener;

    public CustomStringAdapter(Context context, List<AdapterObject> valueList, PresenceFragment fragment) {
        super(context, R.layout.custom_row_string, valueList);
        listener = fragment;
        list = valueList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_row_string, parent, false);
        TextView textView1 = (TextView) view.findViewById(R.id.person_name);
        TextView textView2 = (TextView) view.findViewById(R.id.column_name);

        textView1.setText(list.get(position).attendant.toString());
        if(list.get(position).cellValue != null){
            textView2.setEnabled(true);
            textView2.setText(list.get(position).cellValue.value);
        }
        else
        {
            textView2.setEnabled(false);
        }
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.buttonPressed(v, list.get(position).attendant.getId());
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.buttonPressed(v, list.get(position).attendant.getId());
            }
        });
        textView1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.deletePersonString(v, list.get(position).attendant.getId());
                return true;
            }
        });
        return view;
    }

    public interface CustomStringAdapterListener{
        void buttonPressed(View v, Long position);
        void deletePersonString(View v, Long position);
    }
}
