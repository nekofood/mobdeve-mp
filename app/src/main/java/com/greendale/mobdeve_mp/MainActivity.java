package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // if (isQuizDone) {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivity(intent);
        /*else{
        Intent i = new Intent(MainActivity.this, Mainscreen.class);
            startActivity(i);
            finish();
        }
        * */
    }
}