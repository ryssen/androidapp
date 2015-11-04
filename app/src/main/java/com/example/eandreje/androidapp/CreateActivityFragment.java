package com.example.eandreje.androidapp;

import android.app.Activity;
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

    private static final String DIALOG_TITLE = "Lägg till en ny aktivitet";

    private ArrayAdapter<ListItem> activityAdapter;
    private List<ListItem> activityList = new ArrayList<>();
    public CreateActivityFragmentListener createActivityFragmentListener;
    private OptionsDialogFragment optionsDialog;
    private ListItem itemClicked;
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
    public void onAttach(Activity context) {
        super.onAttach(context);
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
        optionsDialog = new OptionsDialogFragment();
        optionsDialog.listener = this;
        activityList = Queries.getActivites();
        activityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, activityList);
        ListView activityListView = (ListView) view.findViewById(R.id.listView);
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
                itemClicked = activityAdapter.getItem(position);
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
                Bundle bundle = new Bundle();
                bundle.putString("addDocTitle", DIALOG_TITLE);
                bundle.putInt("Layout", R.layout.default_dialog);
                bundle.putInt("Caller", R.id.add_activity_icon);
                defaultDialogFragment.setArguments(bundle);
                defaultDialogFragment.listener = this;
                defaultDialogFragment.show(getFragmentManager(), "dialog");
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    //Recieved name from userinput in dialog
    @Override
    public void enteredText(String text, int id) {
        boolean nameExists = false;
        if(text.contentEquals("") || text.contains("\n") || nameExists)
        {
            Toast.makeText(getActivity(), "Namnet måste vara unikt, ej innehålla mellanslag eller flera rader", Toast.LENGTH_LONG).show();
        }
        else
        {
            if(!changeName)
            {
                ListItem actToDb = new ListItem(text);
                actToDb.save();
                UpdateAndSave();
            }
            if(changeName)
            {
                itemClicked.load(ListItem.class, itemClicked.getId());
                itemClicked.name = text;
                itemClicked.save();
                UpdateAndSave();
                changeName = false;
            }
        }
    }

    @Override
    public void enteredTextBool(String text, int caller, boolean checked) {

    }

    @Override
    public void getChoice(int pos)
    {
            if(pos==0)
            {
                changeName = true;
                Bundle bundle = new Bundle();
                bundle.putString("addDocTitle", DIALOG_TITLE);
                DefaultDialogFragment defaultDialogFragment = new DefaultDialogFragment();
                bundle.putInt("Layout", R.layout.default_dialog);
                defaultDialogFragment.listener = this;
                defaultDialogFragment.setArguments(bundle);
                defaultDialogFragment.show(getFragmentManager(), "dialog");
            }
            if(pos==1)
            {
                itemClicked.delete();
                UpdateAndSave();
            }
    }

    @Override
    public void getDocChoice(DocItem doc) {

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
}

