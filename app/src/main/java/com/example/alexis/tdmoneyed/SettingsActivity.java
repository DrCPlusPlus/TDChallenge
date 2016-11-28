package com.example.alexis.tdmoneyed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SettingsActivity extends AppCompatActivity {

    private Settings settings;
    private String settingsFile = "settingsFile.bin";
    private SettingsActivity setAct;
    private Context context = App.getAppContext();
    private TableLayout table;
    private TableRow tr;
    private TextView label;
    private EditText input;
    private int inputId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setAct = this;

        // set toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Financial Education");
        getSupportActionBar().setIcon(R.drawable.td_shield);

        settings = new Settings();
		try {
            ObjectInputStream getSettings = new ObjectInputStream(openFileInput(settingsFile));
            settings = (Settings)getSettings.readObject();
            getSettings.close();
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        } catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(setAct, MainActivity.class);
                startActivity(i);
            }
        });

        table = (TableLayout)findViewById(R.id.settings_table);
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        tr = (TableRow)findViewById(R.id.table_row);

        createInputOption("Guardians Name");
        createInputOption("Guardians Number");
        createInputOption("Youths Name");
        createInputOption("Youths Number");
        createInputOption("Account Number");
    }

    private void createInputOption(String lableText){
        tr = new TableRow(this);
        label = new TextView(this);
        label.setText(lableText);
        label.setTextSize(18);
        label.setTextColor(ContextCompat.getColor(context, R.color.content));
        input = new EditText(this);
        input.setWidth(400);
        input.setTag(inputId);
        ++inputId;
        loadInput(input);
        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Toast.makeText(getApplicationContext(), "Lost focus", Toast.LENGTH_LONG).show();
                    EditText inputValue = (EditText) v;
                    setSettingsValues(inputValue);
                }
            }
        });
        tr.addView(label);
        tr.addView(input);
        table.addView(tr);
    }

    private void setSettingsValues(EditText input){
        int getId = (int)input.getTag();
        switch (getId) {
            case 0:
                settings.setGardiansName(input.getText().toString());
                break;
            case 1:
                settings.setGardiansNumber(input.getText().toString());
                break;
            case 2:
                settings.setYouthsName(input.getText().toString());
                break;
            case 3:
                settings.setYouthsNumber(input.getText().toString());
                break;
            case 4:
                settings.setAccountNumber(input.getText().toString());
                break;
        }
    }

    private void loadInput(EditText input){
        int getId = (int)input.getTag();
        switch (getId) {
            case 0:
                input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(140) });
                if(settings != null) { input.setText(settings.getGardiansName()); }
                break;
            case 1:
                input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(13) });
                if(settings != null) { input.setText(settings.getGardiansNumber()); }
                break;
            case 2:
                input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(140) });
                if(settings != null) { input.setText(settings.getYouthsName()); }
                break;
            case 3:
                input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(13) });
                if(settings != null) { input.setText(settings.getYouthsNumber()); }
                break;
            case 4:
                input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
                if(settings != null) { input.setText(settings.getAccountNumber()); }
                break;
        }
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
            ObjectOutputStream setSettings = new ObjectOutputStream(openFileOutput(settingsFile, MODE_PRIVATE));
            setSettings.writeObject(settings);
            setSettings.close();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}


