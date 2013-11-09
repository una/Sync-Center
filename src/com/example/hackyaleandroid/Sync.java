package com.example.hackyaleandroid;

import java.util.Calendar;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.telephony.SmsManager;
import android.text.format.Time;

public class Sync {

    
    //GEOFENCING STUFF 
    private static final long GEOFENCE_EXPIRATION = Geofence.NEVER_EXPIRE;
    
	private SimpleGeofence msimpleGeofence;
	private String mName;
	public Boolean mchangeWifi, mchangeBluetooth, musingGF, msendText;
	public int mchangeVolume;
	private AlarmManager malarmManager;
	private TextMessage mtext;
	
	//constructor
	public Sync(String Name, 
			Boolean changeWifi, 
			Boolean changeBluetooth, 
			int changeVolume,
			Boolean usingGF, 
			String locationName, 
			String Address, 
			float radius,
			int transition,
			Week week, 
			Time time,
			TextMessage text)
	{
		mName = Name;  
		mchangeWifi = changeWifi;
		mchangeBluetooth = changeBluetooth;
		mchangeVolume = changeVolume;
		musingGF = usingGF;
		mtext = text;
		if(musingGF)
		{
			//call GeoFence ops
			if (CreateGeoFence(locationName, Address, radius, transition))
				System.out.println("JOSH IS THE MAN");
		}
		
		createAlarmManager(week, time);
	}
	
	private void createAlarmManager(Week week, Time time)
	{
		
	}
	   public boolean CreateGeoFence(String Name, String strAddress, float radius, int transition)
	      {
	    	  Geocoder coder = new Geocoder(Sync_Center.mcontext);
	    	  double lat, lng;
	    	  //check that we have google-play service
	    	  if (servicesConnected())
	    		  {
	    		//get lat and lng of location
	    		  try {
	    			  List<Address> results = coder.getFromLocationName(strAddress,1);
	    			    if (results == null) {
	    			        return false;
	    			    }
	    			    Address location = results.get(0);
	    			    lat = location.getLatitude();
	    			    lng = location.getLongitude();
	    			}
	    		  catch(Exception ex)
	    		  {
	    			  return false;
	    		  }
	        	  //create SimpleGeoFence
	    		  SimpleGeofence myFence = new SimpleGeofence(Name,lat,lng,radius,GEOFENCE_EXPIRATION, transition);
	    		  
	    		  msimpleGeofence = myFence;
	    		  
	    		  //start request
	    		  try {
	    	            // Try to request geofence service
	    	        } catch (UnsupportedOperationException e) {
	    	        	System.out.println("last reuqest hasn't processed");
	    	        }
	    		  return true;
	    		  }
	    	  
	    	  return false;
	    	  
	    	  
	      }
	   
	   public boolean servicesConnected() {
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

   private void performActions()
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
