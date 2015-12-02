package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="AttendantEventTable")
public class AttendantEvent extends Model implements Parcelable{
    @Column(name="Attendant", onDelete = Column.ForeignKeyAction.CASCADE)
    private Attendant attendant;
    @Column(name="Event", onDelete = Column.ForeignKeyAction.CASCADE)
    private Event event;

    public AttendantEvent()
    {
        super();
    }

    public AttendantEvent(Attendant attendant, Event event)
    {
        super();
        this.attendant = attendant;
        this.event = event;
    }

    public Attendant getAttendant() {
        return attendant;
    }

    public void setAttendant(Attendant attendant) {
        this.attendant = attendant;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}


