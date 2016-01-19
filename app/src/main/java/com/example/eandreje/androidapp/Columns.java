package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="ColumnsTable")
public class Columns extends Model implements Parcelable {
    @Column(name ="Header")
    private String header;
    @Column(name = "Checkbox")
    private boolean isCheckbox;
    @Column(name = "ParentEvent", onDelete = Column.ForeignKeyAction.CASCADE)
    private Event parentEvent;

    public Columns() {
        super();
    }

    public Columns (String header, Event parent, Boolean bool)
    {
        super();
        this.header = header;
        this.parentEvent = parent;
        this.isCheckbox = bool;
    }

    protected Columns(Parcel in) {
        header = in.readString();
        isCheckbox = in.readByte() != 0;
        parentEvent = in.readParcelable(Event.class.getClassLoader());
    }

    public static final Creator<Columns> CREATOR = new Creator<Columns>() {
        @Override
        public Columns createFromParcel(Parcel in) {
            return new Columns(in);
        }

        @Override
        public Columns[] newArray(int size) {
            return new Columns[size];
        }
    };

    public void setHeader(String header) {
        this.header = header;
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
        dest.writeString(header);
        dest.writeByte((byte) (isCheckbox ? 1 : 0));
        dest.writeParcelable(parentEvent, flags);
    }
}
