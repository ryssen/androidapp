package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ListItem implements Parcelable {
    int id;
    String name;
    private final ArrayList<DocItem> docContainer = new ArrayList<DocItem>();

    public ListItem( ){
        super();
    }

    public ListItem(int id, String name){
        super();
        this.id = id;
        this.name = name;
    }

//    public ListItem(String name) {
//        this.name = name;
//    }

    public void setId(int id) {this.id = id;}

    public int getId() {return id;}

    public void setName(String name) {this.name = name;}

    public String getName(){return name;}

    public void addToDocList(DocItem doc){
          docContainer.add(doc);
    }

    public List<DocItem> getDocContainer() {
        return docContainer;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
