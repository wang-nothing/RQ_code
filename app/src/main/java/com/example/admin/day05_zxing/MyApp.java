package com.example.admin.day05_zxing;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
    }
}
