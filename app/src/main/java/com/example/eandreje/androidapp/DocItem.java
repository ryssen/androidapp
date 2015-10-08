package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

public class DocItem implements Parcelable {
    int id;
    int parentId;
    String name;
    String context = "doable";
    

    public DocItem(){super();}

    public DocItem(int id, int parentID, String name){
        this.id = id;
        this.parentId = parentID;
        this.name = name;

    }

    public DocItem(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }


    public void setId(int id) {this.id = id;}

    public int getId() {return id;}

    public void setParentId(int parentId) {this.parentId = parentId;}

    public int getParentId() {return parentId;}

    public void setName(String name){this.name = name;}

    public String getName(){return name;}

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


