package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    //settings
    public static final Boolean ISQUIZDONE = false;
    public static final Boolean ISNOTIFSON = true;
    //Bytes Current Attributes
    public static final Integer BCHUNGER = 100;
    public static final Integer BCTHIRST = 100;
    public static final Integer BCLOVE = 100;
    //Bytes Max Attribute Levels
    public static final Integer BMHUNGER = 100;
    public static final Integer BMTHIRST = 100;
    public static final Integer BMLOVE = 100;
    //species
    public static final String BSPECIES = "Birthday Bear";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (sharedPreferences.getBoolean("ISQUIZDONE", false)==false) {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        }
        else{
        Intent i = new Intent(MainActivity.this, Mainscreen.class);
            startActivity(i);
            finish();
        }
    }
}