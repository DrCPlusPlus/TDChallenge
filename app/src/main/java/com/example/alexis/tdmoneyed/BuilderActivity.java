package com.example.alexis.tdmoneyed;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class BuilderActivity extends AppCompatActivity {

    public static Budget budget;
    private String budgetFile = "budgetFile.bin";
    private BuilderActivity build;
    private PagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);
        build = this;

        // Toolbar back button
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Budget Manager");
        getSupportActionBar().setIcon(R.drawable.td_shield);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				getCurrentFocus().clearFocus();
                Intent i = new Intent(build, SummaryActivity.class);
                startActivity(i);
            }
        });

        // populate application object
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_builder, menu);
        return true;
    }

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

    static public Budget getBudget(){
        return budget;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save application object
        try {
            //budget = (Budget)getApplication();
            ObjectOutputStream setBudget = new ObjectOutputStream(openFileOutput(budgetFile,MODE_PRIVATE));
            setBudget.writeObject(budget);
            setBudget.close();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
