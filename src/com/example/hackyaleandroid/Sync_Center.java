package com.example.hackyaleandroid;

import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.Menu;
import android.widget.TextView;

public class Sync_Center extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync__center);
        Context mcontext = getBaseContext() ;
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        WifiManager wifiManager = (WifiManager) mcontext.getSystemService(Context.WIFI_SERVICE);
		AudioManager audioManager = (AudioManager) mcontext.getSystemService(Context.AUDIO_SERVICE);
		SmsManager sms = SmsManager.getDefault();
        
        TextView text = new TextView(this);
        text.setText("hackYale");
        setContentView(text);

        
       //wifiToggle(wifiManager, false);
       //toggleBluetooth(mBluetoothAdapter, true);
       sendSMS(sms, "2406883556","hello");
    }

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
