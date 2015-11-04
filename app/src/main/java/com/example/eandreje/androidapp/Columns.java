package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="Columns")
public class Columns extends Model implements Parcelable {
    @Column(name ="Header")
    private String header;
    @Column(name = "Checkbox")
    private boolean isCheckbox;
    @Column(name = "Parent")
    private DocItem parentDocument;

    public Columns(){
        super();
    }

    public Columns (String header, DocItem parent, Boolean bool)
    {
        super();
        this.header = header;
        this.parentDocument = parent;
        this.isCheckbox = bool;
    }

    public boolean isCheckbox() {
        return isCheckbox;
    }

    @Override
    public String toString() {
        return this.header;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
