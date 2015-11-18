package com.example.eandreje.androidapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
    private List<DocItem> docList;

    public OptionsDialogFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflate = getActivity().getLayoutInflater();
        View view = inflate.inflate(R.layout.act_options_layout, null);

        docList = new ArrayList<>();
        ListView listView = (ListView) view.findViewById(R.id.listView3);
        ArrayList<String> optionsList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, optionsList);
        ArrayAdapter<DocItem> docAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, docList);
        final TextView description = (TextView)view.findViewById(R.id.default_dialog_description);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(getArguments().getString("DialogTitle") != null && getArguments().getString("DialogDesc") != null) {
            builder.setTitle(getArguments().getString("DialogTitle"));
            description.setText(getArguments().getString("DialogDesc"));
        }
//        builder.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
        switch (getArguments().getInt("Caller")){
            case R.id.listView:
                optionsList.add("Radera");
                listView.setAdapter(adapter);
                break;
            case R.id.second_view_up_cloud:
                docList = Queries.getDocuments((ListItem) getArguments().getParcelable("ParentAct_export"));
                listView.setAdapter(docAdapter);
                docAdapter.clear();
                docAdapter.addAll(docList);
                break;
            case R.id.import_persons:
                docList = Queries.getDocuments((ListItem) getArguments().getParcelable("ParentAct"));
                DocItem doc1 = getArguments().getParcelable("ActiveDoc");
                docList.remove(doc1);
                listView.setAdapter(docAdapter);
                docAdapter.clear();
                docAdapter.addAll(docList);
                break;
            case R.id.import_persons_columns:
                docList = Queries.getDocuments((ListItem) getArguments().getParcelable("ParentAct"));
                DocItem doc2 = getArguments().getParcelable("ActiveDoc");
                docList.remove(doc2);
                listView.setAdapter(docAdapter);
                docAdapter.clear();
                docAdapter.addAll(docList);
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
                        listener.importPers(docList.get(position));
                        dismiss();
                        break;
                    case R.id.second_view_up_cloud:
                        listener.importPers(docList.get(position));
                        dismiss();
                        break;
                    case R.id.import_persons_columns:
                        listener.importPersCol(docList.get(position));
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
        void importPers(DocItem doc);
        void importPersCol(DocItem doc);
    }
}



