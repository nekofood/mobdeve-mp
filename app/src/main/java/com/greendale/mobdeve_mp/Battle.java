package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
public class Battle extends AppCompatActivity {
    ImageButton pauseButton;
    FrameLayout battlePopup;
    ImageButton exitMatchButton;
    ImageButton resumeMatchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        pauseButton = findViewById(R.id.pauseButton);
        battlePopup = findViewById(R.id.battlePopup);
        exitMatchButton = findViewById(R.id.exitMatchButton);
        resumeMatchButton = findViewById(R.id.resumeMatchButton);
        battlePopup.setVisibility(View.GONE);
    }
    public void Pause(View view) {
        battlePopup.setVisibility(View.VISIBLE);
    }
    public void Resume(View view) {
        battlePopup.setVisibility(View.GONE);
    }
    public void Exit(View view){
        finish();
    }
}