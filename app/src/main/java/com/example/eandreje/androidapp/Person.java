package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


/**
 * Created by johaerik on 2015-10-21.
 */
@Table(name = "Person")
public class Person extends Model implements Parcelable{
    @Column(name = "Name")
    String name;
    @Column(name = "Parent")
    ListItem parentActivity;

    public Person(){
        super();
    }

    public Person (String name)
    {
        this.name = name;
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
