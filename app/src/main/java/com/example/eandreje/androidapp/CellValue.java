package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "CellValueTable")
public class CellValue extends Model implements Parcelable {
    @Column(name = "Value")
    String value;
    @Column(name = "ParentEvent", onDelete = Column.ForeignKeyAction.CASCADE)
    Event parentEvent;
    @Column(name = "ParentColumn", onDelete = Column.ForeignKeyAction.CASCADE)
    Columns parentColumn;
    @Column(name = "ParentAttendant", onDelete = Column.ForeignKeyAction.CASCADE)
    Attendant parentAttendant;

    public CellValue(){
        super();
    }

    public CellValue(String value, Event event, Columns parent, Attendant attendant)
    {
        super();
        this.value = value;
        this.parentEvent = event;
        this.parentColumn = parent;
        this.parentAttendant = attendant;
    }

    public Attendant getParentAttendant() {
        return parentAttendant;
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
