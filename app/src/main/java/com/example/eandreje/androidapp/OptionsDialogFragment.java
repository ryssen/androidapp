package com.example.eandreje.androidapp;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflate = getActivity().getLayoutInflater();
        View view = inflate.inflate(R.layout.act_options_layout, null);
        if(getArguments().getString("DialogTitle") != null)
            builder.setTitle(getArguments().getString("DialogTitle"));
        ListView listView = (ListView) view.findViewById(R.id.listView3);
        ArrayList<String> optionsList = new ArrayList<>();
        docList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, optionsList);
        final ArrayAdapter<DocItem> docAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, docList);

        TextView description = (TextView)view.findViewById(R.id.default_dialog_description);
        if(getArguments().getString("DialogDesc") != null)
            description.setText(getArguments().getString("DialogDesc"));
        builder.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        if(getArguments().getInt("Caller") == R.id.import_persons) {
            docList = Queries.getDocuments((ListItem) getArguments().getParcelable("ParentAct"));
            listView.setAdapter(docAdapter);
            docAdapter.clear();
            docAdapter.addAll(docList);
        }
        else if (getArguments().getInt("Caller") == R.id.listView) {
            optionsList.add("Ta bort rad");
            listView.setAdapter(adapter);
        }
        else{
            optionsList.add("Ta bort rad");
            optionsList.add("Ändra namn");
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getArguments().getInt("Caller") == R.id.import_persons)
                    listener.getDocChoice(docList.get(position));
                else
                    listener.getChoice(position);
                dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }

    public interface OptionsDialogFragmentListener{
        void getChoice(int pos);
        void getDocChoice(DocItem doc);
    }
}



