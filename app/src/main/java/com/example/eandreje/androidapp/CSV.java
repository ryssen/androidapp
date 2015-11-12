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
        //toCSV = "Namn;";
        for (Columns c : columnList)
        {
            //toCSV = toCSV + c.toString()+";";
            toCSVstringbuilder.append(c.toString() +";");

        }
        //toCSV = toCSV + "\n";
        toCSVstringbuilder.append("\n");
        for(Person p : personList)
        {
            columncontent = new ColumnContent();
            //toCSV = toCSV + p.toString() +";";
            toCSVstringbuilder.append(p.toString() + ";");
            for (Columns c : columnList)
            {
                columncontent = Queries.fetchCellValueForCSV(c,p);
                toCSVstringbuilder.append(columncontent.toString() +";");
                //toCSV = toCSV + columncontent.toString()+";";
            }
            toCSVstringbuilder.append("\n");
            //toCSV = toCSV + "\n";
        }
        toCSV = toCSVstringbuilder.toString();
        return toCSV;
    }
//    public void readFromCSV(Uri uri, DocItem doc, Context context)
//    {
//        BufferedReader buffer;
//        FileOutputStream fos;
//        try
//        {
//            buffer = new BufferedReader(new InputStreamReader(context.getContentResolver().openInputStream(uri)));
//            String line="";
//            int count =0;
//            while((line  = buffer.readLine()) !=null)
//            {
//                String[] str = line.split(";");
//                String temp = str[count].toString();
//                //Columns col = new Columns(temp, doc,);
//            }
//
//
//        }
//
//
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//    public void fromXlsxToCSV(File file, DocItem doc)
//    {
//        StringBuffer cellvalue = new StringBuffer();
//
//        try
//        {
//            FileOutputStream fos = new FileOutputStream(file);
//            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
//            XSSFSheet sheet = workbook.getSheetAt(0);
//
//            Row row;
//            Cell cell;
//            Iterator<Row> rowIterator = sheet.iterator();
//
//        }
//        catch (Exception e)
//        {
//
//        }
//
//    }
    public String getCSV()
    {
        return toCSV;
    }
}
