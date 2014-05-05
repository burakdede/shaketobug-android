package com.shaketobugsample.android;

import android.app.Application;

public class ShaketoBugApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}