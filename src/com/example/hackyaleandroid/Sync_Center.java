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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

		((ViewGroup) findViewById(R.id.headerDiv)).addView(header);
		ImageButton plusButton1 = new ImageButton(this);
		plusButton1.setBackgroundResource(R.drawable.circ_plus);
		plusButton1.setVisibility(View.INVISIBLE);
		((ViewGroup) findViewById(R.id.Buttons)).addView(plusButton1);
		ImageButton plusButton2 = new ImageButton(this);
		plusButton2.setBackgroundResource(R.drawable.circ_plus);
		((ViewGroup) findViewById(R.id.Buttons)).addView(plusButton2);
		plusButton2.setVisibility(View.INVISIBLE);

		plusButton = new ImageButton(mcontext);
		plusButton.setBackground(null);
		plusButton.setBackgroundResource(R.drawable.circ_plus);
		plusButton.setLayoutParams(new LinearLayout.LayoutParams(100, 100));


		((ViewGroup) findViewById(R.id.Buttons)).addView(plusButton);

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

			((ViewGroup) findViewById(R.id.Buttons)).addView(button);
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
					((ViewGroup) findViewById(R.id.Buttons)).removeView(plusButton);
					for (int i=0;i<numberOfSyncs;i++)
					{
						((ViewGroup) findViewById(R.id.Buttons)).removeView(findViewById(i+1));
					}

					//draw the new header
					ImageButton headerBuild= new ImageButton(mcontext);
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

					//now let's update Buttons with the times option and the house option
					final ImageButton location = new ImageButton(mcontext);
					location.setBackground(null);
					location.setBackgroundResource(R.drawable.new_place);
					location.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT-300,1.0f));
					
					final ImageButton spacer = new ImageButton(mcontext);
					spacer.setBackground(null);
					spacer.setBackgroundResource(R.drawable.new_time);
					spacer.setVisibility(View.INVISIBLE);
					
					final ImageButton time = new ImageButton(mcontext);
					time.setBackground(null);
					time.setBackgroundResource(R.drawable.new_time);
					time.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT-300,1.0f));
					((ViewGroup) findViewById(R.id.Buttons)).addView(location);
					((ViewGroup) findViewById(R.id.Buttons)).addView(spacer);
					((ViewGroup) findViewById(R.id.Buttons)).addView(time);
					
					location.setOnClickListener(new OnClickListener()
					{
						public void onClick(View v)
						{
							//title text, location text , trigger radio, radius int , actions check box
							
							//clear the previous stuff
							((ViewGroup) findViewById(R.id.Buttons)).removeView(location);
							((ViewGroup) findViewById(R.id.Buttons)).removeView(spacer);
							((ViewGroup) findViewById(R.id.Buttons)).removeView(time);
							
							//title text
							EditText title=new EditText(mcontext);
							title.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
							((ViewGroup) findViewById(R.id.Buttons)).addView(title);
							
							//Location text
							EditText address=new EditText(mcontext);
							address.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
							((ViewGroup) findViewById(R.id.Buttons)).addView(address);
							
							
							//trigger
							final RadioButton[] rb = new RadioButton[5];
						    RadioGroup rg = new RadioGroup(mcontext); //create the RadioGroup
						    rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
						    
						    
						    rb[0]  = new RadioButton(mcontext);
						    rg.addView(rb[0]); //the RadioButtons are added to the radioGroup instead of the layout
						    rb[0].setText("Arriving");
						    
						    rb[1]  = new RadioButton(mcontext);
						    rg.addView(rb[0]); //the RadioButtons are added to the radioGroup instead of the layout
						    rb[1].setText("Leaving");
						    rb[2]  = new RadioButton(mcontext);
						    rg.addView(rb[0]); //the RadioButtons are added to the radioGroup instead of the layout
						    rb[2].setText("Leaving");
						    
						    ((ViewGroup) findViewById(R.id.Buttons)).addView(rg);//you add the whole RadioGroup to the layout
						    ((ViewGroup) findViewById(R.id.Buttons)).addView(submit); 
						    submit.setOnClickListener(new View.OnClickListener() {
						        public void onClick(View v) {
						            for(int i = 0; i < 5; i++) { 
						                rg.removeView(rb[i]);//now the RadioButtons are in the RadioGroup
						            }  
						            ll.removeView(submit);
						            Questions();
						        }
						    });  
							
							//et.getText().toString();
						}
					});
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
					//((ViewGroup) findViewById(R.id.relativeLayout)).addView(Buttons);
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