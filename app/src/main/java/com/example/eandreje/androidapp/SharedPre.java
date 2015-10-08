package com.example.eandreje.androidapp;
import android.content.SharedPreferences;
import android.content.Context;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by johaerik on 2015-09-30.
 */
public class SharedPre
{
    SharedPreferences data;
    List<ListItem> tempList;
    List<DocItem> tempListDoc;
    List<DocItem> secTemp;

//Here are the methods for saving, loading, editing, adding and removing Listitems
    public void saveListItem(Context context,  List<ListItem> list)
    {
            data =  context.getSharedPreferences("ListItem", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = data.edit();
            Gson gson = new Gson();
            JsonArray ObjArray = gson.toJsonTree(list).getAsJsonArray();
            editor.putString("Aktiviteter", ObjArray.toString());
            editor.commit();

    }

    public void loadListItem(Context context ) {

        data = context.getSharedPreferences("ListItem", Context.MODE_PRIVATE);
        tempList = new ArrayList<ListItem>();
        String ObjArray = data.getString("Aktiviteter", "");
        Type type = new TypeToken<ArrayList<ListItem>>(){}.getType();
        Gson gson = new Gson();
        tempList = gson.fromJson(ObjArray, type);

        if(tempList == null)
                tempList = new ArrayList<>();
    }

    public void editListItem(Context context, String changeName, ListItem editItem)
    {
        data = context.getSharedPreferences("ListItem", Context.MODE_PRIVATE);
        tempList = new ArrayList<ListItem>();
        String ObjArray = data.getString("Aktiviteter", "");
        Type type = new TypeToken<ArrayList<ListItem>>(){}.getType();
        Gson gson = new Gson();
        tempList = gson.fromJson(ObjArray, type);
        for(int i =0; i< tempList.size();i++ )
        {
            if(tempList.get(i).getId()==(editItem.getId()))
            {
                Toast.makeText(context, tempList.get(i).getName()+" "+editItem.getName(), Toast.LENGTH_SHORT).show();
                tempList.get(i).setName(changeName);

            }
        }

    }

    public void addListItem(Context context, ListItem addListItem)
    {
        data = context.getSharedPreferences("ListItem", Context.MODE_PRIVATE);
        tempList = new ArrayList<ListItem>();
        String ObjArray = data.getString("Aktiviteter", "");
        Type type = new TypeToken<ArrayList<ListItem>>(){}.getType();
        Gson gson = new Gson();
        //  TODO    Kolla om ObjArray är tom eller inte
        tempList = gson.fromJson(ObjArray, type);
        if(tempList == null)
            tempList = new ArrayList<>();
        tempList.add(addListItem);


    }

    public void removeListItem(Context context,  ListItem removeItem)
    {
        data = context.getSharedPreferences("ListItem", Context.MODE_PRIVATE);
        tempList = new ArrayList<ListItem>();
        String ObjArray = data.getString("Aktiviteter", "");
        Type type = new TypeToken<ArrayList<ListItem>>(){}.getType();
        Gson gson = new Gson();
        tempList = gson.fromJson(ObjArray, type);

        for(int i =0; i< tempList.size();i++ )
        {
            if(tempList.get(i).getId()==(removeItem.getId()))
            {
                Toast.makeText(context, tempList.get(i).getId()+" "+removeItem.getId(), Toast.LENGTH_SHORT).show();
                tempList.remove(i);
            }
        }
    }


    //Here are the methods for saving, loading, editing, adding and removing DocItems
    public void saveDocItem(Context context, List<DocItem> list)
    {
        data = context.getSharedPreferences("DocItems", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        Gson gson = new Gson();
        JsonArray ObjArray = gson.toJsonTree(list).getAsJsonArray();
        editor.putString("DokumentNamn", ObjArray.toString());
        editor.commit();
    }
    public void loadDocItem(Context context, int key)
    {
        data = context.getSharedPreferences("DocItems", Context.MODE_APPEND);
        tempListDoc = new ArrayList<DocItem>();
        String ObjArray = data.getString("DokumentNamn", "");
        Type type = new TypeToken<ArrayList<DocItem>>(){}.getType();
        Gson gson = new Gson();
        tempListDoc = gson.fromJson(ObjArray, type);
        if(tempListDoc == null)
            secTemp = new ArrayList<>();
        else
        {
            int j = tempListDoc.size();
            secTemp = new ArrayList<DocItem>();
         for(int i=0; i<j; i++)
         {
             if(tempListDoc.get(i).getParentId() == key)
             {
                 secTemp.add(tempListDoc.get(i));
             }
         }
        }
    }

    public void editDocItem()
    {

    }

    public void addDocItem(Context context, DocItem addDocItem)
    {
        data = context.getSharedPreferences("DocItems", Context.MODE_PRIVATE);
        tempListDoc = new ArrayList<DocItem>();
        String ObjArray = data.getString("DokumentNamn", "");
        Type type = new TypeToken<ArrayList<DocItem>>(){}.getType();
        Gson gson = new Gson();
        //  TODO    Kolla om ObjArray är tom eller inte
        tempListDoc = gson.fromJson(ObjArray, type);
        if(tempListDoc == null)
            tempListDoc = new ArrayList<>();
        tempListDoc.add(addDocItem);
        Toast.makeText(context, tempListDoc.toString(), Toast.LENGTH_SHORT).show();

    }

    public void removeDocItem(Context context, DocItem remDocItem)
    {

    }
    public void removeMultipleDocItems(Context context, int key)
    {
        data = context.getSharedPreferences("DocItems", Context.MODE_APPEND);
        tempListDoc = new ArrayList<DocItem>();
        String ObjArray = data.getString("DokumentNamn", "");
        Type type = new TypeToken<ArrayList<DocItem>>(){}.getType();
        Gson gson = new Gson();
        tempListDoc = gson.fromJson(ObjArray, type);
        if(tempListDoc != null)
        {
            for(int i=0; i<tempListDoc.size(); i++)
            {
                if(tempListDoc.get(i).getParentId() == key)
                {
                    tempListDoc.remove(i);
                    i--;
                }
            }
        }

    }
    public void ListItemID(Context context, int ListItemID)
    {

    }
    public void DocItemID(Context context, int DocItemID)
    {

    }

    public void clearAllone(Context context)
    {
        data = context.getSharedPreferences("ListItem", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.clear();
        editor.commit();

    }
    public void clearAlltwo(Context context)
    {
        data = context.getSharedPreferences("DocItems", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.clear();
        editor.commit();
    }

}
