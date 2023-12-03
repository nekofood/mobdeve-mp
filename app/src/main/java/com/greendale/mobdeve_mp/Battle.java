package com.greendale.mobdeve_mp;

import static android.graphics.Color.argb;
import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Battle extends AppCompatActivity {
    FrameLayout battlePopup;
    ImageButton exitMatchButton,resumeMatchButton,pauseButton,ExtendDrive;
    ImageView bytecharacter,enemybyte,BEDIcon;
    TextView sharedpreftesttwo;
    Boolean canFight = true;
    int enemyHP = (int)(Math.random() * (1000-800+1)+800);
    int enemyAttack = (int)(Math.random() * 3 + 1);
    int chunger, cthirst, clove;
    int byteAttack, attackDelay, byteHP;
    int enemyspecies = (int)(Math.random() * 3 + 1);
    ProgressBar enemyBar, playerBar;
    int ExtendMeter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        sharedpreftesttwo = (TextView) findViewById(R.id.sharedpreftesttwo);
        pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        battlePopup = (FrameLayout) findViewById(R.id.battlePopup);
        battlePopup.setVisibility(View.GONE);
        exitMatchButton = (ImageButton) findViewById(R.id.battleExitMatchButton);
        resumeMatchButton = (ImageButton) findViewById(R.id.battleResumeMatchButton);
        ExtendDrive = (ImageButton) findViewById(R.id.ExtendDrive);
        ExtendDrive.setColorFilter(argb(60,50,50,50));
        bytecharacter = (ImageView) findViewById(R.id.bytecharacter);
        BEDIcon = (ImageView) findViewById(R.id.battleExtDriveIcon);
        enemybyte = (ImageView) findViewById(R.id.enemybyte);
        enemyBar = (ProgressBar) findViewById(R.id.enemyHP);
        playerBar = (ProgressBar) findViewById(R.id.playerHP);
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        String Species = sharedPreferences.getString("BSPECIES", "Birthday Bear");
        sharedpreftesttwo.setText(Species);
        byteHP = sharedPreferences.getInt("BCHUNGER", 100);
        attackDelay = sharedPreferences.getInt("BCTHURST", 100);
        byteAttack = sharedPreferences.getInt("BCLOVE", 100);
        byteAttack = byteAttack/5;
        boolean hasFoodBoost = sharedPreferences.getBoolean("HAS_FOODBOOST", false);
        boolean hasWaterBoost = sharedPreferences.getBoolean("HAS_WATERBOOST", false);
        boolean hasLoveBoost = sharedPreferences.getBoolean("HAS_LOVEBOOST", false);
        if (hasFoodBoost) byteHP *= 1.1;
        if (hasWaterBoost) attackDelay *= 1.1;
        if (hasLoveBoost) byteAttack *= 1.1;

        switch (Species) {
            case "Birthday Bear":
                bytecharacter.setImageResource(R.drawable.birthdaybear);
                BEDIcon.setImageResource(R.drawable.birthdaybear);
                break;
            case "PenguRanger":
                bytecharacter.setImageResource(R.drawable.penguranger);
                BEDIcon.setImageResource(R.drawable.penguranger);
                break;
            case "Salacommander":
                bytecharacter.setImageResource(R.drawable.salacommander);
                BEDIcon.setImageResource(R.drawable.salacommander);
                break;
        }
        GenerateRandomEnemy();
        Handler handler = new Handler();
        while(enemyHP > 0 || byteHP > 0){
            handler.postDelayed(new Runnable() {
                public void run() {
                    byteHP -= enemyAttack;
                    playerBar.setProgress(byteHP%100);
                }
            }, (1000));
        }
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
    public void Attack(){
        enemyHP-= byteAttack;
        if (ExtendMeter < 25){
            ExtendMeter++;
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                enemyBar.setProgress(enemyHP%100);
            }
        }, (long)(500-(attackDelay*0.3)));
    }
    public void ExtendDrive(){
        if (ExtendMeter == 25){
            SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
            String Species = sharedPreferences.getString("BSPECIES", "Birthday Bear");
            switch (Species) {
                case "Birthday Bear":
                    bytecharacter.setImageResource(R.drawable.birthdaybear);
                    byteHP += 50;
                    break;
                case "PenguRanger":
                    bytecharacter.setImageResource(R.drawable.penguranger);
                    attackDelay = 0;
                    break;
                case "Salacommander":
                    bytecharacter.setImageResource(R.drawable.salacommander);
                    enemyHP -= 200;
                    break;
            }
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        // Tap is detected
        if(action == MotionEvent.ACTION_DOWN){
            Attack();
        }
        return true;
    }
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
        enemyBar.setProgress(100);
    }
}