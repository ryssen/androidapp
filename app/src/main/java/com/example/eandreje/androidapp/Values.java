//package com.example.eandreje.androidapp;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.activeandroid.Model;
//import com.activeandroid.annotation.Column;
//import com.activeandroid.annotation.Table;
//
///**
// * Created by johaerik on 2015-10-21.
// */
//@Table(name="Values")
//public class Values extends Model implements Parcelable {
//    @Column(name="value")
//    String value;
//    //  // TODO: 2015-10-21 DEKLARERA PARENT
//    //@Column(name = "Parent_DocItem");
//    //DocItem parentActivity;
//    //@Column(name = "Parent_Columns");
//    //Columns parentActivity;
//    //@Column(name = "Parent_Person");
//    //Person parentActivity;
//
//    public Values(){
//        super();
//    }
//
//    public Values (String value)
//    {
//        this.value = value;
//    }
//
//    @Override
//    public String toString() {
//        return this.value;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//
//    }
//}
