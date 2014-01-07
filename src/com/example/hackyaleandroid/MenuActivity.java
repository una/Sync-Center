package com.example.hackyaleandroid;

import com.google.android.gms.location.Geofence;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.CheckBox;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MenuActivity extends Sync_Center {
	private String syncTitle, syncLocation, syncPhoneNumber, syncTextMessage;
	private boolean syncWifiSelected, syncBluetoothSelected, syncBluetooth, syncSmsSelected, syncWifi, syncSms, syncVolumeOn, syncVibrateOn;
	private int syncRadius, syncSoundSetting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Sync_Center.buildType==1)
		{
			setContentView(R.layout.add_place_sync_layout);

			//set up form elements to be hidden until clicked on
			findViewById(R.id.sendTextRecipient).setVisibility(View.GONE);
			findViewById(R.id.sendTextInput).setVisibility(View.GONE);
			findViewById(R.id.textMessage).setVisibility(View.GONE);
			findViewById(R.id.textRecipient).setVisibility(View.GONE);
			findViewById(R.id.wifiToggle).setVisibility(View.GONE);
			findViewById(R.id.bluetoothToggle).setVisibility(View.GONE);
			findViewById(R.id.ringerRadioGroup).setVisibility(View.GONE);
			findViewById(R.id.silentText).setVisibility(View.GONE);
			findViewById(R.id.vibrateCheckBox).setVisibility(View.GONE);
			findViewById(R.id.vibrateText).setVisibility(View.GONE);
			findViewById(R.id.normalText).setVisibility(View.GONE);

			//set up the number picker
			((NumberPicker) findViewById(R.id.radiusOfSyncInput)).setMinValue(1);
			((NumberPicker) findViewById(R.id.radiusOfSyncInput)).setMaxValue(100);

			//set up the submit button
			findViewById(R.id.submitButton).setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					syncTitle=((EditText) findViewById(R.id.nameOfSyncInput)).getText().toString();
					createSync(syncTitle, syncLocation, syncRadius, syncWifiSelected, syncBluetoothSelected,
							syncBluetooth, syncSmsSelected, syncWifi, syncSms, syncVolumeOn, syncSoundSetting, syncVibrateOn, syncPhoneNumber, syncTextMessage);
					finish();  
				}
			});


			//If check boxes are ticked, make their respective elements show up
			findViewById(R.id.sendTextCheckBox).setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					if(((CheckBox) findViewById(R.id.sendTextCheckBox)).isChecked())
					{
						findViewById(R.id.sendTextRecipient).setVisibility(View.VISIBLE);
						findViewById(R.id.sendTextInput).setVisibility(View.VISIBLE);
						findViewById(R.id.textMessage).setVisibility(View.VISIBLE);
						findViewById(R.id.textRecipient).setVisibility(View.VISIBLE);
					}

					else
					{
						findViewById(R.id.sendTextRecipient).setVisibility(View.GONE);
						findViewById(R.id.sendTextInput).setVisibility(View.GONE);	
						findViewById(R.id.textMessage).setVisibility(View.GONE);
						findViewById(R.id.textRecipient).setVisibility(View.GONE);
					}
				}
			});

			findViewById(R.id.wifiCheckBox).setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					if(((CheckBox) findViewById(R.id.wifiCheckBox)).isChecked())
					{
						findViewById(R.id.wifiToggle).setVisibility(View.VISIBLE);
					}
					else
					{
						findViewById(R.id.wifiToggle).setVisibility(View.GONE);
					}
				}
			});

			findViewById(R.id.bluetoothCheckBox).setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					if(((CheckBox) findViewById(R.id.bluetoothCheckBox)).isChecked())
					{
						findViewById(R.id.bluetoothToggle).setVisibility(View.VISIBLE);
					}
					else
					{
						findViewById(R.id.bluetoothToggle).setVisibility(View.GONE);
					}
				}
			});

			findViewById(R.id.ringerCheckBox).setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					if(((CheckBox) findViewById(R.id.ringerCheckBox)).isChecked())
					{
						findViewById(R.id.ringerRadioGroup).setVisibility(View.VISIBLE);
						findViewById(R.id.silentText).setVisibility(View.VISIBLE);
						findViewById(R.id.vibrateCheckBox).setVisibility(View.VISIBLE);
						findViewById(R.id.vibrateText).setVisibility(View.VISIBLE);
						findViewById(R.id.normalText).setVisibility(View.VISIBLE);
					}

					else
					{
						findViewById(R.id.ringerRadioGroup).setVisibility(View.GONE);
						findViewById(R.id.silentText).setVisibility(View.GONE);
						findViewById(R.id.vibrateCheckBox).setVisibility(View.GONE);
						findViewById(R.id.vibrateText).setVisibility(View.GONE);
						findViewById(R.id.normalText).setVisibility(View.GONE);
					}
				}
			});
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
