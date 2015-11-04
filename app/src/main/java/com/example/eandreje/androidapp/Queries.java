package com.example.eandreje.androidapp;


import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

public class Queries {
    static List<ListItem> getActivites(){
        return new Select()
                .from(ListItem.class)
                .orderBy("Name ASC")
                .execute();
    }

    static List<DocItem> getDocuments(ListItem parent){
        return new Select()
                .from(DocItem.class)
                .where("Parent = ?", parent.getId())
                .orderBy("Name ASC")
                .execute();
    }
    static List<DocItem> getAdapterObjects(ListItem parent){
        return new Select()
                .from(Person.class)
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
    static List<Person> getRelation(DocItem doc)
    {
       return new Select()
               .from(Person.class)
               .innerJoin(PersonDocItem.class).on("Person.id = Person")
               .where("PersonDocItem.DocItem = ?", doc.getId())
               .execute();
    }

    static PersonDocItem getPersDocRelation(int person, DocItem doc)
    {
       return new Select()
               .from(PersonDocItem.class)
               .where("Person = ? and DocItem = ?", person, doc.getId())
               .executeSingle();
    }

    static List<Columns> getColumnHeaders(DocItem doc){
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

    static List<ColumnContent> fetchColumnCellData(Columns column, DocItem document){
        return new Select()
                .from(ColumnContent.class)
                .where("ParentColumn = ? and ParentDoc = ?", column.getId(), document.getId())
                .execute();
    }
    static List<PersonDocItem> getPersonCell(DocItem doc)
    {
        return new Select()
                .from(PersonDocItem.class)
                .where("DocItem = ?", doc.getId())
                .execute();
    }
    static ColumnContent fetchCellValueForCSV(Columns column, Person person)
    {
        return new Select()
                .from(ColumnContent.class)
                .where("ParentColumn = ? and ParentPerson = ?", column.getId(), person.getId())
        .executeSingle();
        //"ParentColumn = ?", column.getId(),
    }


    static List<PersonDocItem> getAllActPersons(DocItem doc)
    {
        return new Select()
                .from(PersonDocItem.class)
                .where("DocItem = ?", doc.getId())
                .execute();
    }
}
