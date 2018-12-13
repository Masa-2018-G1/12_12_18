package com.sheygam.masa_2018_g1_12_12_18;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StoreProvider.getInstance().setContext(this);
    }
}
