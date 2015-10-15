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
}
