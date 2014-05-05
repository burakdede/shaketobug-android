package com.shaketobugsample.android;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import com.shaketobug.ShaketobugConfig;
import com.shaketobug.ShaketobugManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ShaketobugManager.getInstance().startListening(this);
		ShaketobugManager.getInstance().setConfig(setupConfig());
	}
	
	public ShaketobugConfig setupConfig() {
		ShaketobugConfig config = new ShaketobugConfig();
		config.setActionbarColor(Color.RED);
		config.setActionbarTitle("Sample Title");
		config.setEmailSubjectField("Bug Report From User");
		config.setEmailToField("burakdede87@gmail.com");
		config.setPencilColor(Color.YELLOW);
		config.setUseDarkIcons(false);
		config.setActionbarBackgrounDrawable(R.drawable.yellow_header_background);
		return config;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShaketobugManager.getInstance().stopListening(this);
	}

}
