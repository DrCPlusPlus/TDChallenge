package com.example.alexis.tdmoneyed;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

/**
 * This class is used to keep the homescreen widget up to date
 * Will contact the server for updated transactions
 */

public class TDWidgetUpdater extends TimerTask{
	private String budgetFile = "budgetFile.bin";
	private Budget budget;

	private RemoteViews remoteViews;
	private AppWidgetManager appWidgetManager;
	private ComponentName thisWidget;
	private Context context;

	private int[] appWidgetIds;
	private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");

	public TDWidgetUpdater(Context context, AppWidgetManager appWidgetManager, int[] ids){
		this.appWidgetManager = appWidgetManager;
		remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
		thisWidget = new ComponentName(context, AppWidget.class);
		this.context = context;
		this.appWidgetIds = ids;
	}

	private void loadBudgetFromFile(){
		budget = new Budget();
		try {
			ObjectInputStream getBudget = new ObjectInputStream(context.openFileInput(budgetFile));
			budget = (Budget)getBudget.readObject();
			getBudget.close();
		} catch (FileNotFoundException ex){
			ex.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		} catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void run(){
		Log.e("Run", "Triggered! " + Calendar.getInstance().getTime().toString());
		//contact server
		//get transactions
		//get budget from persisted memory
		loadBudgetFromFile();
		//update budget
		remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);

		new ServerSync(context,null).execute();

		//update widget
		String budgeted = "";
		String saveGoal = "";
		String spent = "";
		String saveActual = "";

		if(budget != null) {
			budgeted = Utils.getDoubleAsCurrency(budget.getBudgeted());
			saveGoal = Utils.getDoubleAsCurrency(budget.getSaveGoal());
			spent = Utils.getDoubleAsCurrency(budget.getSpent());
			saveActual = Utils.getDoubleAsCurrency(budget.getSaveActual());
		}


		String now = formatter.format(new Date());
		// Launch summary activity on click
		Intent intent = new Intent(context, SummaryActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

		remoteViews.setTextViewText(R.id.budgeted, budgeted);
		remoteViews.setTextViewText(R.id.savings_goal, saveGoal);
		remoteViews.setTextViewText(R.id.spent, spent);
		remoteViews.setTextViewText(R.id.actual_savings, now);
		remoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);


		// Tell AppWidgetManager to update current app widget
		if (appWidgetManager != null)
			appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

	}
}
