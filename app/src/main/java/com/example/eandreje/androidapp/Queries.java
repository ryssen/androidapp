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

    static ArrayList<PersonDocItem> getRelation(Person person, DocItem doc)
    {
       return new Select()
               .from(Person.class)
               .innerJoin(PersonDocItem.class).on("Person.id = PersonDocItem.id")
               .where("PersonDocItem.docItem = ?", doc.getId())
               .execute();
    }

    static PersonDocItem getPersDocRelation(int person, DocItem doc)
    {
       return new Select()
               .from(PersonDocItem.class)
               .where("Person = ? and DocItem = ?", person, doc.getId())
               .executeSingle();
    }

    static ArrayList<Columns> getColumnHeaders(DocItem doc){
        return new Select()
                .from(Columns.class)
                .where("Parent = ?", doc.getId())
                .execute();
    }

    static ColumnContent fetchSingleCellData(Person person, Columns activeColumn, DocItem document){
        return new Select()
                .from(ColumnContent.class)
                .where("ParentPerson = ? and ParentColumn = ? and ParentDoc = ?", person.getId(), activeColumn.getId(), document.getId())
                .executeSingle();
    }

    static ArrayList<ColumnContent> fetchColumnCellData(Columns column, DocItem document){
        return new Select()
                .from(ColumnContent.class)
                .where("ParentColumn = ? and ParentDoc = ?", column.getId(), document.getId())
                .execute();
    }
    static ArrayList<PersonDocItem> getPersonCell(DocItem doc)
    {
        return new Select()
                .from(PersonDocItem.class)
                .where("DocItem = ?", doc.getId())
                .execute();
    }

    static ArrayList<PersonDocItem> getAllActPersons(DocItem doc)
    {
        return new Select()
                .from(PersonDocItem.class)
                .where("DocItem = ?", doc.getId())
                .execute();
    }
}
