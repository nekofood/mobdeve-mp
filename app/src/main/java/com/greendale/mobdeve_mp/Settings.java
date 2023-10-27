package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    TextView notifsetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        notifsetting = (TextView) findViewById(R.id.notifsetting);
    }
    public void ChangeNotif(View v){
        if(notifsetting.getText().toString().equals("Notifications: ON")){
            notifsetting.setText("Notifications: OFF");
            //TODO: Other Setting Stuff for it
        }
        else{
            notifsetting.setText("Notifications: ON");
            //TODO: Other Setting Stuff for it
        }
    }
    public void SaveAndExit(View v){
        //TODO:DO SETTINGS BUSINESS
        finish();
    }
}