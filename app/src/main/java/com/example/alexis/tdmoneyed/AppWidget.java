package com.example.alexis.tdmoneyed;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import android.widget.RemoteViews;
import android.widget.TextView;
/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider implements Serializable{
    private Budget budget;
    private String budgetFile = "budgetFile.bin";
    private Context context = App.getAppContext();
    String budgeted;
    String saveGoal;
    String spent;
    String saveActual;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        try {
            ObjectInputStream getBudget = new ObjectInputStream(context.openFileInput(budgetFile));
            budget = (Budget)getBudget.readObject();
            if(budget == null)
                budget = new Budget();
            getBudget.close();
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        } catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }

        //final int N = appWidgetIds.length;

        if(budget != null) {
            budgeted = Double.toString(budget.getBudgeted());
            saveGoal = Double.toString(budget.getSaveGoal());
            spent = Double.toString(budget.getSpent());
            saveActual = Double.toString(budget.getSaveActual());
        }

        // Perform this loop procedure for each App Widget that belongs to this provider
        //for (int i=0; i<N; i++) {
        //    int appWidgetId = appWidgetIds[i];
        // Launch summary activity on click
        Intent intent = new Intent(context, SummaryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        views.setTextViewText(R.id.budgeted, budgeted);
        views.setTextViewText(R.id.savings_goal, saveGoal);
        views.setTextViewText(R.id.spent, spent);
        views.setTextViewText(R.id.actual_savings, saveActual);
        views.setOnClickPendingIntent(R.id.widget, pendingIntent);
        // Tell AppWidgetManager to update current app widget
        appWidgetManager.updateAppWidget(appWidgetIds, views);
        //}
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

