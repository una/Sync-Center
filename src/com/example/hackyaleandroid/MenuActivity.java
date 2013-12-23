package com.example.hackyaleandroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addsynclayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.addsync, menu);
		return true;
	}


	public void everythingMenu()
	{

	}

}
