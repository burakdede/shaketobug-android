/*
 * Copyright (C) Burak Dede.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shaketobug;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.view.View;

/**
 * @author burakdede
 * 
 */
public class ShaketobugSession implements ShakeListener {

	public static final String SCREENSHOT_FILE_PATH = "com.shaketobug.FeedbackActivity.filepath";
	public static final String DEVICE_INFO_STRING = "com.shaketobug.FeedbackActivity.infostring";

	private ShakeDetector mDetector;
	private SensorManager mSensorManager;
	private Context mContext;
	private String mScreenshotPath;
	private ShaketobugConfig mConfig;

	public ShaketobugSession() {
		mDetector = new ShakeDetector(this);
		mConfig = new ShaketobugConfig();
	}
	
	public ShaketobugConfig getConfig() {
		return mConfig;
	}

	public void setConfig(ShaketobugConfig config) {
		mConfig = config;
	}

	public Context getContext() {
		return mContext;
	}

	public void setContext(Context context) {
		mContext = context;
	}

	/**
	 * Shake detected do the following work in order
	 * 
	 * <li> Get root view </li>
	 * <li> Take screenshot of current context </li>
	 * <li> Start feedback acitivty </li>
	 */
	@Override
	public void onDeviceShaked() throws InterruptedException, ExecutionException {
		View rootView = getRootView();
		Bitmap screenshotBitmap = ScreenUtils.takeScreenshot(rootView);
		mScreenshotPath = new ScreenUtils().
						  new BitmapSaveWorker(screenshotBitmap).execute().get();
		startFeedbackActivity();
	}
	
	public String getDeviceInfo() {
		return new DeviceInfo().setupDeviceInfo();
	}
	
	@Override
	public void startListening(Context context) {
		mContext = context;
		mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
		mDetector.start(mSensorManager);
	}

	/**
	 * Context will be set to null
	 * and {@link SensorManager} will be released
	 */
	@Override
	public void stopListening(Context context) {
		mContext = null;
		mDetector.stop();
	}

	private View getRootView() {
		return ((Activity) mContext).getWindow().getDecorView().getRootView();
	}

	/**
	 * Start library feedback activity
	 */
	private void startFeedbackActivity() {
		Intent intent = new Intent("com.shaketobug.ui.FeedbackActivity");
		intent.putExtra(ShaketobugSession.SCREENSHOT_FILE_PATH, mScreenshotPath);
		intent.putExtra(ShaketobugSession.DEVICE_INFO_STRING, getDeviceInfo());
		mContext.startActivity(intent);
	}
}
