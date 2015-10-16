package com.example.eandreje.androidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LeftsideDocumentFragment extends Fragment implements CreateDocumentFragment.CreateDocumentFragmentListener{
    private DocItem document;
    private String inDocTitle;

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
        inDocTitle = getArguments().getString("inDocTitle");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(inDocTitle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leftside_layout, container, false);
        TextView textView = (TextView)view.findViewById(R.id.left_column_name);
        textView.setText("Namn");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.in_document_actionbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void docObjectClicked(DocItem doc) {
    }
}
