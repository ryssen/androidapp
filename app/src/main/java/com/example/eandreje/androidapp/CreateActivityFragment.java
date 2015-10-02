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

public class CreateActivityFragment extends Fragment implements CustomDialogFragment.Communicator{
    SharedPre sharedPre;
    //kolla hur activity ska refereras
    //Activity context = getActivity();
    ListView activityListView;
    static ArrayAdapter<ListItem> activityAdapter;
    static List<ListItem> activityList = new ArrayList<ListItem>();
    Communicator communicator;
    //CustomDialogFragment.Communicator communicator;

    int posChosen;
    String stringChosen;
    final String[] RemoveEdit = {"Ändra namn", "Ta bort aktivitet"};
    boolean editName = false;

    //Recieved listitem name from dialog
    @Override
    public void activityName(String name) {
        activityAdapter.add(new ListItem(name));
    }

    public interface Communicator{
        void activeObject(ListItem listItem);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
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
                communicator.activeObject(activityList.get(position));
            }
        });
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
        return view;
}

//    public static List<ListItem> getActivityList() {
//        return activityList;
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            communicator = (Communicator)getActivity();
        }
        catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + " must implement Communicator");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.add_activity_icon:
                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                customDialogFragment.show(getFragmentManager(), "dialog");

                //editName = false;
                //addOrChangeName();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
//                CreateDocumentFragment docFragment = new CreateDocumentFragment();
//                //FragmentManager fm = getFragmentManager();
//                //docFragment.addToActivity(activityList.get(position));
////                FragmentTransaction ft = fm.beginTransaction();
////                ft.add(R.id.main_activity_layout, docFragment, "docFragment");
////                ft.addToBackStack(null);
////                ft.commit();
////            getSupportFragmentManager().beginTransaction()
////                    .add(R.id.main_activity_layout, docFragment)
////                    .addToBackStack(null).commit();
//            }