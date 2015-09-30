package com.example.eandreje.androidapp;

public class DocItem {
    String id;

    public DocItem(){
    }

    public DocItem(String name){
        id = name;
    }

    @Override
    public String toString() {
        return this.id;
    }
}


