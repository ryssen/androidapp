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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OptionsDialogFragment extends android.support.v4.app.DialogFragment {
    public OptionsDialogFragmentListener listener;
    private List<Event> eventList;

    public OptionsDialogFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        listener = (OptionsDialogFragmentListener) getTargetFragment();
        LayoutInflater inflate = getActivity().getLayoutInflater();
        View view = inflate.inflate(R.layout.act_options_layout, null);
        eventList = new ArrayList<>();
        ListView listView = (ListView) view.findViewById(R.id.listView3);
        ArrayList<String> optionsList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, optionsList);
        ArrayAdapter<Event> eventAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, eventList);
        final TextView description = (TextView)view.findViewById(R.id.default_dialog_description);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(getArguments().getString("DialogTitle") != null && getArguments().getString("DialogDesc") != null) {
            builder.setTitle(getArguments().getString("DialogTitle"));
            description.setText(getArguments().getString("DialogDesc"));
        }

        switch (getArguments().getInt("Caller")){
            case R.id.listView:
                optionsList.add("Radera");
                listView.setAdapter(adapter);
                break;
            case R.id.second_view_up_cloud:
                eventList = Queries.getEvents((Category) getArguments().getParcelable("ParentAct_export"));
                listView.setAdapter(eventAdapter);
                eventAdapter.clear();
                eventAdapter.addAll(eventList);
                break;
            case R.id.import_persons:
                eventList = Queries.getEvents((Category) getArguments().getParcelable("ParentAct"));
                Event doc1 = getArguments().getParcelable("ActiveDoc");
                eventList.remove(doc1);
                listView.setAdapter(eventAdapter);
                eventAdapter.clear();
                eventAdapter.addAll(eventList);
                break;
            case R.id.import_persons_columns:
                eventList = Queries.getEvents((Category) getArguments().getParcelable("ParentAct"));
                Event doc2 = getArguments().getParcelable("ActiveDoc");
                eventList.remove(doc2);
                listView.setAdapter(eventAdapter);
                eventAdapter.clear();
                eventAdapter.addAll(eventList);
                break;
            default:
                optionsList.add("Ändra namn");
                optionsList.add("Radera");
                listView.setAdapter(adapter);
                break;
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (getArguments().getInt("Caller")) {
                    case R.id.import_persons:
                        listener.importAttendants(eventList.get(position));
                        dismiss();
                        break;
                    case R.id.second_view_up_cloud:
                        listener.importAttendants(eventList.get(position));
                        dismiss();
                        break;
                    case R.id.import_persons_columns:
                        listener.importPersCol(eventList.get(position));
                        dismiss();
                        break;
                    default:
                        listener.getChoice(position);
                        dismiss();
                        break;
                }
            }});
        builder.setView(view);
        return builder.create();
    }

    public interface OptionsDialogFragmentListener{
        void getChoice(int pos);
        void importAttendants(Event doc);
        void importPersCol(Event doc);
    }
}



