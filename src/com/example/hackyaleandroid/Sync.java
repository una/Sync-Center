package com.example.hackyaleandroid;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.bluetooth.BluetoothAdapter;

import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.telephony.SmsManager;
import android.text.format.Time;

abstract class Sync {

	private String mName;
	public Boolean mchangeWifi, mchangeBluetooth, musingGF, msendText;
	public int mchangeVolume;
	private TextMessage mtext;
	
	//constructor
	public Sync()
	{
		
	}
	
	
	public static  boolean servicesConnected() {
       // Check that Google Play services is available
       int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(Sync_Center.mcontext);
       // If Google Play services is available
       if (ConnectionResult.SUCCESS == resultCode) 
       {
               // In debug mode, log the status
               System.out.println("Geofence Detection Google Play services is available.");
               // Continue
               return true;
               // Google Play services was not available for some reason
       }
       else
       {
               //TODO: We werent able to connect!
               System.out.println("we failed!");
               return false;
       }
   }

   public void performActions()
   {
	   if(mchangeWifi)
	   {
		   wifiToggle(Sync_Center.mwifiManager, false);
	   }
	   soundToggle(Sync_Center.maudioManager,mchangeVolume);
	   if(mchangeBluetooth)
	   {
		   toggleBluetooth(Sync_Center.mBluetoothAdapter, false);
	   }
	   if(msendText)
	   {
		   sendSMS(Sync_Center.msms,mtext.mnumber,mtext.mmessage);
	   }
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
     
       sms.sendTextMessage(phoneNumber, null, message, null, null);
       
     }
}
