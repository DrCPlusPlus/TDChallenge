package com.example.alexis.tdmoneyed;


import android.content.Context;
import android.content.Intent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.ObjectOutputStream;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Serializable {

    private Budget budget;
    private String budgetFile = "budgetFile.bin";
    private Context context = App.getAppContext();
    private TableLayout table;
    private TableRow tr, tr2;
    private TextView value1,value2, value3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Financial Education");
        getSupportActionBar().setIcon(R.drawable.td_shield);

        try {
            ObjectInputStream getBudget = new ObjectInputStream(openFileInput(budgetFile));
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

        table = (TableLayout)findViewById(R.id.home_summary);
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        table.setColumnStretchable(2, true);
        tr = (TableRow)findViewById(R.id.table_row);
        tr2 = (TableRow)findViewById(R.id.table_row);
        value1 = (TextView)findViewById(R.id.value0);
        value2 = (TextView)findViewById(R.id.value1);
        value3 = (TextView)findViewById(R.id.value2);

        if(budget != null) {
            createHomeTable("Income", "Budget", "Savings Goal",
                    Double.toString(budget.getIncome()),
                    Double.toString(budget.getBudgeted()),
                    Double.toString(budget.getSaveGoal()));
            createHomeTable("", "Spent", "Actual Savings", "",
                    Double.toString(budget.getSpent()),
                    Double.toString(budget.getSaveActual()));
        }
    }

    public void onClickBuild(View view){
        Intent ib = new Intent(this, BuilderActivity.class);
        startActivity(ib);
    }

    public void onClickSummary(View view){
        Intent is = new Intent(this, SummaryActivity.class);
        startActivity(is);
    }

    public void onClickShare(View view){
        // Beam me up Jon
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_builder, menu);
        return true;
    }

    // ToDo: exclude current activity from menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent i;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            i = new Intent(this, MainActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_builder) {
            i = new Intent(this, BuilderActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_summary) {
            i = new Intent(this, SummaryActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_help) {
            i = new Intent(this, HelpActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save application object
        try {
            ObjectOutputStream setBudget = new ObjectOutputStream(openFileOutput(budgetFile, MODE_PRIVATE));
            setBudget.writeObject(budget);
            setBudget.close();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void createHomeTable(String title1, String title2, String title3,
                                   String info1, String info2, String info3){
        tr = new TableRow(this);
        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams
                (TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tableRowParams.setMargins(0, 50, 0, 15);
        //tableRowParams
        tr.setLayoutParams(tableRowParams);
        value1 = new TextView(this);
        value1.setText(title1);
        value1.setTextSize(17);
        value1.setPadding(60, 20, 20, 20);
        value1.setTextColor(ContextCompat.getColor(context, R.color.TdDarkGreen));
        value2 = new TextView(this);
        value2.setText(title2);
        value2.setTextSize(17);
        value2.setPadding(60, 20, 20, 20);
        value2.setTextColor(ContextCompat.getColor(context, R.color.TdDarkGreen));
        value3 = new TextView(this);
        value3.setText(title3);
        value3.setTextSize(17);
        value3.setPadding(60, 20, 20, 20);
        value3.setTextColor(ContextCompat.getColor(context, R.color.TdDarkGreen));
        tr.addView(value1);
        tr.addView(value2);
        tr.addView(value3);
        tr.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRow));
        table.addView(tr);

        tr2 = new TableRow(this);
        value1 = new TextView(this);
        value1.setText(info1);
        value1.setTextSize(17);
        value1.setPadding(60, 20, 20, 20);
        value1.setTextColor(ContextCompat.getColor(context, R.color.TdDarkGreen));
        value2 = new TextView(this);
        value2.setText(info2);
        value2.setTextSize(17);
        value2.setPadding(60, 20, 20, 20);
        value2.setTextColor(ContextCompat.getColor(context, R.color.TdDarkGreen));
        value3 = new TextView(this);
        value3.setText(info3);
        value3.setTextSize(17);
        value3.setPadding(60, 20, 20, 20);
        value3.setTextColor(ContextCompat.getColor(context, R.color.TdDarkGreen));
        tr2.addView(value1);
        tr2.addView(value2);
        tr2.addView(value3);
        tr.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRow));
        table.addView(tr2);
    }
}
