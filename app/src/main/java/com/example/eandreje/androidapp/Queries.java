package com.example.eandreje.androidapp;

import com.activeandroid.query.Select;

import java.util.ArrayList;

public class Queries {
    static ArrayList<ListItem> getActivites(){
        return new Select()
                .from(ListItem.class)
                .orderBy("Name ASC")
                .execute();
    }

    static ArrayList<DocItem> getDocuments(ListItem parent){
        return new Select()
                .from(DocItem.class)
                .where("Parent = ?", parent.getId())
                .orderBy("Name ASC")
                .execute();
    }
}
