package com.example.eandreje.androidapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class InputDialogFragment extends android.support.v4.app.DialogFragment {
    public DefaultDialogFragmentListener listener;
    private EditText textbox;
    private CheckBox checkbox;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        this.setRetainInstance(true);
        listener = (DefaultDialogFragmentListener) getTargetFragment();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString("addDocTitle"));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(getArguments().getInt("Layout"), null);
        checkbox  = (CheckBox) view.findViewById(R.id.column_dialog_checkbox);
        textbox = (EditText)view.findViewById(R.id.dialog_editText);
        TextView description = (TextView)view.findViewById(R.id.default_dialog_description);
        description.setText(getArguments().getString("DialogDesc"));

        builder.setNegativeButton(R.string.negative_answer_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton(R.string.positive_answer_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (getArguments().getInt("Caller")){
                    case R.id.add_column_icon:
                        listener.enteredTextBool(textbox.getText().toString(), getArguments().getInt("Caller"), checkbox.isChecked());
                        break;
                    case R.id.delete_column:
                        listener.onDeleteRequest(getArguments().getInt("Caller"));
                        break;
                    default:
                        listener.enteredText(textbox.getText().toString(), getArguments().getInt("Caller"));
                        break;
                }
        }});
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }

    //Communication interface
    public interface DefaultDialogFragmentListener {
        void enteredText(String text, int caller);
        void enteredTextBool(String text, int caller, boolean checked);
        void onDeleteRequest(int caller);
    }
}