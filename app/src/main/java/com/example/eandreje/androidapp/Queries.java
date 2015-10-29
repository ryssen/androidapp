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

    static DocItem getDocument(DocItem doc){
        return new Select()
                .from(DocItem.class)
                .where(" = ?", doc.getId())
                .executeSingle();
    }
    static Person getPerson(ColumnContent value){
        return new Select()
                .from(Person.class)
                .where("Parent = ?", value.getId())
                .executeSingle();
    }
    static ArrayList<Person_DocItem> getRelation(Person person, DocItem doc)
    {
       return new Select()
               .from(Person.class)
               .innerJoin(Person_DocItem.class).on("Person.id = Person_DocItem.id")
               .where("Person_DocItem.docItem = ?", doc.getId())
               .execute();
    }
    static ArrayList<Columns> getColumnHeaders(DocItem doc){
        return new Select()
                .from(Columns.class)
                .where("Parent = ?", doc.getId())
                .execute();
    }

    static ArrayList<ColumnContent> fetchCellData(DocItem doc){
        return new Select()
                .from(ColumnContent.class)
                .where("ParentDoc = ?", doc.getId())
                .execute();
    }

    static ColumnContent fetchSingleCellData(int index){
        return new Select()
                .from(ColumnContent.class)
                .where("ParentColumn = ?", index)
                .executeSingle();
    }

    static ArrayList<ColumnContent> fetchColumnCellData(Columns column){
        return new Select()
                .from(ColumnContent.class)
                .where("ParentColumn = ?", column.getId())
                .execute();
    }

}
