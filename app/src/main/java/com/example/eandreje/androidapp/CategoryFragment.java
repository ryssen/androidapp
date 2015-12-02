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

public class CategoryFragment extends Fragment implements InputDialogFragment.DefaultDialogFragmentListener,
        OptionsDialogFragment.OptionsDialogFragmentListener{

    private static final String DIALOG_TITLE = "Ny kategori";
    private static final String CATLIST_TITLE = "Alternativ";
    private static final String DIALOG_CHANGE_CAT_NAME = "Skriv in ett nytt namn";
    private static final String DIALOG_NEW_CATEGORY = "Skriv in namnet på den nya kategorin";
    private static final String DIALOG__ALTERNATIVE = "Ändra namn eller ta bort aktuell kategori";

    private static final int ACTIVITY_CODE = 2;

    private ArrayAdapter<Category> categoryArrayAdapter;
    private List<Category> categoryList = new ArrayList<>();
    public CategoryFragmentListener categoryFragmentListener;
    private OptionsDialogFragment optionsDialog;
    private Category itemClicked;
    private boolean changeName = false;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("ActiveEvent", itemClicked);
        //outState.putParcelable("ActiveColumn", activeColumn);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){
            itemClicked = savedInstanceState.getParcelable("ActiveEvent");
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Kategorier");
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try
        {
            categoryFragmentListener = (CategoryFragmentListener)getActivity();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement CategoryFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activities_layout, container, false);
        getActivity().supportInvalidateOptionsMenu();
        optionsDialog = new OptionsDialogFragment();
        optionsDialog.setTargetFragment(this, ACTIVITY_CODE);
        //optionsDialog.listener = this;
        categoryList = Queries.getCategories();
        categoryArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.row_layout, categoryList);
        ListView activityListView = (ListView) view.findViewById(R.id.listView);
        activityListView.setAdapter(categoryArrayAdapter);
        activityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryFragmentListener.activeObject(categoryList.get(position));
            }
        });
        activityListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemClicked = categoryArrayAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("DialogTitle", CATLIST_TITLE);
                bundle.putInt("Layout", R.layout.act_options_layout);
                bundle.putString("DialogDesc", DIALOG__ALTERNATIVE);
                bundle.putInt("Caller", 0);
                optionsDialog.setArguments(bundle);
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
                InputDialogFragment inputDialogFragment = new InputDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("addDocTitle", DIALOG_TITLE);
                bundle.putInt("Layout", R.layout.default_dialog);
                bundle.putString("DialogDesc", DIALOG_NEW_CATEGORY);
                bundle.putInt("Caller", R.id.add_activity_icon);
                inputDialogFragment.setArguments(bundle);
                //inputDialogFragment.listener = this;
                inputDialogFragment.setTargetFragment(this, ACTIVITY_CODE);
                inputDialogFragment.show(getFragmentManager(), "dialog");
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
                Category actToDb = new Category(text);
                actToDb.save();
                UpdateAndSave();
            }
            if(changeName)
            {
                itemClicked.load(Category.class, itemClicked.getId());
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
    public void onDeleteRequest(int caller) {
    }

    @Override
    public void getChoice(int pos)
    {
        switch (pos){
            case 0:
                changeName = true;
                Bundle bundle = new Bundle();
                bundle.putString("addDocTitle", DIALOG_TITLE);
                InputDialogFragment inputDialogFragment = new InputDialogFragment();
                bundle.putInt("Layout", R.layout.default_dialog);
                bundle.putString("DialogDesc", DIALOG_CHANGE_CAT_NAME);
                //inputDialogFragment.listener = this;
                inputDialogFragment.setTargetFragment(this, ACTIVITY_CODE);
                inputDialogFragment.setArguments(bundle);
                inputDialogFragment.show(getFragmentManager(), "dialog");
                break;
            case 1:
                itemClicked.delete();
                UpdateAndSave();
                break;
            }
    }

    @Override
    public void importPers(Event doc) {
    }

    @Override
    public void importPersCol(Event doc) {

    }

    public interface CategoryFragmentListener {
        void activeObject(Category category);
    }

    public void UpdateAndSave() {
        categoryList = Queries.getCategories();
        categoryArrayAdapter.clear();
        categoryArrayAdapter.addAll(categoryList);
    }
}

