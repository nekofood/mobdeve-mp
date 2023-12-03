package com.greendale.mobdeve_mp;

import static android.graphics.Color.argb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Battle extends AppCompatActivity {
    FrameLayout battlePopup;
    ImageButton exitMatchButton,resumeMatchButton,pauseButton,ExtendDrive;
    ImageView bytecharacter,enemybyte,BEDIcon;
    TextView sharedpreftesttwo, battleplayerhp, enemyhptext;
    Boolean canFight = true;
    int enemyHP = (int)(Math.random() * (1000-800+1)+800);
    int enemymaxHP = enemyHP;
    int enemyAttack = (int)(Math.random() * 3 + 1);
    int byteAttack, byteEvasion, byteHP, byteMaxHP;
    int enemyspecies = (int)(Math.random() * 3 + 1);
    ProgressBar enemyBar, playerBar;
    int ExtendMeter = 0;
    String Species;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        //findviewbyid
        sharedpreftesttwo = (TextView) findViewById(R.id.sharedpreftesttwo);
        pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        battlePopup = (FrameLayout) findViewById(R.id.battlePopup);
        exitMatchButton = (ImageButton) findViewById(R.id.battleExitMatchButton);
        resumeMatchButton = (ImageButton) findViewById(R.id.battleResumeMatchButton);
        ExtendDrive = (ImageButton) findViewById(R.id.ExtendDrive);
        bytecharacter = (ImageView) findViewById(R.id.bytecharacter);
        BEDIcon = (ImageView) findViewById(R.id.battleExtDriveIcon);
        enemybyte = (ImageView) findViewById(R.id.enemybyte);
        enemyBar = (ProgressBar) findViewById(R.id.enemyHP);
        playerBar = (ProgressBar) findViewById(R.id.playerHP);
        battleplayerhp = (TextView) findViewById(R.id.battlePlayerHp);
        enemyhptext = (TextView) findViewById(R.id.enemyHptext);
        //sharedprefs
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        Species = sharedPreferences.getString("BSPECIES", "Birthday Bear");
        boolean hasFoodBoost = sharedPreferences.getBoolean("HAS_FOODBOOST", false);
        boolean hasWaterBoost = sharedPreferences.getBoolean("HAS_WATERBOOST", false);
        boolean hasLoveBoost = sharedPreferences.getBoolean("HAS_LOVEBOOST", false);
        byteHP = (sharedPreferences.getInt("BCHUNGER", 100));
        int chance = sharedPreferences.getInt("BCTHURST", 100);
        if (chance >= 80) byteEvasion = 20;
        byteAttack = sharedPreferences.getInt("BCLOVE", 100)/10;
        //hide pausemenu
        battlePopup.setVisibility(View.GONE);
        //ExtendDrive Initialization
        ExtendDrive.setColorFilter(argb(90,50,50,50));
        ExtendMeter = 0;
        //Test Text
        sharedpreftesttwo.setText(Species);
        //Fight Variable Boosts
        if (hasFoodBoost) byteHP *= 1.1;
        if (hasWaterBoost) byteEvasion += 10;
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
        enemyBar.setProgress(enemyHP%enemymaxHP);
        byteMaxHP = byteHP;
        playerBar.setProgress(byteHP%byteMaxHP);
        battleplayerhp.setText(byteHP+"/"+byteMaxHP);
        enemyhptext.setText(enemyHP+"/"+enemymaxHP);
    }
    public void Pause(View view) {
        battlePopup.setVisibility(View.VISIBLE);canFight=false;
    }
    public void Resume(View view) {
        battlePopup.setVisibility(View.GONE);canFight=true;
    }
    public void Exit(View view){
        finish();
    }
    public void Attack(){
        if (canFight) {
            enemyHP -= byteAttack;
            enemyBar.setProgress(enemyHP/100);
            enemyhptext.setText(enemyHP+"/"+enemymaxHP);
            if (enemyHP <= 0) {
                SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
                canFight=false;
                int increase = (int)(Math.random() * (25-15+1)+15);
                int current = sharedPreferences.getInt("COINS",  100);
                int wins = sharedPreferences.getInt("WINS",  5);
                sharedPreferences.edit().putInt("COINS", increase+current).apply();
                sharedPreferences.edit().putInt("WINS", wins+1);
                finish();
            }
            if (ExtendMeter < 25) {
                ExtendMeter++;
            }
            if (ExtendMeter == 25) {
                ExtendDrive.setColorFilter(argb(0, 50, 50, 50));
            }
            if (((int) (Math.random() * (100 - 30 + 1) + 30)) - byteEvasion > 60) {
                byteHP -= enemyAttack;
                playerBar.setProgress(byteHP%byteMaxHP);
                battleplayerhp.setText(byteHP+"/"+byteMaxHP);
            } //enemy counterattack
            if (byteHP<=0) {finish();}
        }
    }
    public void ExtendDrive(View view){
        if (ExtendMeter >= 25){
            SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
            Species = sharedPreferences.getString("BSPECIES", "Birthday Bear");
            switch (Species) {
                case "Birthday Bear":
                    byteHP += 50;
                    break;
                case "PenguRanger":
                    byteEvasion = 100;
                    break;
                case "Salacommander":
                    enemyHP -= 200;
                    break;
            }
            ExtendMeter = 0;
            ExtendDrive.setColorFilter(argb(90,50,50,50));
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
    }
}