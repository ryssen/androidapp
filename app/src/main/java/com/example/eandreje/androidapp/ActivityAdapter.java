//package com.example.eandreje.androidapp;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//public class ActivityAdapter extends BaseAdapter{
//    String[] activity;
//    Context ctxt;
//    LayoutInflater layoutInflater;
//
//    public ActivityAdapter(String[] arr, Context c){
//        activity = arr;
//        ctxt = c;
//        layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public int getCount() {
//        return activity.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return activity[position];
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        //Create the cell(view) and populate it with
//        //an element of the array
//        return null;
//    }
//}
