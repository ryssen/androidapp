package com.example.eandreje.androidapp;

import java.util.ArrayList;

public class ListItem {
    String id;
    private final ArrayList<DocItem> docContainer = new ArrayList<DocItem>();

    public ListItem( ){
        super();
    }

    public ListItem(String name){
        super();
        this.id = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addToDocList(DocItem doc){
          docContainer.add(doc);
    }

    public ArrayList<DocItem> getDocContainer() {
        return docContainer;
    }

    @Override
    public String toString() {
        return this.id;
    }
}
