package com.example.alexis.tdmoneyed;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.io.Serializable;
import java.util.Timer;
/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider implements Serializable{

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
       	Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TDWidgetUpdater(context, appWidgetManager), 1, 5000);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

