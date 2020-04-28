package com.example.iskchat;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class SimpleBlog extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
