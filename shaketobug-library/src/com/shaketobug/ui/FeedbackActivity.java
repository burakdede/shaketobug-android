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
package com.shaketobug.ui;

import java.io.File;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shake2bug.android.R;
import com.shaketobug.ScreenUtils;
import com.shaketobug.ShaketobugConfig;
import com.shaketobug.ShaketobugManager;
import com.shaketobug.ShaketobugSession;

public class FeedbackActivity extends Activity implements OnTouchListener {

	public static final String PATH_STRING = "com.shaketobug.ui.FeedbackActivity";

	private ShaketobugConfig mConfig;
	private String mDeviceInfo;
	private String mFilePath;
	private ImageView mScreenImageView;
	private FrameLayout mFlashPanel;
	private Paint paint;
	private Matrix matrix;
	private Canvas canvas;
	private Bitmap overlayBitmap;
	private Bitmap backgroundBitmap;
	float downx = 0, downy = 0, upx = 0, upy = 0;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		mConfig = ShaketobugManager.getInstance().getConfig();
		setupCustomActionbar();

		mDeviceInfo = getIntent().getStringExtra(ShaketobugSession.DEVICE_INFO_STRING);
		mFilePath = getIntent().getStringExtra(ShaketobugSession.SCREENSHOT_FILE_PATH);
		setContentView(R.layout.activity_feedback);

		mFlashPanel = (FrameLayout) findViewById(R.id.flashPanel);
		backgroundBitmap = ScreenUtils.loadImage(mFilePath);
		mScreenImageView = (ImageView) findViewById(R.id.screenImageView);
		mScreenImageView.setDrawingCacheEnabled(true);
		mScreenImageView.setOnTouchListener(this);
		
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			mScreenImageView.setBackgroundDrawable(new BitmapDrawable(
					getResources(), backgroundBitmap));
		} else {
			mScreenImageView.setBackground(new BitmapDrawable(getResources(),
					backgroundBitmap));
		}

		startScreenshotAnimation();
		setupPaintConfigs();	
	}

	/**
	 * Setup action bar with library configurations
	 */
	private void setupCustomActionbar() {
		getActionBar().setBackgroundDrawable(new ColorDrawable(mConfig.getActionbarColor()));
		if (mConfig.getActionbarBackgrounDrawable() != Integer.MIN_VALUE) {
			Drawable actionbarDrawable = getResources().getDrawable(mConfig.getActionbarBackgrounDrawable());
			getActionBar().setBackgroundDrawable(actionbarDrawable);
		}
		
		getActionBar().setIcon(android.R.color.transparent);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        	case android.R.id.home: 
	            onBackPressed();
	            return true;
        }

        return super.onOptionsItemSelected(item);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem messageItem = menu.add(0, 1, 0, getResources().
				getString(R.string.actionbar_send_string));
		if (mConfig.isUseDarkIcons()) {
			messageItem.setIcon(R.drawable.ic_action_send_dark);
		} else {
			messageItem.setIcon(R.drawable.ic_action_send_light);
		}
		messageItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		messageItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				String filepath = null;
				try {
					filepath = new ScreenUtils().
							   new BitmapSaveWorker(overlayBitmap).execute().get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				sendMail(new File(filepath));
				return true;
			}
		});

		return true;
	}

	/**
	 * Screenshot taking animation some alpha transition
	 * with fake framelayout
	 */
	private void startScreenshotAnimation() {
		AlphaAnimation fade = new AlphaAnimation(1, 0);
		fade.setDuration(300);
		fade.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationEnd(Animation anim) {
				mFlashPanel.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationStart(Animation animation) {
				mFlashPanel.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {}
		});

		mFlashPanel.startAnimation(fade);
	}

	/**
	 * Send and start mail sending intent
	 * with some pre configured options
	 * @param f {@link File}
	 */
	private void sendMail(File f) {

		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("vnd.android.cursor.dir/email");
		String to[] = {mConfig.getEmailToField()};
		sharingIntent.putExtra(Intent.EXTRA_EMAIL, to);
		Uri uriFile = Uri.fromFile(f);
		sharingIntent.putExtra(Intent.EXTRA_STREAM, uriFile);
		sharingIntent.putExtra(Intent.EXTRA_TEXT, mDeviceInfo);
		sharingIntent.putExtra(Intent.EXTRA_SUBJECT, mConfig.getEmailSubjectField());
		startActivity(Intent.createChooser(sharingIntent, 
				getResources().getString(R.string.mail_intent_title)));

	}

	/**
	 * Setup canvas over {@link ImageView} with size
	 * similar to screenshot image
	 */
	public void setupPaintConfigs() {
		overlayBitmap = Bitmap.createBitmap(backgroundBitmap.getWidth(),
											backgroundBitmap.getHeight(), 
											backgroundBitmap.getConfig());
		canvas = new Canvas(overlayBitmap);
		paint = new Paint();
		paint.setColor(mConfig.getPencilColor());
		paint.setDither(true);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(10);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		matrix = new Matrix();
		canvas.drawBitmap(backgroundBitmap, matrix, paint);
		mScreenImageView.setImageBitmap(overlayBitmap);
	}

	public boolean onTouch(View v, MotionEvent event) {

		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN :
				downx = event.getX();
				downy = event.getY();
				break;

			case MotionEvent.ACTION_MOVE :
				upx = event.getX();
				upy = event.getY();
				canvas.drawLine(downx, downy, upx, upy, paint);
				mScreenImageView.invalidate();
				downx = upx;
				downy = upy;
				break;

			case MotionEvent.ACTION_UP :
				break;

			case MotionEvent.ACTION_CANCEL :
				break;

			default :
				break;
		}

		return true;
	}
}
