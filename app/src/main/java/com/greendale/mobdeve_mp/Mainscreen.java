package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class Mainscreen extends AppCompatActivity {
    FrameLayout slideMenu;
    ImageButton fightButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        slideMenu = findViewById(R.id.slideMenu);
        fightButton = findViewById(R.id.fightButton);
        slideMenu.setVisibility(View.GONE);
        }
    public void OpenSesame(View v)
    {
        slideMenu.setVisibility(View.VISIBLE);
    }
    public void CloseSesame(View v)
    {
       slideMenu.setVisibility(View.GONE);
    }
    public void Battle(View v){
        Intent intent = new Intent(Mainscreen.this, Battle.class);
        startActivity(intent);
    }
    }