package com.example.eandreje.androidapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class AddPersonDialogFragment extends DialogFragment{
    public AddPersonDialogFragmentListener listener;
    private ArrayList<Columns> columnList;
    private ArrayList<LinearLayout> layoutList;
    private View view;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        listener = (AddPersonDialogFragmentListener) getTargetFragment();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getArguments().getString("dialogTitle"));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_person_layout, null);
        LinearLayout parent = (LinearLayout) view.findViewById(R.id.linear3);
        columnList = getArguments().getParcelableArrayList("Columns");
        layoutList = new ArrayList<>();
        TextView description = (TextView)view.findViewById(R.id.default_dialog_description);
        description.setText(getArguments().getString("DialogDesc"));

        for (Columns column: columnList)
        {
            if(column.isCheckbox())
            {
                View checkboxView = inflater.inflate(R.layout.checkbox_view, parent, false);
                TextView name = (TextView) checkboxView.findViewById(R.id.column_name);
                name.setText(column.toString());
                layoutList.add((LinearLayout) checkboxView);
                parent.addView(checkboxView);
            }
            else
            {
                View editTextView = inflater.inflate(R.layout.edit_text_view, parent, false);
                TextView name = (TextView) editTextView.findViewById(R.id.column_name);
                name.setText(column.toString());
                layoutList.add((LinearLayout) editTextView);
                parent.addView(editTextView);
            }
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DocItem doc = getArguments().getParcelable("DocParent");
                EditText personName = (EditText) view.findViewById(R.id.new_person_name);
                Person person = new Person(personName.getText().toString(), doc.getParentActivity());
                person.save();
                PersonDocItem perDocRelation = new PersonDocItem(person, doc);
                perDocRelation.save();

                int i = 0;
                if (columnList.size() != 0) {
                    for (LinearLayout view : layoutList) {
                        Columns column = columnList.get(i);
                        column.save();
                        if (!column.isCheckbox()) {
                            EditText value = (EditText) view.findViewById(R.id.column_value);
                            ColumnContent cellValue = new ColumnContent(value.getText().toString(), doc, column, person);
                            cellValue.save();
                            i++;
                        } else {
                            CheckBox value = (CheckBox) view.findViewById(R.id.checkbox_column_id);
                            String checked;

                            if (value.isChecked())
                                checked = "true";
                            else
                                checked = "false";

                            ColumnContent cellValue = new ColumnContent(checked, doc, column, person);
                            cellValue.save();
                            i++;
                        }
                    }
                }
                listener.newPersonAdded(doc);
            }
        });
        builder.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
        }
        });
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }
    public interface AddPersonDialogFragmentListener{
        void newPersonAdded(DocItem doc);
    }
}
