package com.example.eandreje.androidapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

public class DefaultDialogFragment extends android.support.v4.app.DialogFragment {
    DefaultDialogFragmentListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflate = getActivity().getLayoutInflater();
        builder.setView(inflate.inflate(R.layout.default_dialog, null));

        builder.setNegativeButton(R.string.negative_answer_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton(R.string.positive_answer_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText textbox = (EditText) getDialog().findViewById(R.id.dialog_editText);
                listener.enteredText(textbox.getText().toString());

            }
        });
        Dialog dialog = builder.create();
        return dialog;
    }

    //Communication interface
    public interface DefaultDialogFragmentListener {
        void enteredText(String text);
    }
}