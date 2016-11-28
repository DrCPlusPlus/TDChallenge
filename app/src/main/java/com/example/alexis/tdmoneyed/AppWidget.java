package com.example.alexis.tdmoneyed;


import android.app.ApplicationErrorReport;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Timer;
/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider implements Serializable{
	Timer timer;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

		Log.e("updateAppWidget", "updateAppWidget called" + Calendar.getInstance().getTime().toString());
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
        Log.e("onUpdate", "onUpdate called" + Calendar.getInstance().getTime().toString());
		timer = new Timer();
		timer.scheduleAtFixedRate(new TDWidgetUpdater(context, appWidgetManager, appWidgetIds), 1, 5000);

//		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
//
//		new ServerSync(context, null, remoteViews).execute();
//
//		// Tell AppWidgetManager to update current app widget
//		if (appWidgetManager != null)
//			appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.e("onEnabled", "onEnabled called"+ Calendar.getInstance().getTime().toString());
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.e("onDisabled", "onDisabled called"+ Calendar.getInstance().getTime().toString());
    }
}

