package com.example.eandreje.androidapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class DefaultDialogFragment extends android.support.v4.app.DialogFragment {
    DefaultDialogFragmentListener listener;
    EditText textbox;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflate = getActivity().getLayoutInflater();
        View view = inflate.inflate(R.layout.default_dialog, null);

        builder.setNegativeButton(R.string.negative_answer_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton(R.string.positive_answer_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.enteredText(textbox.getText().toString());
            }
        });
        builder.setView(view);

        Dialog dialog = builder.create();
        textbox = (EditText)view.findViewById(R.id.dialog_editText);
        //textbox.setImeOptions(EditorInfo.IME_ACTION_DONE);
        //textbox.setSingleLine(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        return dialog;
    }

    //Communication interface
    public interface DefaultDialogFragmentListener {
        void enteredText(String text);
    }
}