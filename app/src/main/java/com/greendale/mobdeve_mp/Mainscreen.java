package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.w3c.dom.Text;

public class Mainscreen extends AppCompatActivity {
    FrameLayout slideMenu;
    ConstraintLayout graphicIndicator;
    ImageButton fightButton, foodButton, waterButton, careButton, advButton;
    ImageView bowl, container,heartIndicator, bytePet, gesture;

    TextView advText;
    Boolean foodToggle,waterToggle,careToggle;
    TextView SharedPrefTest;

    // ***** Sensor related crap *****
    private int shakeCounter = 0;
    final int SHAKE_THRESHOLD = 12;
    private float lastX, lastY, lastZ = 0.0f;
    private double accel, accelLast;
    private SensorManager sensorManager;
    private Sensor sensor;

    //Code for detecting shakes
    private SensorEventListener shakeListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float deltaX = x - lastX;
            float deltaY = y - lastY;
            float deltaZ = z - lastZ;

            accel = Math.sqrt((double) (x*x)+(y*y)+(z*z));
            double delta = accel - accelLast;

            if (delta > SHAKE_THRESHOLD) {
                ++shakeCounter;
            }

            if (shakeCounter >= 3) {
                shakeCounter = 0;
                putFoodInTheDamnBowl();
            }
            accelLast = accel;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    // ***** Sensor related crap ends here *****

    int needHunger, needThirst, needLove = 100; //fallback value is 100

    final int MAX_HUNGER = 100;
    final int MAX_THIRST = 100;
    final int MAX_LOVE = 100;

    //how much of a stat is replenished when satiating it
    final int STAT_REPLENISH = 75;

    TextView hungerText, thirstText, careText; //graphic indicators
    MediaPlayer foodMP, waterMP, loveMP; //sound indicators

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        startService(new Intent(this, StatDecreaseService.class));

        slideMenu = (FrameLayout) findViewById(R.id.slideMenu);
        graphicIndicator = (ConstraintLayout) findViewById(R.id.graphicIndicator);
        fightButton = (ImageButton) findViewById(R.id.fightButton);
        foodButton = (ImageButton) findViewById(R.id.foodButton);
        waterButton = (ImageButton) findViewById(R.id.waterButton);
        careButton = (ImageButton) findViewById(R.id.careButton);
        bytePet = (ImageView) findViewById(R.id.bytePet);
        bowl = (ImageView) findViewById(R.id.bowl);
        container = (ImageView) findViewById(R.id.container);
        gesture = (ImageView) findViewById(R.id.gesture);
        heartIndicator = (ImageView) findViewById(R.id.heartIndicator);
        SharedPrefTest = (TextView) findViewById(R.id.SharedPrefTest);
        advButton = (ImageButton) findViewById(R.id.advancebutton);
        advText = (TextView) findViewById(R.id.advtext);
        slideMenu.setVisibility(View.GONE);
        graphicIndicator.setVisibility(View.GONE);
        foodToggle = false;
        waterToggle = false;
        careToggle = false;
        accel = SensorManager.GRAVITY_EARTH;
        accelLast = SensorManager.GRAVITY_EARTH;

        //Sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        //debugging textview
        hungerText = (TextView) findViewById(R.id.debugTextViewHunger);
        thirstText = (TextView) findViewById(R.id.thirstLevel);
        careText = (TextView) findViewById(R.id.carelevel);
        hungerText.setVisibility(View.GONE);
        thirstText.setVisibility(View.GONE);
        careText.setVisibility(View.GONE);

        //sound initialization
        foodMP = MediaPlayer.create(getApplicationContext(), R.raw.food);
        waterMP = MediaPlayer.create(getApplicationContext(), R.raw.water);
        loveMP = MediaPlayer.create(getApplicationContext(), R.raw.love);

        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        String Species = sharedPreferences.getString("BSPECIES", "Birthday Bear");
        Boolean evo = sharedPreferences.getBoolean("CANEVO", false);
        Integer wins = sharedPreferences.getInt("WINS", 0);
        if (!evo && wins >=10){
            advButton.setVisibility(View.VISIBLE);
            advText.setVisibility(View.VISIBLE);
        }
        else{
            advButton.setVisibility(View.GONE);
            advText.setVisibility(View.GONE);
        }

        SharedPrefTest.setText(Species);
        switch (Species) {
            case "Birthday Bear":
                if (evo) bytePet.setImageResource(R.drawable.birthdaybearevo);
                else bytePet.setImageResource(R.drawable.birthdaybear);
                break;
            case "PenguRanger":
                if (evo) bytePet.setImageResource(R.drawable.pengurangerevo);
                else bytePet.setImageResource(R.drawable.penguranger);
                break;
            case "Salacommander":
                if (evo) bytePet.setImageResource(R.drawable.salacommanderevo);
                else bytePet.setImageResource(R.drawable.salacommander);
                break;
        }

        //load current hunger/thirst/love values into ints
        loadData();
        //check if pet should be gone
        if (needHunger == 0 || needThirst == 0) {bytePet.setVisibility(View.INVISIBLE);
        careButton.setVisibility(View.INVISIBLE);
        fightButton.setVisibility(View.INVISIBLE);}
        else{ bytePet.setVisibility(View.VISIBLE);
            careButton.setVisibility(View.VISIBLE);
            fightButton.setVisibility(View.VISIBLE);}
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        debugTextViewRefresh();
    }

    public void debugTextViewRefresh() {
        loadData();
        hungerText.setText("" + needThirst);
    }

    public void OpenSesame(View v) { slideMenu.setVisibility(View.VISIBLE); }
    public void CloseSesame(View v) { slideMenu.setVisibility(View.GONE); }

    public void Battle(View v){
        Intent intent = new Intent(Mainscreen.this, Battle.class);
        startActivity(intent);
    }
    public void GoToShop(View v){
        Intent intent = new Intent(Mainscreen.this, ShopActivity.class);
        startActivity(intent);
        finish();
    }
    public void GoToSettings(View v){
        Intent intent = new Intent(Mainscreen.this, Settings.class);
        startActivity(intent);
        finish();
    }
    public void Advance(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        Boolean evo = sharedPreferences.getBoolean("CANEVO", false);
        Integer wins = sharedPreferences.getInt("WINS", 0);
        if (!evo && wins >=10){
            sharedPreferences.edit().putBoolean("CANEVO", true).apply();
            advButton.setVisibility(View.GONE);
            advText.setVisibility(View.GONE);
            Intent intent = new Intent(this, Mainscreen.class);
            startActivity(intent);
            finish();
        }
    }
    public void giveFood(View v){
        loadData();
        if (!foodToggle){
            foodToggle = true;
            hungerText.setVisibility(View.VISIBLE);
            hungerText.setText(needHunger+"%");
            graphicIndicator.setVisibility(View.VISIBLE);
            gesture.setImageResource(R.drawable.shakeicon);
            gesture.setAnimation(AnimationUtils.loadAnimation(this,R.anim.infiniteshake));
            //only allow feeding if hunger < max
            if (needHunger < MAX_HUNGER)
            {
                bowl.setImageResource(R.drawable.foodbowlempty);
                container.setImageResource(R.drawable.megabites);

                //activate sensor
                sensorManager.registerListener(shakeListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
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
            hungerText.setVisibility(View.GONE);
            sensorManager.unregisterListener(shakeListener);
        }
    }

    public void putFoodInTheDamnBowl() {
        //Do nothing if not in feeding mode
        if (!foodToggle) {
            return;
        }
        //otherwise, feed
        loadData();
        needHunger = Math.min(MAX_HUNGER, needHunger + STAT_REPLENISH);
        foodMP.start();
        if (needHunger == 0 || needThirst == 0) {bytePet.setVisibility(View.INVISIBLE);
            careButton.setVisibility(View.INVISIBLE);
            fightButton.setVisibility(View.INVISIBLE);}
        else{ bytePet.setVisibility(View.VISIBLE);
            careButton.setVisibility(View.VISIBLE);
            fightButton.setVisibility(View.VISIBLE);}
        //violate DRY
        foodToggle = false;
        careButton.setVisibility(View.VISIBLE);
        waterButton.setVisibility(View.VISIBLE);
        graphicIndicator.setVisibility(View.GONE);
        hungerText.setVisibility(View.GONE);
        sensorManager.unregisterListener(shakeListener);
        saveData();
    }
    public void giveWater(View v){
        loadData();
        if (!waterToggle){
            waterToggle = true;
            graphicIndicator.setVisibility(View.VISIBLE);
            thirstText.setVisibility(View.VISIBLE);
            gesture.setAnimation(null);
            gesture.setImageResource(R.drawable.tapicon);
            gesture.setAnimation(AnimationUtils.loadAnimation(this,R.anim.growshrink));
            thirstText.setText(needThirst+"%");
            container.setImageResource(R.drawable.waterpitcher);
            //only allow giving water if thirst < max
            if (needThirst < MAX_THIRST)
            {
                bowl.setImageResource(R.drawable.waterbowlempty);
                container.setVisibility(View.VISIBLE);
            }
            else
            {
                bowl.setImageResource(R.drawable.waterbowlfull);
                container.setVisibility(View.INVISIBLE);
            }
            careButton.setVisibility(View.INVISIBLE);
            foodButton.setVisibility(View.INVISIBLE);
        }
        else{
            waterToggle = false;
            careButton.setVisibility(View.VISIBLE);
            foodButton.setVisibility(View.VISIBLE);
            thirstText.setVisibility(View.GONE);
            graphicIndicator.setVisibility(View.GONE);

        }
    }
    public void pourTheDamnWater() {
        //DO nothing if not in giving water mode
        if (!waterToggle) {
            return;
        }
        loadData();
        //otherwise, give water
        needThirst = Math.min(MAX_THIRST, needThirst + STAT_REPLENISH);
        waterMP.start();
        if (needHunger == 0 || needThirst == 0) {bytePet.setVisibility(View.INVISIBLE);
            careButton.setVisibility(View.INVISIBLE);
            fightButton.setVisibility(View.INVISIBLE);}
        else{ bytePet.setVisibility(View.VISIBLE);
            careButton.setVisibility(View.VISIBLE);
            fightButton.setVisibility(View.VISIBLE);}
        waterToggle = false;
        thirstText.setVisibility(View.GONE);
        careButton.setVisibility(View.VISIBLE);
        foodButton.setVisibility(View.VISIBLE);
        graphicIndicator.setVisibility(View.GONE);
        saveData();
    }
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        // Tap is detected
        if(action == MotionEvent.ACTION_DOWN){
            if (careToggle){
                loadData();
                //care
                needLove = Math.min(MAX_LOVE, needLove + STAT_REPLENISH);
                saveData();
                loveMP.start();
                careToggle = false;
                heartIndicator.setVisibility(View.INVISIBLE);
                careText.setVisibility(View.GONE);
                foodButton.setVisibility(View.VISIBLE);
                waterButton.setVisibility(View.VISIBLE);
                graphicIndicator.setVisibility(View.GONE);
            }
            else if (waterToggle) {
                pourTheDamnWater();
            }
        }
        return true;
    }
    public void giveCare(View v){
        ViewGroup.LayoutParams heartsize = heartIndicator.getLayoutParams();
        heartsize.height = needLove;
        heartsize.width = needLove;
        heartIndicator.setLayoutParams(heartsize);
        gesture.setImageResource(R.drawable.tapicon);
        graphicIndicator.setVisibility(View.VISIBLE);
        container.setVisibility(View.INVISIBLE);
        bowl.setVisibility(View.INVISIBLE);
        gesture.setVisibility(View.VISIBLE);
        gesture.setAnimation(AnimationUtils.loadAnimation(this,R.anim.growshrink));
        if (!careToggle){
            careToggle = true;
            heartIndicator.setVisibility(View.VISIBLE);
            careText.setVisibility(View.VISIBLE);
            loadData();
            careText.setText(needLove+"%");
            waterButton.setVisibility(View.INVISIBLE);
            foodButton.setVisibility(View.INVISIBLE);
        }
        else{
            careToggle = false;
            heartIndicator.setVisibility(View.INVISIBLE);
            careText.setVisibility(View.GONE);
            foodButton.setVisibility(View.VISIBLE);
            waterButton.setVisibility(View.VISIBLE);
            graphicIndicator.setVisibility(View.GONE);
        }
    }

    /**
     * Save data to SharedPreferences
     */
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("BCHUNGER", needHunger);
        editor.putInt("BCTHIRST", needThirst);
        editor.putInt("BCLOVE", needLove);
        editor.apply();
    }

    /**
     * Load data from SharedPreferences.
     */
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        needHunger = sharedPreferences.getInt("BCHUNGER", 100);
        needThirst = sharedPreferences.getInt("BCTHIRST", 100);
        needLove = sharedPreferences.getInt("BCLOVE", 100);
    }

    //
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(shakeListener);
        saveData();
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeListener);
        saveData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(shakeListener);
        saveData();
    }
}