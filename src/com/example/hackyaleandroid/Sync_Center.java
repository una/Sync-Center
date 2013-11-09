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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

	final static int MAIN=0,CHOOSESYNC=1, NEWSYNC=2, TIMESYNC=3, PLACESYNC=4;
	int State = MAIN;
	//temp 
	Button[] buttons = new Button[10];  

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

		ImageView header= new ImageView(mcontext);
		switch(State)
		{
		//we want the main screen, which draws ImageButtons of the saved syncsIn addition to a plus up top
		case MAIN:
		{
			header.setImageResource(R.drawable.header_logo);
			((ViewGroup) findViewById(R.id.headerDiv)).addView(header);

			plusButton = new ImageButton(mcontext);
			plusButton.setBackground(null);
			plusButton.setBackgroundResource(R.drawable.circ_plus);
			plusButton.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
			((ViewGroup) findViewById(R.id.headerDiv)).addView(plusButton);


			//TODO: the 2 will be replaced by the number of previously stored thingies
			for(int i=0; i < 2; i++)
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

				((ViewGroup) findViewById(R.id.headerDiv)).addView(button);
			}
			plusButton.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					State=CHOOSESYNC;
					
					Toast.makeText(getApplicationContext(), "Button Pressed", Toast.LENGTH_SHORT).show();
					/*
					Button button = new Button(mcontext);
					button.setId(buttons.length+1);
					button.setText("NEW Button");
					((ViewGroup) findViewById(R.id.headerDiv)).addView(button);
					 */
				}
			});
			break;
		}
		case CHOOSESYNC:
		{
			header.setImageResource(R.drawable.header_build);
			((ViewGroup) findViewById(R.id.headerDiv)).addView(header);

			LinearLayout chooseType =new LinearLayout(mcontext);
			chooseType.setOrientation(LinearLayout.HORIZONTAL);

			ImageButton location = new ImageButton(mcontext);
			location.setBackground(null);
			location.setBackgroundResource(R.drawable.little_house);

			ImageButton time = new ImageButton(mcontext);
			time.setBackground(null);
			time.setBackgroundResource(R.drawable.little_clock);
			chooseType.addView(location);
			chooseType.addView(time);
		}
		}



		//deleteButton = (Button) findViewById(R.id.delete);



		setContentView(findViewById(R.id.relativeLayout));
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sync__center, menu);
		return true;
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