package com.example.eandreje.androidapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

public class CustomDialogFragment extends DialogFragment {
    Communicator communicator;

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
                EditText textbox = (EditText)getDialog().findViewById(R.id.dialog_editText);
                communicator.activityName(textbox.getText().toString());
                Toast.makeText(getActivity(), textbox.getText().toString() + " är tillagd", Toast.LENGTH_SHORT).show();
            }
        });
        Dialog dialog = builder.create();
        return dialog;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        communicator = (Communicator)getActivity();
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            communicator = (Communicator)getActivity();
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement Communicator");
        }
    }


    //Checks if the user wants to change name on an existing "Aktivitet" or delete it.
    //If the user wants to change a name the function addOrChangeName is called.
    //If the user wants to delete an "Aktivitet", its deleted from the list and the Dialog is closed.
//    public void Dialog(final int position) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setTitle(stringChosen);
//        builder.setItems(RemoveEdit,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (which == 0) {
//                            editName = true;
//                            addOrChangeName();
//                        }
//                        if (which == 1) {
//                            ListItem itemToRemove = activityAdapter.getItem(position);
//                            activityAdapter.remove(itemToRemove);
//                            dialog.dismiss();
//                            sharedPre.saveToSharedPref(context,activityList);
//
//                        }
//                    }
//                });
//        builder.show();
//    }

//    This function either adds or changes a name depending on if the boolean EditName = true/false.
//    The boolean is set in the function Dialog above and is set to true or false depending on if the user
//    clicked "Ändra namn" or "ta bort namn" in the RemoveEdit-string set in the Dialog.
//    The title on the Alert will also change depending on what the user has chosen.
//    Its not possible to enter a name which already exists.
//    public void addOrChangeName() {
//        LayoutInflater li = LayoutInflater.from(this);
//        View add_activityView = li.inflate(R.layout.add_activity, null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setView(add_activityView);
//        final EditText userInput = (EditText) add_activityView.findViewById(R.id.user_input);
//        builder.setCancelable(false);
//        if (editName) {
//            builder.setTitle(R.string.changeName);
//        }
//        if (!editName) {
//            builder.setTitle(R.string.add_activity_title);
//        }
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (activityList.contains(userInput.getText().toString())
//                        || userInput.getText().toString().equals("")
//                        || userInput.getText().toString().contains("\n")) {
////                    Toast.makeText(MainActivity.this,
////                            "Aktiviteten måste vara unik, ej innehålla mellanslag eller ny rad",
////                            Toast.LENGTH_LONG).show();
//                } else {
//                    if (editName) {
//                        activityAdapter.getItem(posChosen).setId(userInput.getText().toString());
//                    }
//                    if (!editName) {
//                        activityAdapter.add(new ListItem(userInput.getText().toString()));
//                    }
//                }
//                sharedPre.saveToSharedPref(context,activityList);
//            }
//        });
//        builder.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
    public interface Communicator{
        public void activityName(String name);
    }
}