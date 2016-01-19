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

    public AttendantEvent() {
        super();
    }

    public AttendantEvent(Attendant attendant, Event event)
    {
        this.attendant = attendant;
        this.event = event;
    }

    protected AttendantEvent(Parcel in) {
        attendant = in.readParcelable(Attendant.class.getClassLoader());
        event = in.readParcelable(Event.class.getClassLoader());
    }

    public static final Creator<AttendantEvent> CREATOR = new Creator<AttendantEvent>() {
        @Override
        public AttendantEvent createFromParcel(Parcel in) {
            return new AttendantEvent(in);
        }

        @Override
        public AttendantEvent[] newArray(int size) {
            return new AttendantEvent[size];
        }
    };

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
        dest.writeParcelable(attendant, flags);
        dest.writeParcelable(event, flags);
    }
}


