package com.example.alexis.tdmoneyed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.alexis.tdmoneyed.R.attr.layoutManager;

public class SummaryActivity extends AppCompatActivity implements Serializable {
    private Budget budget;
    private String budgetFile = "budgetFile.bin";
    private Context context = App.getAppContext();
    private static Double amountBudgeted = 0.00;
    Double savingsGoal = 0.00;
    private TableLayout table;
    private TableRow trHeader, tr;
    private TextView label, value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // set toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Budget Summary");
        getSupportActionBar().setIcon(R.drawable.td_shield);

        try{
            ObjectInputStream getBudget = new ObjectInputStream(openFileInput(budgetFile));
            budget = (Budget)getBudget.readObject();
            if(budget == null)
                budget = new Budget();

            getBudget.close();

        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }

        table = (TableLayout)findViewById(R.id.summary_table);
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        trHeader = (TableRow)findViewById(R.id.table_row);
        tr = (TableRow)findViewById(R.id.table_row);
        amountBudgeted = 0.0; // reset

        if(budget != null) {
            createTable("College", budget.getCollege());
            createTable("Debt", budget.getDebt());
            createTable("Entertainment", budget.getEntertain());
            createTable("Food", budget.getFood());
            createTable("Personal", budget.getPersonal());
            createTable("Pets", budget.getPets());
            createTable("Transportation", budget.getTransport());
            createTableSummary("Summary", budget.getTotals());
            budget.setBudgeted(amountBudgeted);
            budget.setSaveGoal(savingsGoal);
        }
    }

    public void createTable(String header, ArrayList<ListItem> list){
        if(list != null && list.size() > 0){
			double listAmount = 0.0;
            for(ListItem li: list)
				listAmount += li.getAmount();
			if (listAmount < 0.001)		// if the list contains no items with a value then don't display it in the summary
				return;
            trHeader = new TableRow(this);
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams
                    (TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            tableRowParams.setMargins(0, 50, 0, 15);
            //tableRowParams
            trHeader.setLayoutParams(tableRowParams);
            label = new TextView(this);
            label.setText(header);
            label.setTextSize(20);
            label.setPadding(60, 20, 20, 20);
            label.setTextColor(ContextCompat.getColor(context, R.color.TdDarkGreen));
            value = new TextView(this);
            value.setText("");
            trHeader.addView(label);
            trHeader.addView(value);
            trHeader.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRow));
            if (budget.isCategoryOverBudget(header)) {
				trHeader.setBackgroundColor(Color.RED);
				label.setTextColor(Color.WHITE);
			}
            table.addView(trHeader);
            // data
            for(int idx = 0; idx < list.size(); ++idx){
                if(list.get(idx).getAmount() > 0.00) {
                    tr = new TableRow(this);
                    label = new TextView(this);
                    label.setText(list.get(idx).getItem_name());
                    label.setTextSize(17);
                    label.setPadding(60, 10, 10, 10);
                    label.setTextColor(ContextCompat.getColor(context, R.color.content));
                    value = new TextView(this);
                    value.setText(Utils.getDoubleAsCurrency(list.get(idx).getAmount()));
                    amountBudgeted =(amountBudgeted + list.get(idx).getAmount());
                    value.setTextSize(17);
                    value.setPadding(60, 10, 10, 10);
                    value.setTextColor(ContextCompat.getColor(context, R.color.content));
                    tr.addView(label);
                    tr.addView(value);
                    //tr.setPadding(0, 15, 0, 15);
                    table.addView(tr);
                }
            }
        }
    }

    public void createTableSummary(String header, ArrayList<ListItem> list){
        trHeader = new TableRow(this);
        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams
                (TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tableRowParams.setMargins(0, 50, 0, 15);
        trHeader.setLayoutParams(tableRowParams);
        label = new TextView(this);
        label.setText("Summary");
        label.setTextSize(20);
        label.setPadding(60, 20, 20, 20);
        label.setTextColor(Color.parseColor("#FFFFFF"));
        value = new TextView(this);
        value.setText("");
        trHeader.addView(label);
        trHeader.addView(value);
        trHeader.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRowDark));
        table.addView(trHeader);

        tr = new TableRow(this);
        label = new TextView(this);
        label.setText("Total Income");
        label.setTextSize(17);
        label.setPadding(60, 10, 10, 10);
        label.setTextColor(ContextCompat.getColor(context, R.color.content));
        value = new TextView(this);
        value.setText(Utils.getDoubleAsCurrency(budget.getIncome()));
        value.setTextSize(17);
        value.setPadding(60, 10, 10, 10);
        value.setTextColor(ContextCompat.getColor(context, R.color.content));
        tr.addView(label);
        tr.addView(value);
        table.addView(tr);

        tr = new TableRow(this);
        label = new TextView(this);
        label.setText("Amount Budgeted");
        label.setTextSize(17);
        label.setPadding(60, 10, 10, 10);
        label.setTextColor(ContextCompat.getColor(context, R.color.content));
        value = new TextView(this);
        value.setText(Utils.getDoubleAsCurrency(amountBudgeted));
        value.setTextSize(17);
        value.setPadding(60, 10, 10, 10);
        value.setTextColor(ContextCompat.getColor(context, R.color.content));
        tr.addView(label);
        tr.addView(value);
        table.addView(tr);

        tr = new TableRow(this);
        label = new TextView(this);
        label.setText("Savings Goal");
        label.setTextSize(17);
        label.setPadding(60, 10, 10, 10);
        label.setTextColor(ContextCompat.getColor(context, R.color.content));
        value = new TextView(this);
        Double income = budget.getIncome();
        savingsGoal = (income - amountBudgeted);
        value.setText(Utils.getDoubleAsCurrency(savingsGoal));
        value.setTextSize(17);
        value.setPadding(60, 10, 10, 10);
        value.setTextColor(ContextCompat.getColor(context, R.color.content));
        tr.addView(label);
        tr.addView(value);
        table.addView(tr);
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
        if (id == R.id.action_settings) {
            i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_help) {
            i = new Intent(this, HelpActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_tutorial) {
            i = new Intent(this, TDMoneyEdIntro.class);
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
}
