package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Values")
public class Values extends Model implements Parcelable {
    @Column(name = "Value")
    String value;
//    @Column(name = "ParentDocItem");
//    DocItem parentDocument;
//    @Column(name = "ParentColumn");
//    Columns parentColumn;
//    @Column(name = "ParentPerson");
//    Person parentPerson;

    public Values(){
        super();
    }

    public Values (String value)
    {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
