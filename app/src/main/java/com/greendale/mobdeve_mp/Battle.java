package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Battle extends AppCompatActivity {
    ImageButton pauseButton;
    FrameLayout battlePopup;
    ImageButton exitMatchButton;
    ImageButton resumeMatchButton;
    ImageView bytecharacter;
    ImageView enemybyte;
    TextView sharedpreftesttwo;
    Boolean canFight = true;
    int enemyHP = (int)(Math.random() * (500-400+1)+400);
    int enemyAttack = (int)(Math.random() * 3 + 1);
    int enemyspecies = (int)(Math.random() * 3 + 1);

    ProgressBar enemyBar;

    BattleView bv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        bv = new BattleView(this,this);
        setContentView(R.layout.activity_battle);
        sharedpreftesttwo = (TextView) findViewById(R.id.sharedpreftesttwo);
        battlePopup.setVisibility(View.GONE);
        pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        battlePopup = (FrameLayout) findViewById(R.id.battlePopup);
        exitMatchButton = (ImageButton) findViewById(R.id.battleExitMatchButton);
        resumeMatchButton = (ImageButton) findViewById(R.id.battleResumeMatchButton);
        bytecharacter = (ImageView) findViewById(R.id.bytecharacter);
        enemybyte = (ImageView) findViewById(R.id.enemybyte);
        enemyBar = (ProgressBar) findViewById(R.id.enemyHP);
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        String Species = sharedPreferences.getString("BSPECIES", "Birthday Bear");
        sharedpreftesttwo.setText(Species);
        switch (Species) {
            case "Birthday Bear":
                bytecharacter.setImageResource(R.drawable.birthdaybear);
                break;
            case "PenguRanger":
                bytecharacter.setImageResource(R.drawable.penguranger);
                break;
            case "Salacommander":
                bytecharacter.setImageResource(R.drawable.salacommander);
                break;
        }
        GenerateRandomEnemy();
    }
    public void Pause(View view) {
        battlePopup.setVisibility(View.VISIBLE);canFight=false;
    }
    public void Resume(View view) {
        battlePopup.setVisibility(View.GONE);canFight=false;
    }
    public void Exit(View view){
        finish();
    }

    public void attack(){enemyHP--;}

    public void GenerateRandomEnemy(){
        //create random enemy
        switch (enemyspecies) {
            case 1:
                enemybyte.setImageResource(R.drawable.birthdaybear);
                break;
            case 2:
                enemybyte.setImageResource(R.drawable.penguranger);
                break;
            case 3:
                enemybyte.setImageResource(R.drawable.salacommander);
                break;
        }

    }
}