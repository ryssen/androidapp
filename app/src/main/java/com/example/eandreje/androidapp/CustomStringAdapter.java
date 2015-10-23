package com.example.eandreje.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomStringAdapter extends ArrayAdapter implements DefaultDialogFragment.DefaultDialogFragmentListener {
    private ArrayList<Person> nameList;
    public CustomStringAdapterListener listener;

    public CustomStringAdapter(Context context, ArrayList<Person> names, LeftsideDocumentFragment fragment) {
        super(context, R.layout.custom_row_string);
        listener = fragment;
        nameList = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_row_string, parent, false);
        TextView textView1 = (TextView) view.findViewById(R.id.person_name);
        TextView textView2 = (TextView) view.findViewById(R.id.column_name);

        textView1.setText(getItem(position).toString());
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.buttonPressed(v);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.buttonPressed(v);
            }
        });
        return view;
    }


    @Override
    public void enteredText(String text) {
    }

    public interface CustomStringAdapterListener{
        void buttonPressed(View v);
    }
}
