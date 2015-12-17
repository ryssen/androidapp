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

public class PresenceFragment extends Fragment implements EventFragment.CreateDocumentFragmentListener,
        CustomStringAdapter.CustomStringAdapterListener, InputDialogFragment.DefaultDialogFragmentListener,
        AddAttendantDialogFragment.AddPersonDialogFragmentListener, CustomBoolAdapter.CustomBoolAdapterListener,
        OptionsDialogFragment.OptionsDialogFragmentListener {

    private static final String COLUMN_TITLE = "Ny kolumn";
    private static final String ATTENDANT_TITLE = "Ny deltagare";
    private static final String TEXTVIEW_CHANGE = "Nytt värde";
    private static final String EVENTLIST_TITLE = "Importera namn";
    private static final String DIALOG_IMPORT = "Välj ett event för import av deltagare";
    private static final String DIALOG_ADD_ATTENDANT = "Skriv in namn och önskat kolumnvärde om tillgängligt";
    private static final String DIALOG_ADD_COLUMN = "Skriv namn och om kolumntypen ska vara checkruta eller text";
    private static final String DIALOG_RENAME_COLUMN = "Nytt namn på kolumn";
    private static final String DELETE_TITLE = "Alternativ";
    private static final String DIALOG_DELETE = "Ta bort aktuell rad?";
    private static final String DIALOG_DELETE_COLUMN = "Ta bort aktuell kolumn och all tillhörande data?";

    private static final int ADD_PERSON_CODE = 3;

    private Event event;
    private String inEventTitle;
    private List<AdapterObject> valueList;
    private CustomBoolAdapter boolAdapter;
    private CustomStringAdapter stringAdapter;
    private ArrayAdapter<Columns> spinnerAdapt;
    private List<Columns> spinnerColumns;
    private ListView listView;
    private long activeObject;
    private Columns activeColumn;
    private Spinner spinner;

    public static PresenceFragment newInstance(Event event) {
        PresenceFragment presenceFragment = new PresenceFragment();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("activeDocument", event);
        presenceFragment.setArguments(bundle);
        return presenceFragment;
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
            event = savedInstanceState.getParcelable("activeDocument");
            activeObject = savedInstanceState.getLong("ActiveObject");
        }

        //activeColumn = savedInstanceState.getParcelable("ActiveColumn");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        event = getArguments().getParcelable("activeDocument");
        inEventTitle = event.toString();

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(inEventTitle);
        event = getArguments().getParcelable("activeDocument");
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
    initAdapterObjectList(valueList, event);
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
        InputDialogFragment inputDialogFragment;

        switch (item.getItemId())
        {
            case R.id.add_person_icon:
                AddAttendantDialogFragment addPerson = new AddAttendantDialogFragment();
                bundle = new Bundle();
                bundle.putParcelableArrayList("Columns", (ArrayList<? extends Parcelable>) spinnerColumns);
                bundle.putInt("Layout", R.layout.add_person_layout);
                bundle.putParcelable("DocParent", event);
                bundle.putString("dialogTitle", ATTENDANT_TITLE);
                bundle.putString("DialogDesc", DIALOG_ADD_ATTENDANT);
                bundle.putInt("Caller", R.id.add_person_icon);
                addPerson.setArguments(bundle);
                //addPerson.listener = this;
                addPerson.setTargetFragment(this, ADD_PERSON_CODE );
                addPerson.show(getFragmentManager(), "addPerson");
                break;




            case R.id.add_column_icon:
                inputDialogFragment = new InputDialogFragment();
                bundle = new Bundle();
                bundle.putString("addDocTitle", COLUMN_TITLE);
                bundle.putInt("Layout", R.layout.add_column_layout);
                bundle.putInt("Caller", R.id.add_column_icon);
                bundle.putString("DialogDesc", DIALOG_ADD_COLUMN);
                inputDialogFragment.setArguments(bundle);
                inputDialogFragment.setTargetFragment(this, ADD_PERSON_CODE);
                //inputDialogFragment.listener = this;
                inputDialogFragment.show(getFragmentManager(), "newColumnDialog");
                break;
            case R.id.rename_column:
                if (spinnerColumns.size() != 0){
                    inputDialogFragment = new InputDialogFragment();
                    bundle = new Bundle();
                    bundle.putString("addDocTitle", COLUMN_TITLE);
                    bundle.putInt("Layout", R.layout.default_dialog);
                    bundle.putInt("Caller", R.id.rename_column);
                    bundle.putString("DialogDesc", DIALOG_RENAME_COLUMN);
                    inputDialogFragment.setArguments(bundle);
                    inputDialogFragment.setTargetFragment(this, ADD_PERSON_CODE);
                    //inputDialogFragment.listener = this;
                    inputDialogFragment.show(getFragmentManager(), "newColumnDialog");
                }
                else
                    Toast.makeText(getContext(), "Listan med kolumner är tom", Toast.LENGTH_SHORT).show();
                break;
            case R.id.import_persons:
                OptionsDialogFragment chooseDoc = new OptionsDialogFragment();
                bundle = new Bundle();
                bundle.putString("DialogTitle", EVENTLIST_TITLE);
                bundle.putString("DialogDesc", DIALOG_IMPORT);
                bundle.putInt("Layout", R.layout.act_options_layout);
                bundle.putInt("Caller", R.id.import_persons);
                bundle.putParcelable("ParentAct", event.getParentCategory());
                bundle.putParcelable("ActiveDoc", event);
                chooseDoc.setArguments(bundle);
                chooseDoc.setTargetFragment(this, ADD_PERSON_CODE );
                //chooseDoc.listener = this;
                chooseDoc.show(getFragmentManager(), "DocListChoose");
                break;
            case R.id.import_persons_columns:
                OptionsDialogFragment doc = new OptionsDialogFragment();
                bundle = new Bundle();
                bundle.putString("DialogTitle", EVENTLIST_TITLE);
                bundle.putString("DialogDesc", DIALOG_IMPORT);
                bundle.putInt("Layout", R.layout.act_options_layout);
                bundle.putInt("Caller", R.id.import_persons_columns);
                bundle.putParcelable("ParentAct", event.getParentCategory());
                bundle.putParcelable("ActiveDoc", event);
                doc.setArguments(bundle);
                doc.setTargetFragment(this, ADD_PERSON_CODE );
                //doc.listener = this;
                doc.show(getFragmentManager(), "DocListChoose");
                break;
            case R.id.delete_column:
                if(spinnerColumns.size() != 0){
                    inputDialogFragment = new InputDialogFragment();
                    bundle = new Bundle();
                    bundle.putString("addDocTitle", COLUMN_TITLE);
                    bundle.putInt("Layout", R.layout.delete_dialog);
                    bundle.putInt("Caller", R.id.delete_column);
                    bundle.putString("DialogDesc", DIALOG_DELETE_COLUMN);
                    inputDialogFragment.setArguments(bundle);
                    inputDialogFragment.setTargetFragment(this, ADD_PERSON_CODE);
                    //inputDialogFragment.listener = this;
                    inputDialogFragment.show(getFragmentManager(), "DeleteDialog");
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
    public void docObjectClicked(Event doc) {
    }

    @Override
    public void enteredText(String text, int id) {
    Attendant attendant;
    AttendantEvent persDocItem;
    CellValue cell;

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
                persDocItem = Queries.getAttendantEventRelation(activeObject, event);
                attendant = persDocItem.getAttendant();
                //cell = Queries.getSingleCellValue(attendant, activeColumn, event);
                //cell.parentAttendant.setName(text);
                //cell.save();
                persDocItem = Queries.getAttendantEventRelation(activeObject, event);
                persDocItem.getAttendant().setName(text);
                persDocItem.getAttendant().save();
                updateListview();
            }
            else
                Toast.makeText(getContext(), "Ett namnfält får ej vara tomt", Toast.LENGTH_SHORT).show();
            break;
        case R.id.column_name:
            if(!text.equals("")){
                persDocItem = Queries.getAttendantEventRelation(activeObject, event);
                attendant = persDocItem.getAttendant();
                cell = Queries.getSingleCellValue(attendant, activeColumn, event);
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
        Columns column = new Columns(text, event, checked);
        column.save();
        List<AttendantEvent> persons = Queries.getPersonCell(event);
        for (AttendantEvent pDoc : persons) {
            Attendant attendant = pDoc.getAttendant();
            CellValue newColumn = new CellValue("", event, column, attendant);
            newColumn.save();
        }
        spinnerColumns = Queries.getColumnHeaders(event);
        spinnerAdapt.clear();
        spinnerAdapt.addAll(spinnerColumns);
        }
        else{
            Toast.makeText(getContext(), "Ett namnfält får ej vara tomt", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteRequest(int caller) {
        activeColumn.delete();
        spinnerColumns = Queries.getColumnHeaders(event);
        spinnerAdapt.clear();
        spinnerAdapt.addAll(spinnerColumns);
        if(spinnerColumns.size() != 0){
            activeColumn = spinnerColumns.get(0);
        }
        updateColumnData(valueList);
        initListview();
        boolAdapter.notifyDataSetChanged();
        stringAdapter.notifyDataSetChanged();
    }

    @Override
    public void newPersonAdded(Event doc) {
        updateListview();
    }

    @Override
    public void buttonPressed(View v, Long position) {
        activeObject = position;
        InputDialogFragment dialog = new InputDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Layout", R.layout.default_dialog);
        if(v.getId() == R.id.person_name)
            bundle.putInt("Caller", R.id.person_name);
        else
            bundle.putInt("Caller", R.id.column_name);
        bundle.putString("DialogDesc", DIALOG_ADD_ATTENDANT);
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
                Queries.getAttendantEventRelation(activeObject, event).getAttendant().delete();
                updateListview();


                break;
        }
    }

    @Override
    public void importPers(Event doc) {
        List<AttendantEvent> importList = Queries.getAllAttendantEvent(doc);
        if(spinnerColumns != null && stringAdapter != null && boolAdapter != null)
        {
            for (AttendantEvent p : importList)
            {
//                Attendant attendant = p.getAttendant();
//                attendant.save();
                //new Attendant(p.getAttendant().toString(), event.getParentCategory());
                AttendantEvent perDocRelation = new AttendantEvent(p.getAttendant(), event);
                perDocRelation.save();
                for (Columns col:spinnerColumns) {
                    CellValue newColumn = new CellValue("", event, col, p.getAttendant());
                    newColumn.save();
                }
            }
        }
        else
            Toast.makeText(getActivity(), "Listan är inte tom", Toast.LENGTH_SHORT).show();
        updateListview();
    }

    @Override
    public void importPersCol(Event doc) {
        List<Columns> importedCol = Queries.getColumnHeaders(doc);
        activeColumn = importedCol.get(0);
        for (Columns column:importedCol) {
            Columns newCol = new Columns(column.toString(), event, column.isCheckbox());
            newCol.save();
        }
        spinnerColumns = Queries.getColumnHeaders(event);
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
        AttendantEvent persDocItem = Queries.getAttendantEventRelation(activeObject, event);
        Attendant attendant = persDocItem.getAttendant();
        CellValue cell = Queries.getSingleCellValue(attendant, activeColumn, event);
        cell.setValue(String.valueOf(bool));
        cell.save();
        initAdapterObjectList(valueList, event);
    }

    @Override
    public void textboxClick(View v, Long position) {
        activeObject = position;
        InputDialogFragment dialog = new InputDialogFragment();
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
            spinnerColumns = Queries.getColumnHeaders(event);
            spinnerAdapt.clear();
            spinnerAdapt.addAll(spinnerColumns);
        }
    }
    private void updateListview(){
        initAdapterObjectList(valueList, event);
        boolAdapter.notifyDataSetChanged();
        stringAdapter.notifyDataSetChanged();
    }

    public void initAdapterObjectList(List<AdapterObject> adapterObjects, Event document) {
    adapterObjects.clear();
    List<AttendantEvent> persons = Queries.getPersonCell(document);
    for (AttendantEvent pDI:persons)
    {
        AdapterObject newObject = new AdapterObject();
        newObject.attendant = pDI.getAttendant();
        if(activeColumn != null)
            newObject.cellValue = newObject.attendant.getColumnContent(document, activeColumn);
        adapterObjects.add(newObject);
        }
    }

    public void updateColumnData(List<AdapterObject> adapterObjects) {
    for (AdapterObject aO:adapterObjects)
    {

        aO.cellValue = Queries.getSingleCellValue(aO.attendant, activeColumn, event);
    }
    }
}

