package com.greendale.mobdeve_mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ShopActivity extends AppCompatActivity {

    ImageButton shopBackBtn;

    ImageButton[] shopButton;
    TextView coinText, shopText1, shopText2, shopText3;
    boolean hasFoodBoost, hasWaterBoost, hasLoveBoost;
    int coins = 0;

    final int ITEM_PRICE = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        setContentView(R.layout.activity_shop);

        shopBackBtn = (ImageButton) findViewById(R.id.shopBackBtn);

        shopText1 = (TextView) findViewById(R.id.shopText1);
        shopText2 = (TextView) findViewById(R.id.shopText2);
        shopText3 = (TextView) findViewById(R.id.shopText3);

        shopButton = new ImageButton[3];
        shopButton[0] = (ImageButton) findViewById(R.id.shopButton1);
        shopButton[1] = (ImageButton) findViewById(R.id.shopButton2);
        shopButton[2] = (ImageButton) findViewById(R.id.shopButton3);
        disableBoughtButtons();

        coinText = (TextView) findViewById(R.id.coinCount);
        coinText.setText("You have " + coins + " coins");

        for (ImageButton btn : shopButton) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    String tag = btn.getTag().toString();
                    bundle.putString("ITEMTAG", tag);

                    //Show purchase confirmation
                    DialogFragment frag = new ShopFragment();
                    frag.setArguments(bundle);
                    frag.show(getSupportFragmentManager(), "buy");
                }
            });
        }
        shopBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void purchaseItem (String itemTag) {
        Log.i("shop", "received purchase confirmation from fragment");
        Log.i("shop", itemTag);

        //check for validity
        if (itemTag == null || (!itemTag.equals("itemFood") && !itemTag.equals("itemWater") && !itemTag.equals("itemLove"))) {
            return;
        }

        //check if player has enough coins
        if (coins < ITEM_PRICE) {
            //Toast "purchase unsuccessful"
            Toast toast = Toast.makeText(this, "You don't have enough coins!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        //Subtract currency
        coins -= ITEM_PRICE;
        //Set relevant Shared Preferences flags
        switch (itemTag) {
            case "itemFood":
                hasFoodBoost = true;
                break;
            case "itemWater":
                hasWaterBoost = true;
                break;
            case "itemLove":
                hasLoveBoost = true;
                break;
        }

        disableBoughtButtons();
        //Save data and update coins text
        saveData();
        updateCoinsText();
    }

    private void updateCoinsText() {
        loadData();
        coinText.setText("You have " + coins + " coins");
    }

    //Disable buttons for items already purchased
    private void disableBoughtButtons() {
        for (ImageButton btn : shopButton) {
            String tag = btn.getTag().toString();
            if (tag.equals("itemFood") && hasFoodBoost) {
                btn.setClickable(false);
                btn.setEnabled(false);
                shopText1.setTextColor(Color.GRAY);
            }
            if (tag.equals("itemWater") && hasWaterBoost) {
                btn.setClickable(false);
                btn.setEnabled(false);
                shopText2.setTextColor(Color.GRAY);
            }
            if (tag.equals("itemLove") && hasLoveBoost) {
                btn.setClickable(false);
                btn.setEnabled(false);
                shopText3.setTextColor(Color.GRAY);
            }
        }
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        coins = sharedPreferences.getInt("COINS", 1000);
        hasFoodBoost = sharedPreferences.getBoolean("HAS_FOODBOOST", false);
        hasWaterBoost = sharedPreferences.getBoolean("HAS_WATERBOOST", false);
        hasLoveBoost = sharedPreferences.getBoolean("HAS_LOVEBOOST", false);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("COINS", coins);
        editor.putBoolean("HAS_FOODBOOST", hasFoodBoost);
        editor.putBoolean("HAS_WATERBOOST", hasWaterBoost);
        editor.putBoolean("HAS_LOVEBOOST", hasLoveBoost);
        editor.apply();
    }
}
