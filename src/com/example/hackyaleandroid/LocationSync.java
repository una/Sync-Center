package com.example.hackyaleandroid;

import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;

import android.app.PendingIntent;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

public class LocationSync extends Sync implements
ConnectionCallbacks,
OnConnectionFailedListener,
OnAddGeofencesResultListener {

	Geofence myFence;
	PendingIntent myIntent;

	public LocationSync(String nName, 
			boolean changeWifi, 
			boolean changeBlueTooth, 
			int volume, 
			TextMessage text,
			String Address,
			float radius,
			int transition)
	{
		super(nName, changeWifi, changeBlueTooth,volume, text);
		if(servicesConnected())
			if(CreateGeoFence(nName, address, radius, transition))
			{
				System.out.println("got Geofence");
				
			}
	}
	private PendingIntent getTransitionPendingIntent()
	{
		// Create an explicit Intent
        Intent intent = new Intent(Sync_Center.mcontext,
                ReceiveTransitionsIntentService.class);
        /*
         * Return the PendingIntent
         */
        return PendingIntent.getService(
                Sync_Center.mcontext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
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
  			    
  			  myFence = new Geofence.Builder().setCircularRegion(lat, lng, radius).setExpirationDuration(Geofence.NEVER_EXPIRE).setRequestId(Name).setTransitionTypes(transition).build();  		  
  	  		  
  			}
  		  catch(Exception ex)
  		  {
  			  return false;
  		  }
  		  
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
	@Override
	public void onAddGeofencesResult(int arg0, String[] arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
}
