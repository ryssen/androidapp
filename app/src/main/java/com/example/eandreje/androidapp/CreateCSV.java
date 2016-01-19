package com.example.eandreje.androidapp;

import java.util.List;

public class CreateCSV
{

    public String writeToCSV(Event doc)
    {

        List<Columns> columnList = Queries.getColumnHeaders(doc);
        List<Attendant> attendantList = Queries.getRelation(doc);
        StringBuilder toCSVstringbuilder = new StringBuilder("Name;");
        for (Columns c : columnList)
        {
            toCSVstringbuilder.append(c.toString() +";");
        }
        toCSVstringbuilder.append("\n");
        for(Attendant p : attendantList)
        {
            CellValue columncontent;
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
        String toCSV = toCSVstringbuilder.toString();
        return toCSV;
    }

}
