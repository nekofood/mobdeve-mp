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
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class Mainscreen extends AppCompatActivity {
    FrameLayout slideMenu;
    ConstraintLayout graphicIndicator;
    ImageButton fightButton, foodButton, waterButton, careButton;
    ImageView bowl, container,heartIndicator, bytePet;
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
            float y = event.values[0];
            float z = event.values[0];

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
    final int STAT_REPLENISH = 50;

    TextView hungerText; //DEBUG

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
        heartIndicator = (ImageView) findViewById(R.id.heartIndicator);
        SharedPrefTest = (TextView) findViewById(R.id.SharedPrefTest);
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

        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        String Species = sharedPreferences.getString("BSPECIES", "Birthday Bear");

        SharedPrefTest.setText(Species);
        switch (Species) {
            case "Birthday Bear":
                bytePet.setImageResource(R.drawable.birthdaybear);
                break;
            case "PenguRanger":
                bytePet.setImageResource(R.drawable.penguranger);
                break;
            case "Salacommander":
                bytePet.setImageResource(R.drawable.salacommander);
                break;
        }

        //load current hunger/thirst/love values into ints
        loadData();
        //check if pet should be gone
    }

    @Override
    public void onResume() {
        super.onResume();
        debugTextViewRefresh();
    }

    public void debugTextViewRefresh() {
        loadData();
        hungerText.setText("" + needHunger);
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
    }
    public void GoToSettings(View v){
        Intent intent = new Intent(Mainscreen.this, Settings.class);
        startActivity(intent);
    }
    public void giveFood(View v){
        if (!foodToggle){
            foodToggle = true;
            graphicIndicator.setVisibility(View.VISIBLE);
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

            sensorManager.unregisterListener(shakeListener);
        }
    }

    public void putFoodInTheDamnBowl() {
        //Do nothing if not in feeding mode
        if (!foodToggle) {
            return;
        }
        //otherwise, feed
        needHunger = Math.min(100, needHunger + STAT_REPLENISH);

        //violate DRY
        foodToggle = false;
        careButton.setVisibility(View.VISIBLE);
        waterButton.setVisibility(View.VISIBLE);
        graphicIndicator.setVisibility(View.GONE);

        sensorManager.unregisterListener(shakeListener);
    }
    public void giveWater(View v){
        if (!waterToggle){
            waterToggle = true;
            graphicIndicator.setVisibility(View.VISIBLE);
            //only allow giving water if thirst < max
            if (needThirst < MAX_THIRST)
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
        ViewGroup.LayoutParams heartsize = heartIndicator.getLayoutParams();
        heartsize.height = needLove;
        heartsize.width = needLove;
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

    /**
     * Save data to SharedPreferences when switching activities
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
     * Load data from SharedPreferences. Honestly not sure where this is needed
     */
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);

        needHunger = sharedPreferences.getInt("BCHUNGER", 100);
        needThirst = sharedPreferences.getInt("BCTHURST", 100);
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