package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "ColumnContent")
public class ColumnContent extends Model implements Parcelable {
    @Column(name = "Value")
    String value;
    @Column(name = "ParentDoc")
    DocItem parentDocument;
    @Column(name = "ParentColumn")
    Columns parentColumn;
    @Column(name = "ParentPerson")
    Person parentPerson;

    public ColumnContent(){
        super();
    }

    public ColumnContent(String value, DocItem doc, Columns parent, Person person)
    {
        super();
        this.value = value;
        this.parentDocument = doc;
        this.parentColumn = parent;
        this.parentPerson = person;
    }

    public Person getParentPerson() {
        return parentPerson;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
