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
import android.support.test.espresso.core.deps.dagger.internal.DoubleCheckLazy;
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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements Serializable {

    private Budget budget;
    private String budgetFile = "budgetFile.bin";
    private Context context = App.getAppContext();
    private TextView income, budgeted, saveGoal, spent, saveActual;

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

        income = (TextView)findViewById(R.id.income);
        budgeted = (TextView)findViewById(R.id.budgeted);
        saveGoal = (TextView)findViewById(R.id.savings_goal);
        spent = (TextView)findViewById(R.id.spent);
        saveActual = (TextView)findViewById(R.id.actual_savings);
        if(budget != null) {;

            income.setText(Double.toString(budget.getIncome()));
            budgeted.setText(Double.toString(budget.getBudgeted()));
            saveGoal.setText(Double.toString(budget.getSaveGoal()));
            spent.setText(Double.toString(budget.getSpent()));
            saveActual.setText(Double.toString(budget.getSaveActual()));

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
