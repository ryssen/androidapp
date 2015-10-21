package com.example.eandreje.androidapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class OptionsDialogFragment extends android.support.v4.app.DialogFragment {
    public OptionsDialogFragmentListener listener;
    private ArrayList<String> optionsList;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    public OptionsDialogFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflate = getActivity().getLayoutInflater();
        View view = inflate.inflate(R.layout.act_options_layout, null);
        builder.setView(view);

        listView = (ListView)view.findViewById(R.id.listView3);
        optionsList = new ArrayList<>();
        optionsList.add("Ändra namn");
        optionsList.add("Ta bort rad");
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, optionsList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.getChoice(position);
                dismiss();
            }
        });
        Dialog dialog = builder.create();
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public interface OptionsDialogFragmentListener{
        void getChoice(int pos);
    }
}



