package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "AttendantTable")
public class Attendant extends Model implements Parcelable{
    @Column(name = "Name")
    private String name;
    @Column(name = "ParentCategory", onDelete = Column.ForeignKeyAction.CASCADE)
    private Category parentCategory;

    public Attendant(){
        super();
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public Attendant(String name, Category parent)
    {
        super();
        this.name = name;
        this.parentCategory = parent;

    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public void setName(String name) {
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
    public CellValue getColumnContent(Event doc, Columns column){
        return Queries.getSingleCellValue(this, column, doc);
    }
}
