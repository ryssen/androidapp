package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "EventTable")
public class Event extends Model implements Parcelable {

    @Column(name = "Name")
    String name;
    @Column(name = "ParentCategory", onDelete = Column.ForeignKeyAction.CASCADE)
    Category parentCategory;

   public Event(){
       super();
   }

    public Event(String name) {
        super();
        this.name = name;
    }

    protected Event(Parcel in) {
        name = in.readString();
        parentCategory = in.readParcelable(Category.class.getClassLoader());
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public void setName(String name){
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
        dest.writeString(name);
        dest.writeParcelable(parentCategory, flags);
    }

    public Category getParentCategory() {
        return parentCategory;
    }
}
