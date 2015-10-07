package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

public class DocItem implements Parcelable {
    String id;
    String context = "doable";

    public DocItem(){
    }

    public DocItem(String name){
        id = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
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


