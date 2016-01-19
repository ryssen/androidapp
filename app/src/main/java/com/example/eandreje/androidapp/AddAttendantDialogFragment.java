package com.example.eandreje.androidapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class AddAttendantDialogFragment extends DialogFragment{
    public AddPersonDialogFragmentListener listener;
    private ArrayList<Columns> columnList;
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
        TextView description = (TextView)view.findViewById(R.id.default_dialog_description);
        description.setText(getArguments().getString("DialogDesc"));

        for (Columns column: columnList)
        {
            if(column.isCheckbox())
            {
                View checkboxView = inflater.inflate(R.layout.checkbox_view, parent, false);
                TextView name = (TextView) checkboxView.findViewById(R.id.column_name);
                name.setText(column.toString());
                parent.addView(checkboxView);
            }
            else
            {
                View editTextView = inflater.inflate(R.layout.edit_text_view, parent, false);
                TextView name = (TextView) editTextView.findViewById(R.id.column_name);
                name.setText(column.toString());
                parent.addView(editTextView);
            }
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Event event = getArguments().getParcelable("DocParent");
                EditText personName = (EditText) view.findViewById(R.id.new_person_name);
                Attendant attendant = new Attendant(personName.getText().toString(), event.getParentCategory());
                attendant.save();
                AttendantEvent perDocRelation = new AttendantEvent(attendant, event);
                perDocRelation.save();

                if (columnList.size() != 0) {
                    for (Columns column : columnList) {
                        column.save();
                        if (!column.isCheckbox()) {
                            EditText value = (EditText) view.findViewById(R.id.column_value);
                            CellValue cellValue = new CellValue(value.getText().toString(), event, column, attendant);
                            cellValue.save();
                        } else {
                            CheckBox value = (CheckBox) view.findViewById(R.id.checkbox_column_id);
                            String checked;
                            if (value.isChecked())
                                checked = "true";
                            else
                                checked = "false";
                            CellValue cellValue = new CellValue(checked, event, column, attendant);
                            cellValue.save();
                        }
                    }
                }
                listener.newPersonAdded(event);
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
        void newPersonAdded(Event doc);
    }
}
