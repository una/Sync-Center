package com.example.hackyaleandroid;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;


public class Sync_Center extends Activity 
{

	EditText text;
	ImageButton plusButton; 
	Button deleteButton;
	LinearLayout Buttons;

	final static int MAIN=0,CHOOSESYNC=1, NEWSYNC=2, TIMESYNC=3, PLACESYNC=4;
	static int State = MAIN;
	//temp 
	Button[] buttons = new Button[10];
	int numberOfSyncs=2;
	ImageButton header;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync__center);
		final Context mcontext = getBaseContext() ;
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		WifiManager wifiManager = (WifiManager) mcontext.getSystemService(Context.WIFI_SERVICE);
		AudioManager audioManager = (AudioManager) mcontext.getSystemService(Context.AUDIO_SERVICE);
		SmsManager sms = SmsManager.getDefault();

		header= new ImageButton(mcontext);
		//we want the main screen, which draws ImageButtons of the saved syncsIn addition to a plus up top
		header.setImageResource(R.drawable.header_logo);
		header.setScaleType(ImageView.ScaleType.FIT_CENTER);
		header.setBackground(null);
		
		Buttons = new LinearLayout(mcontext);
		
		((ViewGroup) findViewById(R.id.headerDiv)).addView(header);
		ImageButton plusButton1 = new ImageButton(this);
		plusButton1.setBackgroundResource(R.drawable.circ_plus);
		plusButton1.setVisibility(View.INVISIBLE);
		Buttons.addView(plusButton1);
		ImageButton plusButton2 = new ImageButton(this);
		plusButton2.setBackgroundResource(R.drawable.circ_plus);
		Buttons.addView(plusButton2);
		plusButton2.setVisibility(View.INVISIBLE);
		
		plusButton = new ImageButton(mcontext);
		plusButton.setBackground(null);
		plusButton.setBackgroundResource(R.drawable.circ_plus);
		plusButton.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
		
		Buttons.setOrientation(LinearLayout.VERTICAL);
		Buttons.addView(plusButton);

		for(int i=0; i < numberOfSyncs; i++)
		{
			Button button = new Button(this);
			button.setId(i+1);
			button.setText("NEW Buttons"+(i+1));

			button.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					Toast.makeText(getApplicationContext(), "Button Pressed", Toast.LENGTH_SHORT).show();
				}
			});

			Buttons.addView(button);
		}
		plusButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				State=CHOOSESYNC;
				System.out.println("state changed!" +State);
				if(State==CHOOSESYNC)
				{
					//start afresh! by removing the plus button (then updating the logo) and removing the already established syncs!
					((ViewGroup) findViewById(R.id.headerDiv)).removeView(header);
					Buttons.setVisibility(View.INVISIBLE);
					ImageButton headerBuild= new ImageButton(mcontext);
					//we want the main screen, which draws ImageButtons of the saved syncsIn addition to a plus up top
					headerBuild.setImageResource(R.drawable.header_build);
					headerBuild.setScaleType(ImageView.ScaleType.FIT_CENTER);
					headerBuild.setBackground(null);
					((ViewGroup) findViewById(R.id.headerDiv)).addView(headerBuild);

					header.setOnClickListener(new OnClickListener()
					{
						public void onClick(View v)
						{
							State=MAIN;
						}
					});

					//now let's update the header with the times option and the house option
					ImageButton location = new ImageButton(mcontext);
					location.setBackground(null);
					location.setBackgroundResource(R.drawable.new_place);
					location.setLayoutParams(new LinearLayout.LayoutParams(200, 200));

					ImageButton time = new ImageButton(mcontext);
					time.setBackground(null);
					time.setBackgroundResource(R.drawable.new_time);
					((ViewGroup) findViewById(R.id.headerDiv)).addView(location);
					((ViewGroup) findViewById(R.id.headerDiv)).addView(time);
					time.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
				}



				Toast.makeText(getApplicationContext(), "Button Pressed!", Toast.LENGTH_SHORT).show();
				/*
					Button button = new Button(mcontext);
					button.setId(buttons.length+1);
					button.setText("NEW Button");
					((ViewGroup) findViewById(R.id.headerDiv)).addView(button);
				 */
			}
		});

		//deleteButton = (Button) findViewById(R.id.delete);
		((ViewGroup) findViewById(R.id.relativeLayout)).addView(Buttons);
		setContentView(findViewById(R.id.relativeLayout));
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sync__center, menu);
		return true;
	}

	public void toggleInvisibility(boolean turnOn)
	{
		if(turnOn)
			Buttons.setVisibility(View.INVISIBLE);
		else
			Buttons.setVisibility(View.VISIBLE);

	}

	public void toggleBluetooth(BluetoothAdapter mBluetoothAdapter,boolean turnOn)
	{
		if(turnOn)
		{
			mBluetoothAdapter.enable(); 
		}   
		else
		{
			mBluetoothAdapter.disable();
		}
	}
	//turns on wifi if true, turns off if false
	public void wifiToggle(WifiManager wifiManager, boolean turnOn)
	{
		wifiManager.setWifiEnabled(turnOn);
	}

	public void soundToggle(AudioManager audioManager, int state)
	{
		//For Normal mode
		if(state==-1)
		{
			audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		}

		//For Silent mode
		if(state==0)
		{
			audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		}

		//For Vibrate mode
		if(state==1)
		{
			audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		}
	}

	//sends a text message to a number
	public void sendSMS(SmsManager sms, String phoneNumber, String message)
	{ 
		PendingIntent pi = PendingIntent.getActivity(this, 0,
				new Intent(), 0); 
		sms.sendTextMessage(phoneNumber, null, message, pi, null);

	}

}