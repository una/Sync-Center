package com.example.hackyaleandroid;

import java.util.Calendar;
import java.util.List;

import android.app.AlarmManager;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;

public class Sync {

	private SimpleGeofence msimpleGeofence;
	private int mtransition;
	
	private String mName;
	public Boolean mchangeWifi, mchangeBluetooth, mchangeVolume, musingGF;
	private Calendar mSyncCal;
	private AlarmManager malarmManager;
	
	//constructor
	public Sync(String Name, Boolean changeWifi, Boolean changeBluetooth, changeVolume, usingGF, String locationName, String Address, float radius,int transition)
	{
		mName = Name;
		mchangeWifi = changeWifi;
		mchangeBluetooth = changeBluetooth;
		mchangeVolume = changeVolume;
		musingGF = musingGF;
		
		if(musingGF)
		{
			//call GeoFence ops
		}
	}
	
	   public boolean CreateGeoFence(String Name, String strAddress, float radius, int transition)
	      {
	    	  Geocoder coder = new Geocoder(this);
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
	    		  
	        	  //add to geoFenceStore and dictionary
	    		  mPrefs.setGeofence(Name, myFence);
	    		  CurrentFences.put(Name, myFence);
	    		  
	    		  //start request
	    		  try {
	    	            // Try to add geofences
	    	            mGeofenceRequester.addGeofences(mCurrentGeofences);
	    	        } catch (UnsupportedOperationException e) {
	    	        	System.out.println("last reuqest hasn't processed");
	    	        }
	    		  return true;
	    		  }
	    	  
	    	  return false;
	    	  
	    	  
	      }
	   
	   public boolean servicesConnected() {
           // Check that Google Play services is available
           int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
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
}
