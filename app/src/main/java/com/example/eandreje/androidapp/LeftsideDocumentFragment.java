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
    private static final String DIALOG_DELETE = "Ta bort aktuell rad";

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
    initAdapterObjectList(valueList);
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
                addPerson.listener = this;
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
                defaultDialogFragment.listener = this;
                defaultDialogFragment.show(getFragmentManager(), "newColumnDialog");
                break;
            case R.id.rename_column:
                if(activeColumn != null){
                    defaultDialogFragment = new DefaultDialogFragment();
                    bundle = new Bundle();
                    bundle.putString("addDocTitle", COLUMN_TITLE);
                    bundle.putInt("Layout", R.layout.default_dialog);
                    bundle.putInt("Caller", R.id.rename_column);
                    bundle.putString("DialogDesc", DIALOG_RENAME_COLUMN);
                    defaultDialogFragment.setArguments(bundle);
                    defaultDialogFragment.listener = this;
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
                chooseDoc.setArguments(bundle);
                chooseDoc.listener = this;
                chooseDoc.show(getFragmentManager(), "DocListChoose");
                break;
            case R.id.delete_column:
                if(activeColumn != null){
                    Queries.deleteColumnValues(activeColumn);
                    Queries.deleteColumn(activeColumn);
                    updateColumnData(valueList);
                    updateListview();
                }
                else
                    Toast.makeText(getContext(), "Listan med kolumner är tom", Toast.LENGTH_SHORT).show();
                spinnerColumns = Queries.getColumnHeaders(document);
                spinnerAdapt.clear();
                spinnerAdapt.addAll(spinnerColumns);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void docObjectClicked(DocItem doc) {
    }

//    @Override
//    public void launchGoogleDrive() {
//
//    }

    @Override
    public void enteredText(String text, int id) {
    Person person;
    PersonDocItem persDocItem;
    ColumnContent cell;

    switch (id)
    {
        case R.id.add_column_icon:
            if(text.equals("")){
                Toast.makeText(getContext(), "Ett namnfält får ej vara tomt", Toast.LENGTH_SHORT).show();
            }
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
                persDocItem = Queries.getPersDocRelation((int) activeObject, document);
                person = persDocItem.getPerson();
                cell = Queries.fetchSingleCellData(person, activeColumn, document);
                cell.parentPerson.setName(text);
                cell.save();
                updateListview();
            }
            else
                Toast.makeText(getContext(), "Ett namnfält får ej vara tomt", Toast.LENGTH_SHORT).show();
            break;
        case R.id.column_name:
            if(!text.equals("")){
                persDocItem = Queries.getPersDocRelation((int) activeObject, document);
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
        dialog.listener = this;
        dialog.show(getFragmentManager(), "changeCustomAdaptAttr");
    }

    @Override
    public void getChoice(int pos) {
        switch (pos){
            case 0:
                Queries.deleteInColumnContent(activeObject);
                Queries.deleteInDocItemClass(activeObject);
                Queries.deleteInPersonClass(activeObject);
                updateListview();
                break;
            default:
                break;
        }
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
        chooseDoc.listener = this;
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
        chooseDoc.listener = this;
        chooseDoc.show(getFragmentManager(), "DocListChoose");
    }

    @Override
    public void checkboxChange(View v, Long position, boolean bool) {
        activeObject = position;
        PersonDocItem persDocItem = Queries.getPersDocRelation((int) activeObject, document);
        Person person = persDocItem.getPerson();
        ColumnContent cell = Queries.fetchSingleCellData(person, activeColumn, document);
        cell.setValue(String.valueOf(bool));
        cell.save();
        initAdapterObjectList(valueList);
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
        initAdapterObjectList(valueList);
        boolAdapter.notifyDataSetChanged();
        stringAdapter.notifyDataSetChanged();
    }

    @Override
    public void getDocChoice(DocItem doc) {
        List<PersonDocItem> importList = Queries.getAllActPersons(doc);
        if(spinnerColumns != null && stringAdapter != null && boolAdapter != null)
        {
            for (PersonDocItem p : importList)
            {
                Person person = new Person(p.getPerson().toString(), document.getParentActivity());
                person.save();
                PersonDocItem perDocRelation = new PersonDocItem(person, document);
                perDocRelation.save();
                List<PersonDocItem> persons = Queries.getPersonCell(document);
                for (PersonDocItem pDoc : persons)
                {
                    ColumnContent newColumn = new ColumnContent("", document, activeColumn, person);
                    newColumn.save();
                }
            }
        }
        else
            Toast.makeText(getActivity(), "Listan är inte tom", Toast.LENGTH_SHORT).show();
            updateListview();
        }

    public void initAdapterObjectList(List<AdapterObjects> adapterObjects) {
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

