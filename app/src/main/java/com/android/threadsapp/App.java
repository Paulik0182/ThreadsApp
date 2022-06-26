package com.android.threadsapp;


import android.app.Application;

public class App extends Application {

    private final MySingleton mySingleton = new MySingleton();

    public MySingleton getSingleton() {
        return mySingleton;
    }
}
