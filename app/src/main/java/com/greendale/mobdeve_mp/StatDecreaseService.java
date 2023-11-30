package com.greendale.mobdeve_mp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StatDecreaseService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Code to be executed when the service is started
		// Return START_STICKY if you want the service to restart automatically if it gets terminated
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
}
