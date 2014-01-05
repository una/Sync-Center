package com.example.hackyaleandroid;
import java.util.HashMap;

import com.google.android.gms.location.Geofence;

import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.LinearLayout;
import android.content.Context;
import android.content.Intent;


public class Sync_Center extends Activity 
{
	// Add geofences handler
	public static BluetoothAdapter mBluetoothAdapter;
	public static WifiManager mwifiManager;
	public static AudioManager maudioManager;
	public static SmsManager msms;
	public static Context mcontext;
	public static HashMap<String, LocationSync> currentLocationSyncs;
	public static HashMap<String, TimeSync	> currentTimeSyncs;
	public static int buildType=0;//if build type is 1, make a place sync, if 2, make a time sync
	public static int numberOfSyncs;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync__center);
		final Context mcontext = getApplicationContext() ;

		currentLocationSyncs = new HashMap<String,LocationSync>();
		numberOfSyncs=currentLocationSyncs.size();

		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		WifiManager wifiManager = (WifiManager) mcontext.getSystemService(Context.WIFI_SERVICE);
		AudioManager audioManager = (AudioManager) mcontext.getSystemService(Context.AUDIO_SERVICE);
		SmsManager sms = SmsManager.getDefault();

		//set up main screen: title, plus button to add syncs, and view to see previous ones
		findViewById(R.id.addNewPlaceSync).setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				buildType=1;
				Intent intent = new Intent(Sync_Center.this,MenuActivity.class);
				startActivity(intent);
			}
		});

		findViewById(R.id.addNewTimeSync).setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				buildType=2;
				Intent intent = new Intent(Sync_Center.this,MenuActivity.class);
				startActivity(intent);
			}
		});

	}
	public void createSync(String Title, String Location, int Radius, Boolean wifiSelected, Boolean bluetoothSelected, Boolean bluetooth,
			Boolean smsSelected,Boolean wifi, Boolean sms, Boolean volumeOn, int soundSetting, boolean vibrateOn,  String phoneNumber, String textMessage)
	{
		LocationSync sync = new LocationSync(Sync_Center.this, Title, Location, Radius, wifiSelected, wifi, bluetoothSelected, bluetooth, volumeOn, soundSetting, vibrateOn, smsSelected, new TextMessage(phoneNumber,textMessage), Geofence.GEOFENCE_TRANSITION_ENTER);
	}
}