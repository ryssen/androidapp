package com.example.eandreje.androidapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class DefaultDialogFragment extends android.support.v4.app.DialogFragment {
    public DefaultDialogFragmentListener listener;
    private EditText textbox;
    private CheckBox checkbox;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString("addDocTitle"));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(getArguments().getInt("Layout"), null);
        checkbox  = (CheckBox) view.findViewById(R.id.column_dialog_checkbox);
        textbox = (EditText)view.findViewById(R.id.dialog_editText);
        TextView description = (TextView)view.findViewById(R.id.default_dialog_description);
        description.setText(getArguments().getString("DialogDesc").toString());

        builder.setNegativeButton(R.string.negative_answer_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton(R.string.positive_answer_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (getArguments().getInt("Layout") != R.layout.add_column_layout)
                    listener.enteredText(textbox.getText().toString(), getArguments().getInt("Caller"));
                else
                    listener.enteredTextBool(textbox.getText().toString(), getArguments().getInt("Caller"), checkbox.isChecked());
            }
        });
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return dialog;
    }

    //Communication interface
    public interface DefaultDialogFragmentListener {
        void enteredText(String text, int caller);
        void enteredTextBool(String text, int caller, boolean checked);
    }
}