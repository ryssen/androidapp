package com.example.eandreje.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.List;

public class CustomBoolAdapter extends ArrayAdapter {
    private List<AdapterObject> list;
    public CustomBoolAdapterListener listener;

    public CustomBoolAdapter(Context context, List<AdapterObject> values, PresenceFragment presenceFragment) {
        super(context, R.layout.custom_row_boolean, values);
        listener = presenceFragment;
        list = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_row_boolean, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.person_name);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox_value);

        textView.setText(list.get(position).attendant.toString());
        if(list.get(position).cellValue != null){
            checkBox.setVisibility(View.VISIBLE);
            if(list.get(position).cellValue.value.equals("")){
                list.get(position).cellValue.setValue("true");
                list.get(position).cellValue.save();
                checkBox.setChecked(Boolean.parseBoolean(list.get(position).cellValue.value));
            }
            else
                checkBox.setChecked(Boolean.parseBoolean(list.get(position).cellValue.value));
        }
        else {
            checkBox.setVisibility(View.INVISIBLE);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.textboxClick(v, list.get(position).attendant.getId());
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.deletePersonBool(v, list.get(position).attendant.getId());
                return true;
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.checkboxChange(buttonView, list.get(position).attendant.getId(), isChecked);
            }
        });
        return view;
    }

    public interface CustomBoolAdapterListener{
        void checkboxChange(View v, Long position, boolean bool);
        void textboxClick(View v, Long position);
        void deletePersonBool(View v, Long position);
    }
}
