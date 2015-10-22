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
//@Table(name="Columns")
//public class Columns extends Model implements Parcelable {
//    @Column(name ="Header-names")
//    String header;
//    @Column(name = "Parent")
//    DocItem parentActivity;
//
//    public Columns(){
//        super();
//    }
//
//    public Columns (String header)
//    {
//        this.header = header;
//    }
//
//    @Override
//    public String toString() {
//        return this.header;
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
