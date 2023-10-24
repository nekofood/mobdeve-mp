package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class ShopActivity extends AppCompatActivity {

    ImageButton shopBackBtn, shopButton1, shopButton2, shopButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
    }
}