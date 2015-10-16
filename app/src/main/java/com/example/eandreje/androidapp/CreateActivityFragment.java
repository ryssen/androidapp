package com.example.eandreje.androidapp;

import android.app.Activity;
import android.content.ClipData;
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

public class CreateActivityFragment extends Fragment implements DefaultDialogFragment.DefaultDialogFragmentListener,
        OptionsDialogFragment.OptionsDialogFragmentListener{

    //SharedPre sharedPre;
    ListView activityListView;
    ArrayAdapter<ListItem> activityAdapter;
    List<ListItem> activityList = new ArrayList<ListItem>();
    CreateActivityFragmentListener createActivityFragmentListener;
    OptionsDialogFragment optionsDialog;
    Context context;
    ListItem ItemClicked;
    final String DIALOG_TITLE = "Ändra namn på ";

    OptionsDialogFragment optionsDialogFragment;
    int key;
    int Li_ID;
    boolean nameExists = false;

//    int posChosen;
//    String stringChosen;
//    final String[] RemoveEdit = {"Ändra namn", "Ta bort aktivitet"};
      private boolean changeName = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Aktiviteter");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try
        {
            createActivityFragmentListener = (CreateActivityFragmentListener)getActivity();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement CreateActivityFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activities_layout, container, false);
        getActivity().supportInvalidateOptionsMenu();
        activityList = Queries.getActivites();
//        sharedPre = new SharedPre();
//        context = getActivity();
////        sharedPre.clearAllone(context);
////        sharedPre.clearAlltwo(context);
//        sharedPre.loadListItemID(context);
//        Li_ID = sharedPre.tempLi_ID;
//        sharedPre.loadListItem(context);
//        activityList = sharedPre.tempList;
        optionsDialogFragment = new OptionsDialogFragment();

//        sharedPre.loadFromSharedPref(context);
//        activityList = sharedPre.tempList;
        activityAdapter = new ArrayAdapter<ListItem>(getActivity(), android.R.layout.simple_list_item_1, activityList);
        activityListView = (ListView)view.findViewById(R.id.listView);
        activityListView.setAdapter(activityAdapter);
        activityAdapter.notifyDataSetChanged();
        optionsDialog = new OptionsDialogFragment();
        optionsDialog.listener = this;

        activityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                createActivityFragmentListener.activeObject(activityList.get(position));
            }
        });
        activityListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ItemClicked = activityAdapter.getItem(position);
                optionsDialog.show(getFragmentManager(), "Options");
                return true;
            }
        });
        return view;
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
                String text = "Lägg till en ny aktivitet";
                Bundle bundle = new Bundle();
                bundle.putString("addDocTitle", text);
                defaultDialogFragment.setArguments(bundle);
                defaultDialogFragment.listener = this;
                defaultDialogFragment.show(getFragmentManager(), "dialog");

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Recieved name from userinput in dialog
    @Override
    public void enteredText(String text) {
        nameExists = false;
        checkName(text);
        if (text.toString().contentEquals("") || text.toString().contains("\n") || nameExists == true) {
            Toast.makeText(getActivity(), "Aktiviteten måste vara unik, ej innehålla mellanslag eller ny rad", Toast.LENGTH_LONG).show();

        } else {
            if (!changeName) {
                ListItem actToDb = new ListItem(text);
                actToDb.save();
                UpdateAndSave();
            }
            if (changeName) {
                ItemClicked.load(ListItem.class, ItemClicked.getId());
                ItemClicked.name = text;
                ItemClicked.save();

                Toast.makeText(getActivity(), Queries.getActivites().toString(), Toast.LENGTH_SHORT).show();
                UpdateAndSave();

            }
        }
    }

    @Override
    public void getChoice(int pos)
    {
        if(pos==0)
            {
                changeName = true;

                Bundle bundle = new Bundle();
                bundle.putString("addDocTitle", DIALOG_TITLE + ItemClicked.name);

                DefaultDialogFragment defaultDialogFragment = new DefaultDialogFragment();
                defaultDialogFragment.listener = this;
                defaultDialogFragment.setArguments(bundle);
                defaultDialogFragment.show(getFragmentManager(), "dialog");
            }
            if(pos==1)
            {
                ItemClicked.delete();
                UpdateAndSave();
            }
    }

    public interface CreateActivityFragmentListener {
        void activeObject(ListItem listItem);
}
    public void UpdateAndSave()
    {
        activityList = Queries.getActivites();
        activityAdapter.clear();
        activityAdapter.addAll(activityList);
    }


    public void  checkName(String text)
    {
        for(ListItem l : activityList)
        {
            if (l.name.equals(text))
            {
                nameExists = true;
            }
        }
    }

}

