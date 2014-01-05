package com.example.hackyaleandroid;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationStatusCodes;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

public class LocationSync extends Sync implements
ConnectionCallbacks,
OnConnectionFailedListener,
OnAddGeofencesResultListener {
	Context context;
	Geofence myFence;
	private LocationClient mLocationClient;

	private PendingIntent mGeofenceRequestIntent;
	// Flag that indicates if a request is underway.
	private boolean mInProgress;

	public LocationSync(Context contextin,	
			String mName, String Address, int Radius,
			boolean changeWifi, boolean wifiOn,
			boolean changeBlueTooth, boolean blueToothOn,
			boolean volumeOn, int volume, boolean vibrateOn, 
			boolean textOn, TextMessage text,
			int transition
			)
	{
		super(mName, changeWifi, wifiOn, changeBlueTooth, blueToothOn, volumeOn, volume, vibrateOn, textOn, text);
		context = contextin;
		mInProgress = false;
		if(servicesConnected())
			if(CreateGeoFence(mName, Address, Radius, transition))
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
		Geocoder coder = new Geocoder(context);
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

	public void addGeofences() {
		/*
		 * Test for Google Play services after setting the request type.
		 * If Google Play services isn't present, the proper request
		 * can be restarted.
		 */
		if (!servicesConnected()) {
			return;
		}
		/*
		 * Create a new location client object. Since the current
		 * activity class implements ConnectionCallbacks and
		 * OnConnectionFailedListener, pass the current activity object
		 * as the listener for both parameters
		 */
		mLocationClient = new LocationClient(Sync_Center.mcontext,this,this);
		// If a request is not already underway
		if (!mInProgress) {
			// Indicate that a request is underway
			mInProgress = true;
			// Request a connection from the client to Location Services
			mLocationClient.connect();
		} else {
			/*
			 * A request is already underway. You can handle
			 * this situation by disconnecting the client,
			 * re-setting the flag, and then re-trying the
			 * request.
			 */
			mLocationClient.disconnect();
			mInProgress = false;
			addGeofences();
		}
	}

	@Override
	public void onAddGeofencesResult(int arg0, String[] arg1) {
		if (LocationStatusCodes.SUCCESS == arg0) {
			System.out.println("successfully added geofence");
		} else {
			System.out.println("ERROR: could not add fence");
		}
		// Turn off the in progress flag and disconnect the client
		mInProgress = false;
		mLocationClient.disconnect();

	}
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onConnected(Bundle connectionHint) {
		// Get the PendingIntent for the request
		mGeofenceRequestIntent =
				getTransitionPendingIntent();
		// Send a request to add the current geofences
		ArrayList<Geofence> fences = new ArrayList<Geofence>();
		fences.add(myFence);
		mLocationClient.addGeofences(
				fences, mGeofenceRequestIntent, this);

	}
	@Override
	public void onDisconnected() {
		// Turn off the request flag
		mInProgress = false;
		// Destroy the current location client
		mLocationClient = null;

	}
}