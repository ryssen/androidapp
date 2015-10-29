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

import java.util.ArrayList;

public class LeftsideDocumentFragment extends Fragment implements CreateDocumentFragment.CreateDocumentFragmentListener,
        CustomStringAdapter.CustomStringAdapterListener, DefaultDialogFragment.DefaultDialogFragmentListener,
        AddPersonDialogFragment.AddPersonDialogFragmentListener, CustomBoolAdapter.CustomBoolAdapterListener{

    private static final String COLUMN_TITLE = "Lägg till en kolumn";
    private static final String PERSON_TITLE = "Lägg till en deltagare";

    private DocItem document;
    private String inDocTitle;
    private ArrayList<ColumnContent> stringValueList;
    private CustomBoolAdapter boolAdapter;
    private CustomStringAdapter stringAdapter;
    private ArrayAdapter<Columns> spinnerAdapt;
    private ArrayList<Columns> spinnerColumns;
    private ListView listView;
    private Spinner spinner;
    private Bundle bundle;
    private View viewClicked;
    private int listPos;
    private int spinnerIndex;

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
        inDocTitle = getArguments().getString("inDocTitle");
        document = getArguments().getParcelable("activeDocument");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(inDocTitle);
        spinnerColumns = Queries.getColumnHeaders(document);
        spinnerAdapt.clear();
        spinnerAdapt.addAll(spinnerColumns);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leftside_layout, container, false);
        listView = (ListView) view.findViewById(R.id.listView4);
        spinnerColumns = new ArrayList<>();
        stringValueList = new ArrayList<>();
        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinnerAdapt = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerColumns);
        //stringValueList = Queries.getValue(document);
        boolAdapter = new CustomBoolAdapter(getContext(), stringValueList, this);
        stringAdapter = new CustomStringAdapter(getContext(), stringValueList, this);
        spinner.setAdapter(spinnerAdapt);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerColumns.get(position).isCheckbox()) {
                    //Toast.makeText(getActivity(), "position = "+position+"Id = "+id, Toast.LENGTH_SHORT).show();
                    spinnerIndex = position;
                    listView.setAdapter(boolAdapter);
                    stringValueList = Queries.fetchColumnCellData(spinnerColumns.get(position));
                    boolAdapter.clear();
                    boolAdapter.addAll(stringValueList);
                    boolAdapter.notifyDataSetChanged();
                } else {
                    //Toast.makeText(getActivity(), "position = "+position+"Id = "+id, Toast.LENGTH_SHORT).show();
                    spinnerIndex = position;
                    listView.setAdapter(stringAdapter);
                    stringValueList = Queries.fetchColumnCellData(spinnerColumns.get(position));
                    stringAdapter.clear();
                    stringAdapter.addAll(stringValueList);
                    stringAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                stringValueList.get(position).delete();
                stringAdapter.clear();
                stringAdapter.addAll(stringValueList);
                stringAdapter.notifyDataSetChanged();
                return true;
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
        switch (item.getItemId())
        {
            case R.id.add_person_icon:
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
                defaultDialogFragment.listener = this; //interface gets its reference
                defaultDialogFragment.show(getFragmentManager(), "newColumnDialog");
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
        switch (id)
        {
            case R.id.add_column_icon:
                break;
            case R.id.add_person_icon:
                break;
            case R.id.person_name:
                //Toast.makeText(getActivity(), "id = "+listPos, Toast.LENGTH_SHORT).show();
                Person person = Person.load(Person.class, listPos+1);
                person.setName(text);
                person.save();
                stringAdapter.clear();
                stringAdapter.addAll(stringValueList);
                break;
                //Toast.makeText(getActivity(), "bra erik", Toast.LENGTH_SHORT).show();
            case R.id.column_name:
                //Toast.makeText(getActivity(), "id = "+listPos, Toast.LENGTH_SHORT).show();
                //ColumnContent cellContent = ColumnContent.load(ColumnContent.class, spinnerIndex);
                ColumnContent cellContent = Queries.fetchSingleCellData(spinnerIndex);
                cellContent.setValue(text);
                cellContent.save();
                stringAdapter.clear();
                stringAdapter.addAll(stringValueList);
                //Toast.makeText(getActivity(), "bra erik", Toast.LENGTH_SHORT).show();
                break;
            default:
        }

    }

    @Override
    public void enteredTextBool(String text, int caller, boolean checked) {
        Columns column = new Columns(text, document, checked);
        column.save();
        spinnerColumns = Queries.getColumnHeaders(document);
        spinnerAdapt.clear();
        spinnerAdapt.addAll(spinnerColumns);
    }

    @Override
    public void newPersonAdded(DocItem doc) {
        stringValueList = Queries.fetchCellData(doc);
        stringAdapter.clear();
        stringAdapter.addAll(stringValueList);
    }

    @Override
    public void buttonPressed(View v, int position) {
        viewClicked = v;
        listPos = position;
        DefaultDialogFragment dialog = new DefaultDialogFragment();
        Bundle bundle = new Bundle();
        String title = "Ändra text";
        bundle.putInt("Layout", R.layout.default_dialog);
        if(v.getId() == R.id.person_name)
            bundle.putInt("Caller", R.id.person_name);
        else
            bundle.putInt("Caller", R.id.column_name);
        bundle.putString("addDocTitle", title);
        dialog.setArguments(bundle);
        dialog.listener = this;
        dialog.show(getFragmentManager(), "changeCustomAdaptAttr");
    }

    @Override
    public void checkboxChange(View v, int position, boolean bool) {
        viewClicked = v;
        listPos = position;
        DefaultDialogFragment dialog = new DefaultDialogFragment();
        Bundle bundle = new Bundle();
        String title = "Ändra text";
        bundle.putInt("Layout", R.layout.default_dialog);
        bundle.putInt("Caller", R.id.column_name);
        bundle.putString("addDocTitle", title);
        dialog.setArguments(bundle);
        dialog.listener = this;
        dialog.show(getFragmentManager(), "changeCustomAdaptAttr");
    }

    @Override
    public void textboxClick(View v, int position) {

    }
}
