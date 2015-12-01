package com.example.eandreje.androidapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
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
import java.util.List;

public class LeftsideDocumentFragment extends Fragment implements CreateDocumentFragment.CreateDocumentFragmentListener,
        CustomStringAdapter.CustomStringAdapterListener, DefaultDialogFragment.DefaultDialogFragmentListener,
        AddPersonDialogFragment.AddPersonDialogFragmentListener, CustomBoolAdapter.CustomBoolAdapterListener,
        OptionsDialogFragment.OptionsDialogFragmentListener {

    private static final String COLUMN_TITLE = "Ny kolumn";
    private static final String PERSON_TITLE = "Ny deltagare";
    private static final String TEXTVIEW_CHANGE = "Nytt värde";
    private static final String DOCLIST_TITLE = "Importera namn";
    private static final String DIALOG_IMPORT = "Välj ett av dokumenten för import av namn";
    private static final String DIALOG_ADD_PERSON = "Skriv in namn och önskat kolumnvärde om tillgängligt";
    private static final String DIALOG_ADD_COLUMN = "Skriv namn och om kolumntypen ska vara checkbox eller text";
    private static final String DIALOG_RENAME_COLUMN = "Nytt namn på kolumn";
    private static final String DELETE_TITLE = "Alternativ";
    private static final String DIALOG_DELETE = "Ta bort aktuell rad?";
    private static final String DIALOG_DELETE_COLUMN = "Ta bort aktuell kolumn och all tillhörande data?";

    private static final int ADD_PERSON_CODE = 3;

    private DocItem document;
    private String inDocTitle;
    private List<AdapterObjects> valueList;
    private CustomBoolAdapter boolAdapter;
    private CustomStringAdapter stringAdapter;
    private ArrayAdapter<Columns> spinnerAdapt;
    private List<Columns> spinnerColumns;
    private ListView listView;
    private long activeObject;
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
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("Document", getArguments().getParcelable("activeDocument"));
        outState.putLong("ActiveObject", activeObject);
        //outState.putParcelable("ActiveColumn", activeColumn);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){
            document = savedInstanceState.getParcelable("activeDocument");
            activeObject = savedInstanceState.getLong("ActiveObject");
        }

        //activeColumn = savedInstanceState.getParcelable("ActiveColumn");
        super.onViewStateRestored(savedInstanceState);
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
    valueList = new ArrayList<>();
    spinnerColumns = new ArrayList<>();
    valueList = new ArrayList<>();
    spinner = (Spinner) view.findViewById(R.id.spinner);
    spinnerAdapt = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerColumns);
    boolAdapter = new CustomBoolAdapter(getContext(), valueList, this);
    stringAdapter = new CustomStringAdapter(getContext(), valueList, this);
    spinner.setAdapter(spinnerAdapt);
    initAdapterObjectList(valueList, document);
    listView.setAdapter(stringAdapter);

    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (spinnerColumns.get(position).isCheckbox()) {
                activeColumn = (Columns) spinner.getItemAtPosition(position);
                updateColumnData(valueList);
                listView.setAdapter(boolAdapter);
            } else {
                activeColumn = (Columns) spinner.getItemAtPosition(position);
                updateColumnData(valueList);
                listView.setAdapter(stringAdapter);
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
        DefaultDialogFragment defaultDialogFragment;

        switch (item.getItemId())
        {
            case R.id.add_person_icon:
                AddPersonDialogFragment addPerson = new AddPersonDialogFragment();
                bundle = new Bundle();
                bundle.putParcelableArrayList("Columns", (ArrayList<? extends Parcelable>) spinnerColumns);
                bundle.putInt("Layout", R.layout.add_person_layout);
                bundle.putParcelable("DocParent", document);
                bundle.putString("dialogTitle", PERSON_TITLE);
                bundle.putString("DialogDesc", DIALOG_ADD_PERSON);
                bundle.putInt("Caller", R.id.add_person_icon);
                addPerson.setArguments(bundle);
                //addPerson.listener = this;
                addPerson.setTargetFragment(this, ADD_PERSON_CODE );
                addPerson.show(getFragmentManager(), "addPerson");
                break;
            case R.id.add_column_icon:
                defaultDialogFragment = new DefaultDialogFragment();
                bundle = new Bundle();
                bundle.putString("addDocTitle", COLUMN_TITLE);
                bundle.putInt("Layout", R.layout.add_column_layout);
                bundle.putInt("Caller", R.id.add_column_icon);
                bundle.putString("DialogDesc", DIALOG_ADD_COLUMN);
                defaultDialogFragment.setArguments(bundle);
                defaultDialogFragment.setTargetFragment(this, ADD_PERSON_CODE);
                //defaultDialogFragment.listener = this;
                defaultDialogFragment.show(getFragmentManager(), "newColumnDialog");
                break;
            case R.id.rename_column:
                if(spinnerColumns.size() != 0){
                    defaultDialogFragment = new DefaultDialogFragment();
                    bundle = new Bundle();
                    bundle.putString("addDocTitle", COLUMN_TITLE);
                    bundle.putInt("Layout", R.layout.default_dialog);
                    bundle.putInt("Caller", R.id.rename_column);
                    bundle.putString("DialogDesc", DIALOG_RENAME_COLUMN);
                    defaultDialogFragment.setArguments(bundle);
                    defaultDialogFragment.setTargetFragment(this, ADD_PERSON_CODE);
                    //defaultDialogFragment.listener = this;
                    defaultDialogFragment.show(getFragmentManager(), "newColumnDialog");
                }
                else
                    Toast.makeText(getContext(), "Listan med kolumner är tom", Toast.LENGTH_SHORT).show();
                break;
            case R.id.import_persons:
                OptionsDialogFragment chooseDoc = new OptionsDialogFragment();
                bundle = new Bundle();
                bundle.putString("DialogTitle", DOCLIST_TITLE);
                bundle.putString("DialogDesc", DIALOG_IMPORT);
                bundle.putInt("Layout", R.layout.act_options_layout);
                bundle.putInt("Caller", R.id.import_persons);
                bundle.putParcelable("ParentAct", document.getParentActivity());
                bundle.putParcelable("ActiveDoc", document);
                chooseDoc.setArguments(bundle);
                chooseDoc.setTargetFragment(this, ADD_PERSON_CODE );
                //chooseDoc.listener = this;
                chooseDoc.show(getFragmentManager(), "DocListChoose");
                break;
            case R.id.import_persons_columns:
                OptionsDialogFragment doc = new OptionsDialogFragment();
                bundle = new Bundle();
                bundle.putString("DialogTitle", DOCLIST_TITLE);
                bundle.putString("DialogDesc", DIALOG_IMPORT);
                bundle.putInt("Layout", R.layout.act_options_layout);
                bundle.putInt("Caller", R.id.import_persons_columns);
                bundle.putParcelable("ParentAct", document.getParentActivity());
                bundle.putParcelable("ActiveDoc", document);
                doc.setArguments(bundle);
                doc.setTargetFragment(this, ADD_PERSON_CODE );
                //doc.listener = this;
                doc.show(getFragmentManager(), "DocListChoose");
                break;
            case R.id.delete_column:
                if(spinnerColumns.size() != 0){
                    defaultDialogFragment = new DefaultDialogFragment();
                    bundle = new Bundle();
                    bundle.putString("addDocTitle", COLUMN_TITLE);
                    bundle.putInt("Layout", R.layout.delete_dialog);
                    bundle.putInt("Caller", R.id.delete_column);
                    bundle.putString("DialogDesc", DIALOG_DELETE_COLUMN);
                    defaultDialogFragment.setArguments(bundle);
                    defaultDialogFragment.setTargetFragment(this, ADD_PERSON_CODE);
                    //defaultDialogFragment.listener = this;
                    defaultDialogFragment.show(getFragmentManager(), "DeleteDialog");
                }
                else{
                    Toast.makeText(getContext(), "Listan med kolumner är tom", Toast.LENGTH_SHORT).show();
                    break;
                }
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
            if(text.equals("")){
                Toast.makeText(getContext(), "Ett namnfält får ej vara tomt", Toast.LENGTH_SHORT).show();
            }
            break;
        case R.id.rename_column:
            if(!text.equals("")){
                activeColumn.setHeader(text);
                activeColumn.save();
                spinnerAdapt.notifyDataSetChanged();
            }
            else
                Toast.makeText(getContext(), "Ett namnfält får ej vara tomt", Toast.LENGTH_SHORT).show();
            break;
        case R.id.person_name:
            if(!text.equals("")){
                persDocItem = Queries.getPersDocRelation(activeObject, document);
                person = persDocItem.getPerson();
                //cell = Queries.fetchSingleCellData(person, activeColumn, document);
                //cell.parentPerson.setName(text);
                //cell.save();
                persDocItem = Queries.getPersDocRelation(activeObject, document);
                persDocItem.getPerson().setName(text);
                persDocItem.getPerson().save();
                updateListview();
            }
            else
                Toast.makeText(getContext(), "Ett namnfält får ej vara tomt", Toast.LENGTH_SHORT).show();
            break;
        case R.id.column_name:
            if(!text.equals("")){
                persDocItem = Queries.getPersDocRelation(activeObject, document);
                person = persDocItem.getPerson();
                cell = Queries.fetchSingleCellData(person, activeColumn, document);
                cell.setValue(text);
                cell.save();
                updateListview();
            }
            else
                Toast.makeText(getContext(), "Ett namnfält får ej vara tomt", Toast.LENGTH_SHORT).show();
            break;
        default:
        }
    }

    @Override
    public void enteredTextBool(String text, int caller, boolean checked) {
        if(!text.equals("")){
        Columns column = new Columns(text, document, checked);
        column.save();
        List<PersonDocItem> persons = Queries.getPersonCell(document);
        for (PersonDocItem pDoc : persons) {
            Person person = pDoc.getPerson();
            ColumnContent newColumn = new ColumnContent("", document, column, person);
            newColumn.save();
        }
        spinnerColumns = Queries.getColumnHeaders(document);
        spinnerAdapt.clear();
        spinnerAdapt.addAll(spinnerColumns);
        }
        else{
            Toast.makeText(getContext(), "Ett namnfält får ej vara tomt", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteRequest(int caller) {
        //Queries.deleteColumnValues(activeColumn);
        //Queries.deleteColumn(activeColumn);
        activeColumn.delete();
        spinnerColumns = Queries.getColumnHeaders(document);
        spinnerAdapt.clear();
        spinnerAdapt.addAll(spinnerColumns);
        updateColumnData(valueList);
        updateListview();
    }

    @Override
    public void newPersonAdded(DocItem doc) {
        updateListview();
    }

    @Override
    public void buttonPressed(View v, Long position) {
        activeObject = position;
        DefaultDialogFragment dialog = new DefaultDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Layout", R.layout.default_dialog);
        if(v.getId() == R.id.person_name)
            bundle.putInt("Caller", R.id.person_name);
        else
            bundle.putInt("Caller", R.id.column_name);
        bundle.putString("DialogDesc", DIALOG_ADD_PERSON);
        bundle.putString("addDocTitle", TEXTVIEW_CHANGE);
        dialog.setArguments(bundle);
        dialog.setTargetFragment(this, ADD_PERSON_CODE);
        //dialog.listener = this;
        dialog.show(getFragmentManager(), "changeCustomAdaptAttr");
    }

    @Override
    public void getChoice(int pos) {
        switch (pos){
            default:
//                Queries.deleteInColumnContent(activeObject);
//                Queries.deleteInDocItemClass(activeObject);
//                Queries.deleteInPersonClass(activeObject);
                //activeColumn.delete();
                Queries.getPersDocRelation(activeObject, document).getPerson().delete();
                updateListview();


                break;
        }
    }

    @Override
    public void importPers(DocItem doc) {
        List<PersonDocItem> importList = Queries.getAllDocPersons(doc);
        if(spinnerColumns != null && stringAdapter != null && boolAdapter != null)
        {
            for (PersonDocItem p : importList)
            {
//                Person person = p.getPerson();
//                person.save();
                //new Person(p.getPerson().toString(), document.getParentActivity());
                PersonDocItem perDocRelation = new PersonDocItem(p.getPerson(), document);
                perDocRelation.save();
                for (Columns col:spinnerColumns) {
                    ColumnContent newColumn = new ColumnContent("", document, col, p.getPerson());
                    newColumn.save();
                }
            }
        }
        else
            Toast.makeText(getActivity(), "Listan är inte tom", Toast.LENGTH_SHORT).show();
        updateListview();
    }

    @Override
    public void importPersCol(DocItem doc) {
        List<Columns> importedCol = Queries.getColumnHeaders(doc);
        activeColumn = importedCol.get(0);
        for (Columns column:importedCol) {
            Columns newCol = new Columns(column.toString(), document, column.isCheckbox());
            newCol.save();
        }
        spinnerColumns = Queries.getColumnHeaders(document);
        spinnerAdapt.clear();
        spinnerAdapt.addAll(spinnerColumns);
        importPers(doc);
        updateColumnData(valueList);
        initListview();
        updateListview();
    }

    @Override
    public void deletePersonBool(View v, Long position) {
        activeObject = position;
        OptionsDialogFragment chooseDoc = new OptionsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DialogTitle", DELETE_TITLE);
        bundle.putString("DialogDesc", DIALOG_DELETE);
        bundle.putInt("Layout", R.layout.act_options_layout);
        bundle.putInt("Caller", R.id.listView);
        chooseDoc.setArguments(bundle);
        chooseDoc.setTargetFragment(this, ADD_PERSON_CODE);
        //chooseDoc.listener = this;
        chooseDoc.show(getFragmentManager(), "DocListChoose");
    }

    @Override
    public void deletePersonString(View v, Long position) {
        activeObject = position;
        OptionsDialogFragment chooseDoc = new OptionsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DialogTitle", DELETE_TITLE);
        bundle.putString("DialogDesc", DIALOG_DELETE);
        bundle.putInt("Layout", R.layout.act_options_layout);
        bundle.putInt("Caller", R.id.listView);
        chooseDoc.setArguments(bundle);
        chooseDoc.setTargetFragment(this, ADD_PERSON_CODE);
        //chooseDoc.listener = this;
        chooseDoc.show(getFragmentManager(), "DocListChoose");
    }

    @Override
    public void checkboxChange(View v, Long position, boolean bool) {
        activeObject = position;
        PersonDocItem persDocItem = Queries.getPersDocRelation(activeObject, document);
        Person person = persDocItem.getPerson();
        ColumnContent cell = Queries.fetchSingleCellData(person, activeColumn, document);
        cell.setValue(String.valueOf(bool));
        cell.save();
        initAdapterObjectList(valueList, document);
    }

    @Override
    public void textboxClick(View v, Long position) {
        activeObject = position;
        DefaultDialogFragment dialog = new DefaultDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Layout", R.layout.default_dialog);
        bundle.putInt("Caller", R.id.person_name);
        bundle.putString("addDocTitle", TEXTVIEW_CHANGE);
        bundle.putString("DialogDesc", DIALOG_IMPORT);
        dialog.setArguments(bundle);
        dialog.setTargetFragment(this, ADD_PERSON_CODE);
        //dialog.listener = this;
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
        initAdapterObjectList(valueList, document);
        boolAdapter.notifyDataSetChanged();
        stringAdapter.notifyDataSetChanged();
    }

    public void initAdapterObjectList(List<AdapterObjects> adapterObjects, DocItem document) {
    adapterObjects.clear();
    List<PersonDocItem> persons = Queries.getPersonCell(document);
    for (PersonDocItem pDI:persons)
    {
        AdapterObjects newObject = new AdapterObjects();
        newObject.person = pDI.getPerson();
        if(activeColumn != null)
            newObject.columnContent = newObject.person.getColumnContent(document, activeColumn);
        adapterObjects.add(newObject);
        }
    }

    public void updateColumnData(List<AdapterObjects> adapterObjects) {
    for (AdapterObjects aO:adapterObjects)
    {
        aO.columnContent = Queries.fetchSingleCellData(aO.person, activeColumn, document);
    }
    }
}

