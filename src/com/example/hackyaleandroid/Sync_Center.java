package com.example.hackyaleandroid;
import java.util.HashMap;
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
	EditText Text;
	ImageButton plusButton; 
	Button deleteButton;
	EditText title;

	public RadioButton[] getRb() {
		return rb;
	}


	public void setRb(RadioButton[] rb) {
		this.rb = rb;
	}


	public RadioGroup getArriveLeave() {
		return arriveLeave;
	}


	public void setArriveLeave(RadioGroup arriveLeave) {
		this.arriveLeave = arriveLeave;
	}


	public RadioGroup getRgSounds() {
		return rgSounds;
	}


	public void setRgSounds(RadioGroup rgSounds) {
		this.rgSounds = rgSounds;
	}


	public TextView getAddress() {
		return Address;
	}


	public void setAddress(TextView address) {
		Address = address;
	}


	public int getArriveOrLeave() {
		return arriveOrLeave;
	}


	public void setArriveOrLeave(int arriveOrLeave) {
		this.arriveOrLeave = arriveOrLeave;
	}

	final static int MAIN=0,CHOOSESYNC=1, NEWSYNC=2, TIMESYNC=3, PLACESYNC=4;
	static int State = MAIN;
	//temp 
	Button[] buttons = new Button[10];
	
	ImageButton header;
	RadioButton[] rb;
	RadioGroup arriveLeave, rgSounds;
	LinearLayout TextLayout;
	TextView Address;
	int arriveOrLeave, soundSetting;
	boolean wifiSelected, soundSelected, bluetoothSelected, smsSelected;
	String textMessage,phoneNumber;
	int numberOfSyncs;

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

		rb = new RadioButton[8];
		arriveLeave = new RadioGroup(mcontext);
		rgSounds = new RadioGroup(mcontext);
		TextLayout = new LinearLayout(mcontext);
		Address = new TextView(mcontext);
		arriveOrLeave=0;//0 means arrive, 1 means leave
		title=new EditText(mcontext);


		//we want the main screen, which draws ImageButtons of the saved syncsIn addition to a plus up top
		//set up main screen: title, plus to add syncs, and view to see previous ones
		findViewById(R.id.addSyncButton).setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				//switch intent
				Intent intent = new Intent(Sync_Center.this,MenuActivity.class);
				startActivity(intent);
			}	
		});


		for(int i=0; i < numberOfSyncs; i++)
		{
			Button button = new Button(this);
			button.setId(i+1);
			button.setText("NEW Buttons"+(i+1));

			button.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					//switch intent
					Intent intent = new Intent(Sync_Center.this,MenuActivity.class);
					startActivity(intent);
				}
			});

			//((ViewGroup) findViewById(R.id.Buttons)).addView(button);
		}
		
//		plusButton.setOnClickListener(new OnClickListener()
//		{
//			public void onClick(View v)
//			{
//				State=CHOOSESYNC;
//				System.out.println("state changed!" +State);
//				if(State==CHOOSESYNC)
//				{
//					//start afresh! by removing the plus button (then updating the logo) and removing the already established syncs!
//					((ViewGroup) findViewById(R.id.Buttons)).removeView(plusButton);
//					for (int i=0;i<numberOfSyncs;i++)
//					{
//						((ViewGroup) findViewById(R.id.Buttons)).removeView(findViewById(i+1));
//					}
//
//					//draw the new header
//					ImageButton headerBuild= new ImageButton(mcontext);
//					headerBuild.setImageResource(R.drawable.header_build);
//					headerBuild.setScaleType(ImageView.ScaleType.FIT_CENTER);
//					headerBuild.setBackground(null);
//
//					header.setOnClickListener(new OnClickListener()
//					{
//						public void onClick(View v)
//						{
//							State=MAIN;
//						}
//					});
//
//					//now let's update Buttons with the times option and the house option
//					final ImageButton location = new ImageButton(mcontext);
//					location.setBackground(null);
//					location.setBackgroundResource(R.drawable.new_place);
//					location.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
//
//					final ImageButton spacer = new ImageButton(mcontext);
//					spacer.setBackground(null);
//					spacer.setBackgroundResource(R.drawable.new_time);
//					spacer.setVisibility(View.INVISIBLE);
//					final ImageButton spacer2 = new ImageButton(mcontext);
//					spacer2.setBackground(null);
//					spacer2.setBackgroundResource(R.drawable.new_time);
//					spacer2.setVisibility(View.INVISIBLE);
//
//					final ImageButton time = new ImageButton(mcontext);
//					time.setBackground(null);
//					time.setBackgroundResource(R.drawable.new_time);
//					time.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
//					((ViewGroup) findViewById(R.id.Buttons)).addView(location);
//					((ViewGroup) findViewById(R.id.Buttons)).addView(spacer);
//					((ViewGroup) findViewById(R.id.Buttons)).addView(spacer2);
//					//((ViewGroup) findViewById(R.id.Buttons)).addView(time);
//
//					location.setOnClickListener(new OnClickListener()
//					{
//						public void onClick(View v)
//						{
//							//title text, location text , trigger radio, radius int , actions check box
//
//							//clear the previous stuff
//							((ViewGroup) findViewById(R.id.Buttons)).removeView(location);
//							((ViewGroup) findViewById(R.id.Buttons)).removeView(spacer);
//							((ViewGroup) findViewById(R.id.Buttons)).removeView(spacer2);
//							((ViewGroup) findViewById(R.id.Buttons)).removeView(time);
//
//							//title text
//
//							TextLayout.setOrientation(LinearLayout.VERTICAL);
//							
//							TextView label = new TextView(mcontext);
//							title.setBackgroundColor(Color.GRAY);
//							LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//
//							label.setText("Sync Name: ");
//							TextLayout.addView(label, layoutParams);
//							TextLayout.addView(title,layoutParams);
//							TextView spacer = new TextView(mcontext);
//							spacer.setVisibility(View.INVISIBLE);
//							TextLayout.addView(spacer,layoutParams);
//
//							//Location text
//
//							Address.setText("Address: ");
//							EditText address=new EditText(mcontext);
//							address.setBackgroundColor(Color.GRAY);
//							TextLayout.addView(Address,layoutParams);
//							TextLayout.addView(address,layoutParams);
//							((ViewGroup) findViewById(R.id.Buttons)).addView(TextLayout);
//
//							//trigger (arriving or leaving)
//							arriveLeave.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
//
//							rb[0]  = new RadioButton(mcontext);
//							arriveLeave.addView(rb[0]); //the RadioButtons are added to the radioGroup instead of the layout
//							rb[0].setText("Arriving");
//							rb[0].setChecked(true);
//
//							rb[1]  = new RadioButton(mcontext);
//							arriveLeave.addView(rb[1]); //the RadioButtons are added to the radioGroup instead of the layout
//							rb[1].setText("Leaving");
//							rb[1].setChecked(false);
//
//							((ViewGroup) findViewById(R.id.Buttons)).addView(arriveLeave);//you add the whole RadioGroup to the layout   
//							arriveLeave.setOnClickListener(new OnClickListener() {
//								public void onClick(View v) {
//									getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//									if(rb[0].isChecked())
//									{
//										//Arriving
//										arriveOrLeave=0;
//									}
//									else
//									{
//										//leaving
//										arriveOrLeave=1;
//									}
//								}
//							});  
//
//							final CheckBox wifi = new CheckBox(mcontext);	
//							wifi.setText("Turn Wifi On");
//							final CheckBox sound = new CheckBox(mcontext);
//							sound.setText("Change Sound");
//							final CheckBox bluetooth = new CheckBox(mcontext);
//							bluetooth.setText("Turn Bluetooth On");
//							final CheckBox sms = new CheckBox(mcontext);	
//							sms.setText("Send Text Message");
//
//							((ViewGroup) findViewById(R.id.Buttons)).addView(sms);
//							((ViewGroup) findViewById(R.id.Buttons)).addView(bluetooth);
//							((ViewGroup) findViewById(R.id.Buttons)).addView(sound);
//							((ViewGroup) findViewById(R.id.Buttons)).addView(wifi);
//
//							wifi.setOnClickListener(new OnClickListener() {
//								public void onClick(View v) 
//								{	
//									if(wifi.isSelected())
//									{
//										setWifiSelected(true);
//									}
//									else
//									{
//										setWifiSelected(false);
//									}
//								}
//							});
//
//							bluetooth.setOnClickListener(new OnClickListener() {
//								public void onClick(View v) 
//								{	
//									if(wifi.isSelected())
//									{
//										setBluetoothSelected(true);
//									}
//									else
//									{
//										setBluetoothSelected(false);
//									}
//								}
//							});
//
//							sms.setOnClickListener(new OnClickListener() {
//								public void onClick(View v) 
//								{
//									LinearLayout textMessageLayout=new LinearLayout(mcontext);
//									textMessageLayout.setOrientation(LinearLayout.VERTICAL);
//									EditText smsMessage=new EditText(mcontext);
//									TextView smsMessageLabel = new TextView(mcontext);
//									EditText phoneNumbers=new EditText(mcontext);
//									TextView phoneNumberLabel = new TextView(mcontext);
//									if(sms.isChecked())
//									{
//										smsSelected=true;
//										smsMessageLabel.setText("Text Message");
//										smsMessage.setBackgroundColor(Color.GRAY);
//										phoneNumberLabel.setText("Phone Number");
//										phoneNumbers.setBackgroundColor(Color.GRAY);
//										LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//										textMessageLayout.addView(phoneNumberLabel,layoutParams);
//										textMessageLayout.addView(phoneNumbers,layoutParams);
//										textMessageLayout.addView(smsMessageLabel,layoutParams);
//										textMessageLayout.addView(smsMessage,layoutParams);
//										((ViewGroup) findViewById(R.id.Buttons)).addView(textMessageLayout);
//										textMessage=smsMessage.getText().toString();
//										phoneNumber=phoneNumbers.getText().toString();
//									}
//									else if (!sms.isChecked())
//									{
//										textMessageLayout.setVisibility(View.GONE);
//										textMessageLayout.setVisibility(View.INVISIBLE);
//										((ViewGroup) findViewById(R.id.Buttons)).removeView(textMessageLayout);
//										textMessage="";
//										phoneNumber="";
//										smsSelected=false;
//
//									}
//								}
//							});
//
//
//							sound.setOnClickListener(new OnClickListener() {
//								public void onClick(View v) 
//								{	
//									if(sound.isChecked())
//									{
//										soundSelected=true;
//										rgSounds.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
//
//										rb[2]  = new RadioButton(mcontext);
//										rgSounds.addView(rb[2]); //the RadioButtons are added to the radioGroup instead of the layout
//										rb[2].setText("Silent");
//										rb[2].setChecked(false);
//
//										rb[3]  = new RadioButton(mcontext);
//										rgSounds.addView(rb[3]); //the RadioButtons are added to the radioGroup instead of the layout
//										rb[3].setText("Vibrate");
//										rb[3].setChecked(false);
//
//										rb[4]  = new RadioButton(mcontext);
//										rgSounds.addView(rb[4]); //the RadioButtons are added to the radioGroup instead of the layout
//										rb[4].setText("Normal");
//										rb[4].setChecked(false);
//
//										rb[5]  = new RadioButton(mcontext);
//										rgSounds.addView(rb[5]); //the RadioButtons are added to the radioGroup instead of the layout
//										rb[5].setText("Loud");
//										rb[5].setChecked(false);
//
//										((ViewGroup) findViewById(R.id.Buttons)).addView(rgSounds);//you add the whole RadioGroup to the layout   
//										rgSounds.setOnClickListener(new OnClickListener()
//										{
//											public void onClick(View v)
//											{
//
//												if(rb[0].isChecked())
//												{
//													//silent
//													soundSetting=0;
//												}
//												if(rb[1].isChecked())
//												{
//													//vibrate
//													soundSetting=1;
//												}
//												if(rb[2].isChecked())
//												{
//													//normal
//													soundSetting=2;
//												}
//												if(rb[3].isChecked())
//												{
//													//loud
//													soundSetting=3;
//												}
//											}
//										});  
//									}
//									else if (!sound.isChecked())
//									{
//										soundSelected=false;
//										((ViewGroup) findViewById(R.id.Buttons)).removeView(rgSounds);
//									}
//								}
//							});
//							Button submit = new Button(mcontext);
//							submit.setText("SUBMIT");
//							((ViewGroup) findViewById(R.id.Buttons)).addView(submit);
//							submit.setOnClickListener(new OnClickListener()
//							{
//								public void onClick(View v)
//								{
//									LocationSync sync = new LocationSync(Sync_Center.this,title.getText().toString(), wifiSelected, bluetoothSelected, smsSelected, soundSetting, new TextMessage(phoneNumber,textMessage), Address.getText().toString(), 40, Geofence.GEOFENCE_TRANSITION_ENTER);
//									currentLocationSyncs.put(title.getText().toString(), sync);
//							//		Toast.makeText(mcontext, "Submitted!", 10);
//									//we want the main screen, which draws ImageButtons of the saved syncsIn addition to a plus up top
//									//((ViewGroup) findViewById(R.id.relativeLayout)).removeViewInLayout(buttons);
//									header.setImageResource(R.drawable.header_logo);
//									header.setScaleType(ImageView.ScaleType.FIT_CENTER);
//									header.setBackground(null);
//									/*
//									((ViewGroup) findViewById(R.id.headerDiv)).addView(header);
//									//ImageButton plusButton1 = new ImageButton(this);
//									plusButton1.setBackgroundResource(R.drawable.circ_plus);
//									plusButton1.setVisibility(View.INVISIBLE);
//									//ImageButton plusButton2 = new ImageButton(this);
//									plusButton2.setBackgroundResource(R.drawable.circ_plus);
//									((ViewGroup) findViewById(R.id.Buttons)).addView(plusButton2);
//									plusButton2.setVisibility(View.INVISIBLE);
//
//									plusButton = new ImageButton(mcontext);
//									plusButton.setBackground(null);
//									plusButton.setBackgroundResource(R.drawable.circ_plus);
//									plusButton.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
//
//
//									((ViewGroup) findViewById(R.id.Buttons)).addView(plusButton);
//									*/
//									for(int i=0; i < numberOfSyncs; i++)
//									{
//										Button button = new Button(mcontext);
//										button.setId(i+1);
//										button.setText("NEW Buttons"+(i+1));
//
//										button.setOnClickListener(new OnClickListener()
//										{
//											public void onClick(View v)
//											{
//												
//											}
//										});
//
//										((ViewGroup) findViewById(R.id.Buttons)).addView(button);
//									}
//									plusButton.setOnClickListener(new OnClickListener()
//									{
//										public void onClick(View v)
//										{
//											State=CHOOSESYNC;
//											System.out.println("state changed!" +State);
//											if(State==CHOOSESYNC)
//											{
//												//start afresh! by removing the plus button (then updating the logo) and removing the already established syncs!
//												((ViewGroup) findViewById(R.id.Buttons)).removeView(plusButton);
//												for (int i=0;i<numberOfSyncs;i++)
//												{
//													((ViewGroup) findViewById(R.id.Buttons)).removeView(findViewById(i+1));
//												}
//
//												//draw the new header
//												ImageButton headerBuild= new ImageButton(mcontext);
//												headerBuild.setImageResource(R.drawable.header_build);
//												headerBuild.setScaleType(ImageView.ScaleType.FIT_CENTER);
//												headerBuild.setBackground(null);
//											}
//										}
//									});
//									
//										
//								}
//							});
//						}
//					});
//				}
//			}
//		});

		//deleteButton = (Button) findViewById(R.id.delete);
		//((ViewGroup) findViewById(R.id.relativeLayout)).addView(Buttons);
		//setContentView(findViewById(R.id.relativeLayout));
	}


	public EditText getText() {
		return Text;
	}


	public void setText(EditText text) {
		Text = text;
	}


	public LinearLayout getTextLayout() {
		return TextLayout;
	}


	public void setTextLayout(LinearLayout textLayout) {
		TextLayout = textLayout;
	}


	public boolean isWifiSelected() {
		return wifiSelected;
	}


	public void setWifiSelected(boolean wifiSelected) {
		this.wifiSelected = wifiSelected;
	}


	public boolean isSoundSelected() {
		return soundSelected;
	}


	public void setSoundSelected(boolean soundSelected) {
		this.soundSelected = soundSelected;
	}


	public boolean isBluetoothSelected() {
		return bluetoothSelected;
	}


	public void setBluetoothSelected(boolean bluetoothSelected) {
		this.bluetoothSelected = bluetoothSelected;
	}


	public boolean isSmsSelected() {
		return smsSelected;
	}


	public void setSmsSelected(boolean smsSelected) {
		this.smsSelected = smsSelected;
	}


	public String getTextMessage() {
		return textMessage;
	}


	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}

}