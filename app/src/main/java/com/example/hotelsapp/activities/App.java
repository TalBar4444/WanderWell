package com.example.hotelsapp.activities;

import android.app.Application;

import com.example.hotelsapp.MySignal;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySignal.init(this);
        Imager.initHelper(this);
        ///
    }
}
