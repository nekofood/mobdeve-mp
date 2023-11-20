package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    ImageView bowl, container,heartIndicator, bytePet;
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
        bytePet = (ImageView) findViewById(R.id.bytePet);
        bowl = (ImageView) findViewById(R.id.bowl);
        container = (ImageView) findViewById(R.id.container);
        heartIndicator = (ImageView) findViewById(R.id.heartIndicator);
        slideMenu.setVisibility(View.GONE);
        graphicIndicator.setVisibility(View.GONE);
        foodToggle = false;
        waterToggle = false;
        careToggle = false;
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        String Species = sharedPreferences.getString("BSPECIES", "Birthday Bear");
        if (Species.equals("Birthday Bear")){
                bytePet.setImageResource(R.drawable.birthdaybear);
        } else if (Species.equals("PenguRanger")) {
            bytePet.setImageResource(R.drawable.penguranger);
        }
        else bytePet.setImageResource(R.drawable.salacommander);
        if ((sharedPreferences.getInt("BCHUNGER", 0) == 1) && (sharedPreferences.getInt("BCTHIRST ", 1)==0)){
            bytePet.setVisibility(View.INVISIBLE);
        }
        else bytePet.setVisibility(View.VISIBLE);
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
    public void GoToSettings(View v){
        Intent intent = new Intent(Mainscreen.this, Settings.class);
        startActivity(intent);
    }
    public void giveFood(View v){
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        if (!foodToggle){
            foodToggle = true;
            graphicIndicator.setVisibility(View.VISIBLE);
            if (sharedPreferences.getInt("BCHUNGER", 0) < sharedPreferences.getInt("BMHUNGER", 0)) //TODO: replace this with sharedpreference food variable
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
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        if (!waterToggle){
            waterToggle = true;
            graphicIndicator.setVisibility(View.VISIBLE);
            if (sharedPreferences.getInt("BCTHIRST", 0) < sharedPreferences.getInt("BMTHIRST", 0)) //TODO: replace this with sharedpreference food variable
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
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        Integer heart = sharedPreferences.getInt("BCLOVE", 0); //get BCLOVE
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