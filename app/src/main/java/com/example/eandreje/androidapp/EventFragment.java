package com.example.eandreje.androidapp;

import android.app.Activity;
import android.content.Intent;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment implements InputDialogFragment.InputDialogFragmentListener,
        OptionsDialogFragment.OptionsDialogFragmentListener{

    private static final String DIALOG_TITLE = "Nytt event";
    private static final String DOCLIST_TITLE = "Alternativ";
    private static final String DIALOG_CHANGE_EVENT_NAME = "Skriv in ett nytt namn";
    private static final String DIALOG_NEW_EVENT = "Skriv in namnet på nytt event";
    private static final String DIALOG_ALTERNATIVE = "Ändra namn eller ta bort aktuellt event";
    private static final String DIALOG_EXPORT_TITLE = "Exportera event";
    private static final String DIALOG_EXPORT_DISC = "Välj ett event att exportera";

    private static final int DOCUMENT_CODE = 2;

    private boolean state = false;
    private ArrayAdapter<Event> adapter;
    private List<Event> eventList;
    private Category category;
    private Event eventClicked;
    public EventFragmentListener eventFragmentListener;
    private OptionsDialogFragment optionsDialogFragment;
    private CreateCSV createCsv;

    //newInstance factoring method, returns a new instance of this class
    // with custom parameter
    public static EventFragment newInstance(Category category){
        EventFragment fragment = new EventFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("listobject", category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("Event", getArguments().getParcelable("listobject"));
        outState.putParcelable("ActiveDoc", eventClicked);
        outState.putBoolean("State", state);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){
            category = savedInstanceState.getParcelable("Event");
            eventClicked = savedInstanceState.getParcelable("ActiveDoc");
            state = savedInstanceState.getBoolean("State");
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        category = getArguments().getParcelable("listobject");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(category.name);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.document_layout, container, false);
        eventList = new ArrayList<>();
        eventList = Queries.getEvents(category);
        createCsv = new CreateCSV();
        optionsDialogFragment = new OptionsDialogFragment();
        optionsDialogFragment.setTargetFragment(this, DOCUMENT_CODE);
        ListView listView = (ListView) view.findViewById(R.id.document_listview);
        adapter = new ArrayAdapter<>(getActivity(), R.layout.row_layout, eventList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eventClicked = adapter.getItem(position);
                eventFragmentListener.eventObjectClicked(eventList.get(position));
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                eventClicked = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("DialogTitle", DOCLIST_TITLE);
                bundle.putString("DialogDesc", DIALOG_ALTERNATIVE);
                bundle.putInt("Layout", R.layout.act_options_layout);
                bundle.putInt("Caller", 0);
                optionsDialogFragment.setArguments(bundle);
                optionsDialogFragment.show(getFragmentManager(), "docOptions");
                return true;
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            eventFragmentListener = (EventFragmentListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement CategoryFragmentListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.event_actionbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle;
        switch (item.getItemId()) {
            case R.id.add_doc_icon:
                InputDialogFragment inputDialogFragment = new InputDialogFragment();
                bundle = new Bundle();
                bundle.putString("addDocTitle", DIALOG_TITLE);
                bundle.putString("DialogDesc", DIALOG_NEW_EVENT);
                bundle.putInt("Layout", R.layout.default_dialog);
                bundle.putInt("Caller", R.id.add_doc_icon);
                inputDialogFragment.setArguments(bundle);
                inputDialogFragment.setTargetFragment(this, DOCUMENT_CODE);
                inputDialogFragment.show(getFragmentManager(), "NewDocDialog");
                break;
            case R.id.second_view_up_cloud:
                OptionsDialogFragment choosedocExport = new OptionsDialogFragment();
                bundle = new Bundle();
                bundle.putString("DialogTitle", DIALOG_EXPORT_TITLE);
                bundle.putString("DialogDesc", DIALOG_EXPORT_DISC);
                bundle.putInt("Layout", R.layout.act_options_layout);
                bundle.putInt("Caller", R.id.second_view_up_cloud);
                bundle.putParcelable("ParentAct_export", category);
                choosedocExport.setArguments(bundle);
                choosedocExport.setTargetFragment(this, DOCUMENT_CODE);
                choosedocExport.show(getFragmentManager(), "ExportDoc");
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void enteredText(String text, int id) {
        if(text.contentEquals("") || text.contains("\n"))
        {
            Toast.makeText(getActivity(), "Namnet får ej innehålla mellanslag eller ny rad", Toast.LENGTH_LONG).show();
        }
        else
        if(!state)
        {
            addDocument(text);
        }
        else
        {
            eventClicked.load(Event.class, eventClicked.getId());
            eventClicked.name = text;
            eventClicked.save();
            UpdateAndSave();
            state = false;
        }
    }

    @Override
    public void enteredTextBool(String text, int caller, boolean checked) {

    }

    @Override
    public void onDeleteRequest(int caller) {

    }

    public void UpdateAndSave() {
        eventList = Queries.getEvents(category);
        adapter.clear();
        adapter.addAll(eventList);
    }

    @Override
    public void getChoice(int pos) {
        switch (pos) {
            case 0:
                state = true;
                InputDialogFragment docDialog = new InputDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("addDocTitle", DIALOG_TITLE);
                bundle.putString("DialogDesc", DIALOG_CHANGE_EVENT_NAME);
                bundle.putInt("Layout", R.layout.default_dialog);
                docDialog.setArguments(bundle);
                docDialog.setTargetFragment(this, DOCUMENT_CODE);
                //docDialog.listener = this;
                docDialog.show(getFragmentManager(), "editDocDialog");
                break;
            case 1:
                List<Attendant> list = Queries.getRelation(eventClicked);
                for (Attendant p:list) {
                    p.delete();
                }
                eventClicked.delete();
                UpdateAndSave();
                break;
            default:
        }
    }

    public void addDocument(String text) {
        Event document = new Event(text);
        document.parentCategory = category;
        document.save();
        UpdateAndSave();
    }

    @Override
    public void importAttendants(Event doc)
    {
        exportFile(doc);
    }

    @Override
    public void importPersCol(Event doc) {

    }

    public interface EventFragmentListener {
        void eventObjectClicked(Event event);
    }

    public void exportFile(Event doc) {

        String exportCSV = createCsv.writeToCSV(doc);

        if (checkStringSize(exportCSV) == true) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            String title = "Dela " + doc.name + " via";
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, exportCSV);

            if (shareIntent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivity(Intent.createChooser(shareIntent, title));
            } else {
                Toast.makeText(getContext(), "Det finns ingen installarad applikation som kan ta emot innehållet", Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Ladda ner t.ex. Dropbox, Google Drive eller OneDrive för att kunna exportera", Toast.LENGTH_LONG).show();
            }
        } else
            Toast.makeText(getContext(), "Filen är för stor och kan inte exporteras", Toast.LENGTH_SHORT).show();
    }

    public boolean checkStringSize(String csv) {
        int size = 0;
        boolean sizeOK = true;
        try {
            final byte[] utf8bytes = csv.getBytes("UTF-8");
            size = utf8bytes.length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (size > 800000) {
            Toast.makeText(getContext(), "Filstorleken är 0,8MB av max 1 MB", Toast.LENGTH_SHORT).show();
        } else if (size > 900000) {
            sizeOK = false;
        }
        return sizeOK;
    }
}

//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data == null)
//            return;
//        String filepath = data.getData().getPath();
//        File file = new File(filepath);
//        String filename = file.getName();
//        filename = filename.replace(".txt", "");
//        addDocument(filename);
//
//
//        Toast.makeText(getActivity(), filename+".txt imported", Toast.LENGTH_SHORT).show();




