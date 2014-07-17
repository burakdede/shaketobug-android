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

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;

/**
 * @author burakdede
 * 
 */
public class ScreenUtils {

	/**
	 * Take screenshot of the current context
	 * 
	 * @param rootView of the {@link Activity}
	 * @return {@link Bitmap}
	 */
	public static Bitmap takeScreenshot(View rootView) {
        rootView.destroyDrawingCache();
		rootView.setDrawingCacheEnabled(true);
		Bitmap screenBitmap = rootView.getDrawingCache();
		return screenBitmap;
	}

	/**
	 * Load image from external directory 
	 * @param filePath {@link String}
	 * @return {@link Bitmap}
	 */
	public static Bitmap loadImage(String filePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		File screenshotFile = new File(filePath);
		Bitmap bitmap = BitmapFactory.decodeFile(screenshotFile.getAbsolutePath(), options);
		return bitmap;
	}
	
	/**
	 * Create file name with current date
	 * @return {@link String} filename
	 */
	public static String createNameforFile() {
		return String.valueOf(System.currentTimeMillis());
	}
	
	/**
	 * Save given bitmap to external directory
	 * 
	 * @param bitmap {@link Bitmap}
	 * @return {@link String}
	 */
	public static String saveScreenshot(Bitmap bitmap) {
		String externalPath = Environment.getExternalStorageDirectory() + "/";
		String filePath = externalPath + createNameforFile();
		File imageFile = new File(filePath);
		FileOutputStream fos;

		try {
			fos = new FileOutputStream(imageFile);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			// add exception
		}
		return filePath;
	}
	
	/**
	 * Non-UI external thread for bitmap save operations
	 *
	 */
	public class BitmapSaveWorker extends AsyncTask<Void, Void, String> {
		
		private final WeakReference<Bitmap> weakBitmap;
		
		public BitmapSaveWorker(Bitmap bitmap) {
			weakBitmap = new WeakReference<Bitmap>(bitmap);
		}

		@Override
		protected String doInBackground(Void... params) {
			return saveScreenshot(weakBitmap.get());
		}
		
	}
}
