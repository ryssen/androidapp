package com.example.eandreje.androidapp;

import android.support.annotation.NonNull;

import com.activeandroid.annotation.Column;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by johaerik on 2015-10-29.
 */
public class CSV
{
    private ColumnContent columncontent;
    private List<Columns> columnList = new ArrayList<>();
    private List<Person> personList = new ArrayList<>();
    private String toCSV;

    public void writeToCSV(DocItem doc)
    {

        columnList = Queries.getColumnHeaders(doc);
        personList = Queries.getRelation(doc);
        toCSV = "Namn,";
        for (Columns c : columnList)
        {
            toCSV = toCSV + c.toString()+",";
        }
        toCSV = toCSV + "\n";
        for(Person p : personList)
        {
            columncontent = new ColumnContent();
            toCSV = toCSV + p.toString() +",";
            for (Columns c : columnList)
            {
                columncontent = Queries.fetchCellValueForCSV(c,p);
                toCSV = toCSV + columncontent.toString()+",";
            }
            toCSV = toCSV + "\n";
        }

    }
    public String getCSV()
    {
        return toCSV;
    }
}
