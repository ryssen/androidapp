package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Person")
public class Person extends Model implements Parcelable{
    @Column(name = "Name")
    private String name;
    @Column(name = "Parent")
    private ListItem parentActivity;

    public Person(){
        super();
    }

    public Person (String name, ListItem parent)
    {
        this.name = name;
        this.parentActivity = parent;

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