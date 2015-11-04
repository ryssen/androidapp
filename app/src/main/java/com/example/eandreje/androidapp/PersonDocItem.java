package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="PersonDocItem")
public class PersonDocItem extends Model implements Parcelable{
    @Column(name="Person")
    private Person person;
    @Column(name="DocItem")
    private DocItem docItem;

    public PersonDocItem()
    {
        super();
    }

    public PersonDocItem(Person person, DocItem docItem)
    {
        super();
        this.person = person;
        this.docItem = docItem;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}


