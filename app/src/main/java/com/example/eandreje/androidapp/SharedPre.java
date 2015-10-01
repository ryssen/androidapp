package com.example.eandreje.androidapp;
import android.content.SharedPreferences;
import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johaerik on 2015-09-30.
 */
public class SharedPre
{
    SharedPreferences data;
    List<ListItem> tempList;

         public void saveToSharedPref(Context context, List<ListItem> list)
        {

            data =  context.getSharedPreferences("activities", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = data.edit();
            editor.clear();
            Gson gson = new Gson();
            for(int i=0; i<list.size(); i++)
            {
              String json = gson.toJson(list.get(i));
              editor.putString("Aktiviteter" + i, json);
              editor.putInt("size", i);

            }

         editor.commit();

    }
    public void  loadFromSharedPref(Context context) {

        data = context.getSharedPreferences("activities", Context.MODE_PRIVATE);
        int size = data.getInt("size", -1);
        tempList = new ArrayList<ListItem>();
        for(int i =0; i<=size; i++)
        {

            String json = data.getString("Aktiviteter" + i, "");
            Gson gson = new Gson();
            ListItem a = gson.fromJson(json, ListItem.class);
            //Toast.makeText(this.context, a.getName(), Toast.LENGTH_SHORT).show();
            tempList.add(a);
        }

    }

}
