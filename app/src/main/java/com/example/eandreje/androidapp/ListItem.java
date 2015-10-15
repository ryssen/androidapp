package com.example.eandreje.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.List;

@Table(name = "Activities")
public class ListItem extends Model implements Parcelable {
    //int id;
    @Column(name = "Name")
    String name;
    //private final ArrayList<DocItem> docContainer = new ArrayList<DocItem>();

    public ListItem( ){
        super();
    }
//
//    public ListItem(int id, String name){
//        super();
//        this.id = id;
//        this.name = name;
//    }
//
    public ListItem(String name) {
        this.name = name;
    }
//
//    public void setId(int id) {this.id = id;}
//
//    public int getId() {return id;}
//
//    public void setName(String name) {this.name = name;}
//
//    public String getName(){return name;}
//
//    public void addToDocList(DocItem doc){
//          docContainer.add(doc);
//    }
//
//    public List<DocItem> getDocContainer() {
//        return docContainer;
//    }
    public List<DocItem> getDocuments(){
        return getMany(DocItem.class, "Parent");
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
}
