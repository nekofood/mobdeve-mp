package com.greendale.mobdeve_mp;

import static android.graphics.Color.argb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Battle extends AppCompatActivity {
    FrameLayout battlePopup;
    ImageButton exitMatchButton,resumeMatchButton,pauseButton,ExtendDrive;
    ImageView bytecharacter,enemybyte,BEDIcon;
    TextView sharedpreftesttwo, battleplayerhp, enemyhptext, wintest;
    Boolean canFight = true;
    float enemyHP = (float)(Math.random() * (1000-800+1)+800);
    float enemymaxHP = enemyHP;
    int enemyAttack = (int)(Math.random() * 3 + 1);
    int byteAttack, byteEvasion, byteHP, byteMaxHP, byteSetEvasion, shieldcountdown;
    int enemyspecies = (int)(Math.random() * 3 + 1);
    ProgressBar enemyBar, playerBar;
    int ExtendMeter = 0;
    String Species;
    MediaPlayer hit, pengushroud, cake, artillery;
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
        wintest = (TextView) findViewById(R.id.wintest);
        //soundfx
        pengushroud = MediaPlayer.create(getApplicationContext(), R.raw.tone);
        hit = MediaPlayer.create(getApplicationContext(), R.raw.hithurt);
        artillery = MediaPlayer.create(getApplicationContext(), R.raw.explosion);
        cake = MediaPlayer.create(getApplicationContext(), R.raw.powerup);
        //sharedprefs
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        int wins = sharedPreferences.getInt("WINS",  5);
        wintest.setText(wins+"");
        Species = sharedPreferences.getString("BSPECIES", "Birthday Bear");
        Boolean evo = sharedPreferences.getBoolean("CANEVO", false);
        boolean hasFoodBoost = sharedPreferences.getBoolean("HAS_FOODBOOST", false);
        boolean hasWaterBoost = sharedPreferences.getBoolean("HAS_WATERBOOST", false);
        boolean hasLoveBoost = sharedPreferences.getBoolean("HAS_LOVEBOOST", false);
        byteHP = (sharedPreferences.getInt("BCHUNGER", 100));
        if (byteHP == 0) byteHP = 1; //anti divide by 0
        int chance = sharedPreferences.getInt("BCTHURST", 100);
        if (chance >= 80) byteEvasion = 20;
        byteAttack = sharedPreferences.getInt("BCLOVE", 100)/4;
        byteSetEvasion = byteEvasion;
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
        if (evo){
            byteHP *= 1.1;
            byteEvasion += 10;
            byteAttack *= 1.1;
        }
        switch (Species) {
            case "Birthday Bear":
                if (evo){
                    bytecharacter.setImageResource(R.drawable.birthdaybearevoback);
                    BEDIcon.setImageResource(R.drawable.birthdaybearevo);
                }
                else{
                bytecharacter.setImageResource(R.drawable.birthdaybearback);
                BEDIcon.setImageResource(R.drawable.birthdaybear);}
                break;
            case "PenguRanger":
                if (evo){
                    bytecharacter.setImageResource(R.drawable.pengurangerevoback);
                    BEDIcon.setImageResource(R.drawable.pengurangerevo);
                }
                else{
                bytecharacter.setImageResource(R.drawable.pengurangerback);
                BEDIcon.setImageResource(R.drawable.penguranger);}
                break;
            case "Salacommander":
                if(evo){
                    bytecharacter.setImageResource(R.drawable.salacommanderevoback); //salacommander never faces his opponent, they are doomed
                    BEDIcon.setImageResource(R.drawable.salacommanderevo);
                }
                else{
                bytecharacter.setImageResource(R.drawable.salacommanderback); //salacommander never faces his opponent, they are doomed
                BEDIcon.setImageResource(R.drawable.salacommander);}
                break;
        }
        GenerateRandomEnemy();
        enemyBar.setProgress((int)Math.round(enemyHP/enemymaxHP*100.0));
        byteMaxHP = byteHP;
        playerBar.setProgress(byteHP%byteMaxHP);
        battleplayerhp.setText(byteHP+"/"+byteMaxHP);
        enemyhptext.setText(Math.round(enemyHP)+"/"+Math.round(enemymaxHP));
        wintest.setVisibility(View.INVISIBLE);
    }
    public void Pause(View view) {
        battlePopup.setVisibility(View.VISIBLE);canFight=false;
    }
    public void Resume(View view) {
        battlePopup.setVisibility(View.GONE);canFight=true;
    }
    public void Exit(View view){
        Intent intent = new Intent(this, Mainscreen.class);
        startActivity(intent);
        finish();
    }
    public void Attack(){
        if (canFight) {
            enemyHP = Math.max(0, enemyHP - byteAttack);
            hit.start();
            enemybyte.setAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
            enemyBar.setProgress((int)Math.round(enemyHP/enemymaxHP*100.0));
            enemyhptext.setText(Math.round(enemyHP)+"/"+Math.round(enemymaxHP));
            if (shieldcountdown >0) {
                shieldcountdown--;
                if (shieldcountdown==0){
                    byteEvasion = byteSetEvasion;
                    bytecharacter.setImageAlpha(255);
                }
            }
            //If enemyHP <= 0, you win!
            if (enemyHP <= 0) {
                SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
                canFight=false;
                int increase = (int)(Math.random() * (25-15+1)+15);
                int current = sharedPreferences.getInt("COINS",  100);
                int wins = sharedPreferences.getInt("WINS",  5);
                Toast toast = Toast.makeText(this, "You win! You earned " + increase + " coins!", Toast.LENGTH_SHORT);
                toast.show();
                sharedPreferences.edit().putInt("COINS", increase+current).apply();
                sharedPreferences.edit().putInt("WINS", wins+1).apply();
                Intent intent = new Intent(this, Mainscreen.class);
                startActivity(intent);
                finish();
            }
            if (ExtendMeter < 25) {
                ExtendMeter++;
            }
            if (ExtendMeter == 25) {
                ExtendDrive.setColorFilter(argb(0, 50, 50, 50));
            }
            if ((((int) (Math.random() * (100 - 30 + 1) + 30)) - byteEvasion) > 60) {
                byteHP -= enemyAttack;
                hit.start();
                bytecharacter.setAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
                if (byteHP > byteMaxHP) playerBar.setProgress(100);
                else playerBar.setProgress(byteHP%byteMaxHP);
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
                    cake.start();
                    break;
                case "PenguRanger":
                    byteEvasion = 100;
                    pengushroud.start();
                    bytecharacter.setImageAlpha(60);
                    shieldcountdown = 20;
                    break;
                case "Salacommander":
                    enemyHP -= 200;
                    artillery.start();
                    enemyBar.setProgress((int)Math.round(enemyHP/enemymaxHP*100.0));
                    enemyhptext.setText(Math.round(enemyHP)+"/"+Math.round(enemymaxHP));
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