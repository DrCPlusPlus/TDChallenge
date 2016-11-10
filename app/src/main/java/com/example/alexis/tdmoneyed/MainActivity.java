package com.example.alexis.tdmoneyed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Financial Education");
        getSupportActionBar().setIcon(R.drawable.td_shield);
    }

    public void onClickBuild(View view){
        Intent i = new Intent(this, BuilderActivity.class);
        startActivity(i);
    }


    @Override
    protected void onPause() {
        super.onPause();

    }
}
