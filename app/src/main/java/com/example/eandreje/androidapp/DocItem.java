package com.example.eandreje.androidapp;
//
import android.os.Parcel;
import android.os.Parcelable;
//
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Documents")
public class DocItem extends Model implements Parcelable {

    @Column(name = "Name")
    String name;
    @Column(name = "Parent")
    ListItem parentActivity;

   public DocItem(){
       super();
   }

    public DocItem(String name) {
        super();
        this.name = name;
    }

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

    }

    public ListItem getParentActivity() {
        return parentActivity;
    }
}
