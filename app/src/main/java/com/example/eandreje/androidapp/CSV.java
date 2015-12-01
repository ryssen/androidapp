package com.example.eandreje.androidapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * Created by johaerik on 2015-10-29.
 */
public class CSV
{
    private ColumnContent columncontent;
    private List<Columns> columnList = new ArrayList<>();
    private List<Person> personList = new ArrayList<>();
    private String toCSV;

    public String writeToCSV(DocItem doc)
    {

        columnList = Queries.getColumnHeaders(doc);
        personList = Queries.getRelation(doc);
        StringBuilder toCSVstringbuilder = new StringBuilder("Name;");
        //      TODO    Snygga till parsning med en stringbuilder ist flr en String

        for (Columns c : columnList)
        {

            toCSVstringbuilder.append(c.toString() +";");

        }

        toCSVstringbuilder.append("\n");
        for(Person p : personList)
        {
            columncontent = new ColumnContent();

            toCSVstringbuilder.append(p.toString() + ";");
            for (Columns c : columnList)
            {
                columncontent = Queries.fetchCellValueForCSV(c,p);
                if (columncontent != null)
                {
                    if (columncontent.equals("true"))
                    {
                        columncontent.value = "Ja";
                    }
                    else if (columncontent.equals("false"))
                    {
                        columncontent.value = "Nej";
                    }

                    toCSVstringbuilder.append(columncontent.value.toString() + ";");
                }
                else
                    toCSVstringbuilder.append(";");

            }
            toCSVstringbuilder.append("\n");

        }
        toCSV = toCSVstringbuilder.toString();
        return toCSV;
    }

}
