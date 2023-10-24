package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class Mainscreen extends AppCompatActivity {
    FrameLayout slideMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        slideMenu = findViewById(R.id.slideMenu);
        BottomSheetBehavior.from(slideMenu).setState(BottomSheetBehavior.STATE_HIDDEN);

        }
    public void OpenSesame(View v)
    {
        BottomSheetBehavior.from(slideMenu).setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    public void CloseSesame(View v)
    {
        BottomSheetBehavior.from(slideMenu).setState(BottomSheetBehavior.STATE_HIDDEN);
    }
    }