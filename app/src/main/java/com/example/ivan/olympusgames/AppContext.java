package com.example.ivan.olympusgames;

import android.app.Application;
import android.content.Context;

/**
 * Created by Fernando on 22/06/2017.
 */

public class AppContext extends Application {
    private static AppContext instance;

    public static AppContext getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
