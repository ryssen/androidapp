package com.example.eandreje.androidapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateActivityFragment extends Fragment implements DefaultDialogFragment.DefaultDialogFragmentListener {
    SharedPre sharedPre;
    ListView activityListView;
    ArrayAdapter<ListItem> activityAdapter;
    List<ListItem> activityList = new ArrayList<ListItem>();
    CreateActivityFragmentListener createActivityFragmentListener;

//    int posChosen;
//    String stringChosen;
//    final String[] RemoveEdit = {"Ändra namn", "Ta bort aktivitet"};
//    boolean editName = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activities_layout, container, false);
        getActivity().supportInvalidateOptionsMenu();
//        sharedPre = new SharedPre();
//        sharedPre.loadFromSharedPref(context);
//        activityList = sharedPre.tempList;
        activityAdapter = new ArrayAdapter<ListItem>(getActivity(), android.R.layout.simple_list_item_1, activityList);
        activityListView = (ListView)view.findViewById(R.id.listView);
        activityListView.setAdapter(activityAdapter);

        activityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                createActivityFragmentListener.activeObject(activityList.get(position));
            }
        });
        activityListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                OptionsDialogFragment optionsDialog = new OptionsDialogFragment();
                optionsDialog.show(getFragmentManager(), "Options");
                return false;
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try
        {
            createActivityFragmentListener = (CreateActivityFragmentListener)getActivity();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(getActivity().toString() + " must implement CreateActivityFragmentListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_activity_icon:
                DefaultDialogFragment defaultDialogFragment = new DefaultDialogFragment();
                defaultDialogFragment.listener = this;
                defaultDialogFragment.show(getFragmentManager(), "dialog");

                //editName = false;
                //addOrChangeName();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Recieved name from userinput in dialog
    @Override
    public void enteredText(String text) {
        activityAdapter.add(new ListItem(text));
    }

    public interface CreateActivityFragmentListener {
        void activeObject(ListItem listItem);
}
}


//Menu that activates on longclick
//    activityListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//        @Override
//        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//            posChosen = position;
//            stringChosen = activityAdapter.getItem(position).toString();
//            {
//                Dialog(position);
//            }
//            return true;
//        }
//    });