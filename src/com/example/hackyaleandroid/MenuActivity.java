package com.example.hackyaleandroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Sync_Center.buildType==1)
		{
			setContentView(R.layout.add_place_sync_layout);
			findViewById(R.id.addSyncLayout).setVisibility(View.VISIBLE);
			findViewById(R.id.addSyncLayout).bringToFront();
			findViewById(R.id.addressOfSyncInput).bringToFront();
			findViewById(R.id.addressOfSyncInput).setVisibility(View.VISIBLE);
			
		}
		else
			setContentView(R.layout.add_time_sync_layout);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.addsync, menu);
		return true;
	}


}
