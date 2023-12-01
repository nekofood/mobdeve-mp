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

	final int REDUCE_RATE = 5; //base stat reduction rate
	final int INTERVAL = 300000; //how often in ms the thread will work; default 5 mins

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

		// Looper will send a message regularly every <interval> ms
		bgHandler.postDelayed(() -> update(), INTERVAL);

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

		//todo: Check if player has boost items
		needHunger = Math.max(0, needHunger - REDUCE_RATE);
		needThirst = Math.max(0, needHunger - REDUCE_RATE);
		needLove = Math.max(0, needHunger - REDUCE_RATE);

		saveData();
	}
}
