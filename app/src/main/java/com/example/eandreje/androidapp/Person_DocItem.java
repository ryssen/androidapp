package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="PersonDocItem")
public class Person_DocItem extends Model implements Parcelable{
    @Column(name="Person_id")
    public Person person;
    @Column(name="Docitem_id")
    public DocItem docItem;

    public Person_DocItem (Person person, DocItem docItem)
    {
        super();
        this.person = person;
        this.docItem = docItem;
    }

        @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}


