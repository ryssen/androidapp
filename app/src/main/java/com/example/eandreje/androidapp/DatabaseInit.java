package com.example.eandreje.androidapp;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

public class DatabaseInit extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        ActiveAndroid.getDatabase().setForeignKeyConstraintsEnabled(true);

    }


}
