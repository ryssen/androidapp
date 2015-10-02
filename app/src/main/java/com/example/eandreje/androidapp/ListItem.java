package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ListItem implements Parcelable {
    String id;
    private final ArrayList<DocItem> docContainer = new ArrayList<DocItem>();

    public ListItem( ){
        super();
    }

    public ListItem(String name){
        super();
        this.id = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addToDocList(DocItem doc){
          docContainer.add(doc);
    }

    public ArrayList<DocItem> getDocContainer() {
        return docContainer;
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
