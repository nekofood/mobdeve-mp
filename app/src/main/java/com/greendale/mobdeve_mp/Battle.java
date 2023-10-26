package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
public class Battle extends AppCompatActivity {
    ImageButton pauseButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        pauseButton = findViewById(R.id.pauseButton);
    }
    public void Pause(View view) {
        DialogFragment frag = new BattlePopup();
        frag.show(getSupportFragmentManager(), "Retreat");
    }
}