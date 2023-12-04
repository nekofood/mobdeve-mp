package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    TextView notifsetting;
    Boolean ISNOTIFSON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        notifsetting = (TextView) findViewById(R.id.notifsetting);
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        ISNOTIFSON = sharedPreferences.getBoolean("ISNOTIFSON", false);
        if (ISNOTIFSON) notifsetting.setText("Notifications: ON");
        else notifsetting.setText("Notifications: OFF");
    }
    public void ChangeNotif(View v){
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        if(notifsetting.getText().toString().equals("Notifications: ON")){
            notifsetting.setText("Notifications: OFF");
            sharedPreferences.edit().putBoolean("ISNOTIFSON",false).apply();
            //TODO: Other Setting Stuff for it
        }
        else{
            notifsetting.setText("Notifications: ON");
            sharedPreferences.edit().putBoolean("ISNOTIFSON",true).apply();
            //TODO: Other Setting Stuff for it
        }
    }
    public void SaveAndExit(View v){
        //TODO:DO SETTINGS BUSINESS
        finish();
    }
}