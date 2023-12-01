package com.greendale.mobdeve_mp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;

public class StatDecreaseService extends Service {

	Handler bgHandler;
	HandlerThread handlerThread;

	int needHunger, needThirst, needLove = 100; //fallback value is 100

	final int MAX_HUNGER = 100;
	final int MAX_THIRST = 100;
	final int MAX_LOVE = 100;

	boolean hasFoodBoost, hasWaterBoost, hasLoveBoost = false;

	final int REDUCE_RATE = 5; //base stat reduction rate
	final int INTERVAL = 10; //how often in seconds the thread will work; default 5 mins (300), for debugging you can use smaller values

	@Override
	public void onCreate() {
		super.onCreate();
		//Read the stats from SharedPrefs
		loadData();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Code to be executed when the service is started
		// Return START_STICKY if you want the service to restart automatically if it gets terminated

		// Start a handler to decrease stats by fixed value * (item-based multiplier) every 5/10 minutes
		handlerThread = new HandlerThread("bgHandler");
		handlerThread.setDaemon(true);
		handlerThread.start();
		bgHandler = new Handler(handlerThread.getLooper());

		// Looper will run this regularly every <interval> seconds
		bgHandler.postDelayed(new Runnable() {

			public void run() {
				update();

				bgHandler.postDelayed(this, INTERVAL*1000);
			}
		}, INTERVAL*1000);

		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	public void loadData() {
		SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);

		needHunger = sharedPreferences.getInt("BCHUNGER", 100);
		needThirst = sharedPreferences.getInt("BCTHURST", 100);
		needLove = sharedPreferences.getInt("BCLOVE", 100);
		hasFoodBoost = sharedPreferences.getBoolean("HAS_FOODBOOST", false);
		hasWaterBoost= sharedPreferences.getBoolean("HAS_WATERBOOST", false);
		hasLoveBoost= sharedPreferences.getBoolean("HAS_LOVEBOOST", false);
	}
	public void saveData() {
		SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putInt("BCHUNGER", needHunger);
		editor.putInt("BCTHIRST", needThirst);
		editor.putInt("BCLOVE", needLove);
		editor.apply();
	}

	//Function to be ran every interval
	public void update() {
		loadData();

		//If player has relevant boost item, halve the amount of stat reduced every interval
		//(we achieve this using a ternary operation)
		//Also prevent stats from going negative using a max()
		needHunger = Math.max(0, needHunger - (hasFoodBoost ? REDUCE_RATE / 2 : REDUCE_RATE));
		needThirst = Math.max(0, needHunger - (hasWaterBoost ? REDUCE_RATE / 2 : REDUCE_RATE));
		needLove = Math.max(0, needHunger - (hasLoveBoost ? REDUCE_RATE / 2 : REDUCE_RATE));

		saveData();
	}
}
