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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class LeftsideDocumentFragment extends Fragment implements CreateDocumentFragment.CreateDocumentFragmentListener,
        CustomStringAdapter.CustomStringAdapterListener, DefaultDialogFragment.DefaultDialogFragmentListener,
        AddPersonDialogFragment.AddPersonDialogFragmentListener, CustomBoolAdapter.CustomBoolAdapterListener,
        OptionsDialogFragment.OptionsDialogFragmentListener
    {


    private static final String COLUMN_TITLE = "Ny kolumn";
    private static final String PERSON_TITLE = "Ny deltagare";
    private static final String TEXTVIEW_CHANGE = "Ändra text";
    private static final String DOCLIST_TITLE = "Välj ett dokument för import";

    private DocItem document;
    private String inDocTitle;
    private ArrayList<ColumnContent> valueList;
    private CustomBoolAdapter boolAdapter;
    private CustomStringAdapter stringAdapter;
    private ArrayAdapter<Columns> spinnerAdapt;
    private ArrayList<Columns> spinnerColumns;
    private ListView listView;
    private long listPos;
    private Columns activeColumn;
    private Spinner spinner;

    public static LeftsideDocumentFragment newInstance(DocItem docItem) {
        LeftsideDocumentFragment leftsideDocumentFragment = new LeftsideDocumentFragment();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("activeDocument", docItem);
        leftsideDocumentFragment.setArguments(bundle);
        return leftsideDocumentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        document = getArguments().getParcelable("activeDocument");
        inDocTitle = document.toString();

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(inDocTitle);
        document = getArguments().getParcelable("activeDocument");
        initListview();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leftside_layout, container, false);
        listView = (ListView) view.findViewById(R.id.listView4);

        spinnerColumns = new ArrayList<>();
        valueList = new ArrayList<>();
        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinnerAdapt = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerColumns);
        boolAdapter = new CustomBoolAdapter(getContext(), valueList, this);
        stringAdapter = new CustomStringAdapter(getContext(), valueList, this);
        spinner.setAdapter(spinnerAdapt);
        initListview();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerColumns.get(position).isCheckbox()) {
                    activeColumn = (Columns) spinner.getItemAtPosition(position);
                    listView.setAdapter(boolAdapter);
                    valueList = Queries.fetchColumnCellData(spinnerColumns.get(position), document);
                    updateListview();
                } else {
                    activeColumn = (Columns) spinner.getItemAtPosition(position);
                    listView.setAdapter(stringAdapter);
                    valueList = Queries.fetchColumnCellData(spinnerColumns.get(position), document);
                    updateListview();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.in_document_actionbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle;

        switch (item.getItemId())
        {
            case R.id.add_person_icon:
                if(spinnerColumns.size() == 0)
                {
                    Toast.makeText(getActivity(), "Lägg till en kolumn först", Toast.LENGTH_SHORT).show();
                    break;
                }
                AddPersonDialogFragment addPerson = new AddPersonDialogFragment();
                bundle = new Bundle();
                bundle.putParcelableArrayList("Columns", spinnerColumns);
                bundle.putInt("Layout", R.layout.add_person_layout);
                bundle.putParcelable("DocParent", document);
                bundle.putString("dialogTitle", PERSON_TITLE);
                bundle.putInt("Caller", R.id.add_person_icon);
                addPerson.setArguments(bundle);
                addPerson.listener = this;
                addPerson.show(getFragmentManager(), "addPerson");
                break;
            case R.id.add_column_icon:
                DefaultDialogFragment defaultDialogFragment = new DefaultDialogFragment();
                bundle = new Bundle();
                bundle.putString("addDocTitle", COLUMN_TITLE);
                bundle.putInt("Layout", R.layout.add_column_layout);
                bundle.putInt("Caller", R.id.add_column_icon);
                defaultDialogFragment.setArguments(bundle);
                defaultDialogFragment.listener = this;
                defaultDialogFragment.show(getFragmentManager(), "newColumnDialog");
                break;
            case R.id.import_persons:
                OptionsDialogFragment chooseDoc = new OptionsDialogFragment();
                bundle = new Bundle();
                bundle.putString("DialogTitle", DOCLIST_TITLE);
                bundle.putInt("Layout", R.layout.act_options_layout);
                bundle.putInt("Caller", R.id.import_persons);
                bundle.putParcelable("ParentAct", document.getParentActivity());
                chooseDoc.setArguments(bundle);
                chooseDoc.listener = this;
                chooseDoc.show(getFragmentManager(), "DocListChoose");
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void docObjectClicked(DocItem doc) {
    }

    @Override
    public void enteredText(String text, int id) {
        Person person;
        PersonDocItem persDocItem;
        ColumnContent cell;

        switch (id)
        {
            case R.id.add_column_icon:
                break;
            case R.id.add_person_icon:
                break;
            case R.id.checkbox_value:
                break;
            case R.id.person_name:
                persDocItem = Queries.getPersDocRelation((int) listPos, document);
                person = persDocItem.getPerson();
                cell = Queries.fetchSingleCellData(person, activeColumn, document);
                cell.parentPerson.setName(text);
                cell.save();
                updateListview();
                break;
            case R.id.column_name:
                persDocItem = Queries.getPersDocRelation((int) listPos, document);
                person = persDocItem.getPerson();
                cell = Queries.fetchSingleCellData(person, activeColumn, document);
                cell.setValue(text);
                cell.save();
                updateListview();
                break;
            default:
        }

    }

    @Override
    public void enteredTextBool(String text, int caller, boolean checked) {
        Columns column = new Columns(text, document, checked);
        column.save();
        ArrayList<PersonDocItem> persons = Queries.getPersonCell(document);
        for (PersonDocItem pDoc : persons) {
            Person person = pDoc.getPerson();
            ColumnContent newColumn = new ColumnContent("", document, column, person);
            newColumn.save();
        }
        spinnerColumns = Queries.getColumnHeaders(document);
        spinnerAdapt.clear();
        spinnerAdapt.addAll(spinnerColumns);
    }

    @Override
    public void newPersonAdded(DocItem doc) {
        updateListview();
    }

    @Override
    public void buttonPressed(View v, Long position) {
        listPos = position;
        DefaultDialogFragment dialog = new DefaultDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Layout", R.layout.default_dialog);

        if(v.getId() == R.id.person_name)
            bundle.putInt("Caller", R.id.person_name);
        else
            bundle.putInt("Caller", R.id.column_name);

        bundle.putString("addDocTitle", TEXTVIEW_CHANGE);
        dialog.setArguments(bundle);
        dialog.listener = this;
        dialog.show(getFragmentManager(), "changeCustomAdaptAttr");
    }

    @Override
    public void checkboxChange(View v, Long position, boolean bool) {
        listPos = position;
        PersonDocItem persDocItem = Queries.getPersDocRelation((int) listPos, document);
        Person person = persDocItem.getPerson();
        ColumnContent cell = Queries.fetchSingleCellData(person, activeColumn, document);
        cell.setValue(String.valueOf(bool));
        cell.save();
        updateListview();
    }

    @Override
    public void textboxClick(View v, Long position) {
        listPos = position;
        DefaultDialogFragment dialog = new DefaultDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Layout", R.layout.default_dialog);
        bundle.putInt("Caller", R.id.person_name);
        bundle.putString("addDocTitle", TEXTVIEW_CHANGE);
        dialog.setArguments(bundle);
        dialog.listener = this;
        dialog.show(getFragmentManager(), "changeCustomAdaptAttr");
    }

    private void initListview(){
        if(spinnerColumns.size() != 0){
            if(spinnerColumns.get(0).isCheckbox()){
                listView.setAdapter(boolAdapter);
                boolAdapter.notifyDataSetChanged();
            }
            else
            {
                listView.setAdapter(stringAdapter);
                stringAdapter.notifyDataSetChanged();
            }
        }
        else
        {
            spinnerColumns = Queries.getColumnHeaders(document);
            spinnerAdapt.clear();
            spinnerAdapt.addAll(spinnerColumns);
        }
    }
    private void updateListview(){
        if(activeColumn != null)
            valueList = Queries.fetchColumnCellData(activeColumn, document);
        boolAdapter.clear();
        boolAdapter.addAll(valueList);
        stringAdapter.clear();
        stringAdapter.addAll(valueList);
    }

        @Override
        public void getChoice(int pos) {

        }

        @Override
        public void getDocChoice(DocItem doc) {
             ArrayList<PersonDocItem> importList = Queries.getAllActPersons(doc);
                if(spinnerColumns != null && stringAdapter != null && boolAdapter != null)
                {
                    for (PersonDocItem p : importList)
                    {
                        Person person = new Person(p.getPerson().toString(), document.getParentActivity());
                        person.save();
                        PersonDocItem perDocRelation = new PersonDocItem(person, document);
                        perDocRelation.save();
                            ArrayList<PersonDocItem> persons = Queries.getPersonCell(document);
                            for (PersonDocItem pDoc : persons)
                            {
                                ColumnContent newColumn = new ColumnContent("", document, activeColumn, person);
                                newColumn.save();
                            }
                    }


                        /*if(spinnerColumns.size() != 0){
                            for (PersonDocItem pDoc : persons)
                            {
                                //Person aPerson = pDoc.getPerson();
                                ColumnContent newColumn = new ColumnContent("", document, activeColumn, person);
                                newColumn.save();
                            }
                        }*/
                    }

                else
                    Toast.makeText(getActivity(), "Listan är inte tom", Toast.LENGTH_SHORT).show();
                updateListview();
        }
    }
