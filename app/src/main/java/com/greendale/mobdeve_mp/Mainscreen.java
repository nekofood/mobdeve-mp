package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class Mainscreen extends AppCompatActivity {
    FrameLayout slideMenu;
    ConstraintLayout graphicIndicator;
    ImageButton fightButton, foodButton, waterButton, careButton;
    ImageView bowl, container,heartIndicator;
    Boolean foodToggle,waterToggle,careToggle;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        slideMenu = (FrameLayout) findViewById(R.id.slideMenu);
        graphicIndicator = (ConstraintLayout) findViewById(R.id.graphicIndicator);
        fightButton = (ImageButton) findViewById(R.id.fightButton);
        foodButton = (ImageButton) findViewById(R.id.foodButton);
        waterButton = (ImageButton) findViewById(R.id.waterButton);
        careButton = (ImageButton) findViewById(R.id.careButton);
        bowl = (ImageView) findViewById(R.id.bowl);
        container = (ImageView) findViewById(R.id.container);
        heartIndicator = (ImageView) findViewById(R.id.heartIndicator);
        slideMenu.setVisibility(View.GONE);
        graphicIndicator.setVisibility(View.GONE);
        foodToggle = false;
        waterToggle = false;
        careToggle = false;
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
    public void GoToShop(View v){
        Intent intent = new Intent(Mainscreen.this, ShopActivity.class);
        startActivity(intent);
    }
    public void giveFood(View v){
        if (!foodToggle){
            foodToggle = true;
            graphicIndicator.setVisibility(View.VISIBLE);
            if (true) //TODO: replace this with sharedpreference food variable
            {
                bowl.setImageResource(R.drawable.foodbowlempty);
                container.setImageResource(R.drawable.megabites);
            }
            else
            {
                bowl.setImageResource(R.drawable.foodbowlfull);
            }
            careButton.setVisibility(View.INVISIBLE);
            waterButton.setVisibility(View.INVISIBLE);
        }
        else{
            foodToggle = false;
            careButton.setVisibility(View.VISIBLE);
            waterButton.setVisibility(View.VISIBLE);
            graphicIndicator.setVisibility(View.GONE);
        }
    }
    public void giveWater(View v){
        if (!waterToggle){
            waterToggle = true;
            graphicIndicator.setVisibility(View.VISIBLE);
            if (true) //TODO: replace this with sharedpreference food variable
            {
                bowl.setImageResource(R.drawable.waterbowlempty);
                container.setImageResource(R.drawable.waterpitcher);
            }
            else
            {
                bowl.setImageResource(R.drawable.waterbowlfull);
            }
            careButton.setVisibility(View.INVISIBLE);
            foodButton.setVisibility(View.INVISIBLE);
        }
        else{
            waterToggle = false;
            careButton.setVisibility(View.VISIBLE);
            foodButton.setVisibility(View.VISIBLE);
            graphicIndicator.setVisibility(View.GONE);
        }
    }
    public void giveCare(View v){
        Integer heart = 100; //TODO: GET SHARED PREFERENCE "CARE AMOUNT"
        ViewGroup.LayoutParams heartsize = heartIndicator.getLayoutParams();
        heartsize.height = heart;
        heartsize.width = heart;
        heartIndicator.setLayoutParams(heartsize);
        if (!careToggle){
            careToggle = true;
            heartIndicator.setVisibility(View.VISIBLE);
            waterButton.setVisibility(View.INVISIBLE);
            foodButton.setVisibility(View.INVISIBLE);
        }
        else{
            careToggle = false;
            heartIndicator.setVisibility(View.INVISIBLE);
            foodButton.setVisibility(View.VISIBLE);
            waterButton.setVisibility(View.VISIBLE);
            graphicIndicator.setVisibility(View.GONE);
        }
    }
    }