package com.example.eandreje.androidapp;

import com.activeandroid.Model;
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

    static DocItem getDocument(DocItem doc){
        return new Select()
                .from(DocItem.class)
                .where(" = ?", doc.getId())
                .executeSingle();
    }
    static ArrayList<Person> getPersons(ListItem parent){
        return new Select()
                .from(Person.class)
                .where("Parent = ?", parent.getId())
                .execute();
    }
    static ArrayList<Person_DocItem> getRelation(Person person, DocItem docItem)
    {
       return new Select()
               .from(Person.class)
               .innerJoin(Person_DocItem.class).on("Person.id = Person_DocItem.id")
               .where("Person_DocItem.docItem = ?", docItem.getId())
               .execute();
    }


}
