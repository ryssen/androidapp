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

    protected CellValue(Parcel in) {
        value = in.readString();
        parentEvent = in.readParcelable(Event.class.getClassLoader());
        parentColumn = in.readParcelable(Columns.class.getClassLoader());
        parentAttendant = in.readParcelable(Attendant.class.getClassLoader());
    }

    public static final Creator<CellValue> CREATOR = new Creator<CellValue>() {
        @Override
        public CellValue createFromParcel(Parcel in) {
            return new CellValue(in);
        }

        @Override
        public CellValue[] newArray(int size) {
            return new CellValue[size];
        }
    };

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
        dest.writeString(value);
        dest.writeParcelable(parentEvent, flags);
        dest.writeParcelable(parentColumn, flags);
        dest.writeParcelable(parentAttendant, flags);
    }
}
