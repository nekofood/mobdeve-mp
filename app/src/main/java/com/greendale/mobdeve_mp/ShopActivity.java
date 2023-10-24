package com.greendale.mobdeve_mp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ShopActivity extends AppCompatActivity {

    ImageButton shopBackBtn, shopButton1, shopButton2, shopButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        ImageButton[] shopButton = new ImageButton[3];
        shopButton[0] = (ImageButton) findViewById(R.id.shopButton1);
        shopButton[1] = (ImageButton) findViewById(R.id.shopButton2);
        shopButton[2] = (ImageButton) findViewById(R.id.shopButton3);

        for (ImageButton btn : shopButton) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment frag = new ShopFragment();
                    frag.show(getSupportFragmentManager(), "buy");
                }
            });
        }
    }
}
