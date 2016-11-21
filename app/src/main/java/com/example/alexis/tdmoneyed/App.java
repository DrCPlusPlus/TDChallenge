package com.example.alexis.tdmoneyed;

import android.app.Application;
import android.content.Context;

/**
 * Global class needed to insure context not lost when
 * page view fragments dropped before onPause.
 * Created by Alexis on 11/17/2016.
 */

public class App extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return App.context;
    }
}
