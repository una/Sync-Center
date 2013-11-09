package com.example.hackyaleandroid;

import android.location.Address;
import android.location.Geocoder;

public class LocationSync extends Sync {

	SimpleGeofence msimpleGeofence;
	
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
}
