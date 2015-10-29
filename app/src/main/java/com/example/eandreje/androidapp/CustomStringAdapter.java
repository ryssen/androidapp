package com.example.eandreje.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomStringAdapter extends ArrayAdapter {
    private ArrayList<ColumnContent> valueList;
    public CustomStringAdapterListener listener;
    int listItemPos;

    public CustomStringAdapter(Context context, ArrayList<ColumnContent> values, LeftsideDocumentFragment fragment) {
        super(context, R.layout.custom_row_string, values);
        listener = fragment;
        valueList = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_row_string, parent, false);
        //listItemPos = position;
        TextView textView1 = (TextView) view.findViewById(R.id.person_name);
        TextView textView2 = (TextView) view.findViewById(R.id.column_name);

        textView1.setText(valueList.get(position).getParentPerson().toString());
        textView2.setText(valueList.get(position).toString());

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.buttonPressed(v, position);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.buttonPressed(v, position);
            }
        });

        return view;
    }

    public interface CustomStringAdapterListener{
        void buttonPressed(View v, int position);
    }
}
