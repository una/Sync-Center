package com.example.hackyaleandroid;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.hackyaleandroid.GeofenceUtils.REQUEST_TYPE;

import android.location.Address;
import android.location.Geocoder;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

import com.example.hackyaleandroid.GeofenceUtils.REMOVE_TYPE;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;


public class Sync_Center extends Activity 
{
        
    EditText text;
    ImageButton plusButton; 
    Button deleteButton;
    
    //temp 
    Button[] buttons = new Button[10];  
    
    //GEOFENCING STUFF 
    private static final long GEOFENCE_EXPIRATION = Geofence.NEVER_EXPIRE;

   // Store the current request
   private REQUEST_TYPE mRequestType;

   // Store the current type of removal
   private REMOVE_TYPE mRemoveType;

   // Persistent storage for geofences
   private SimpleGeofenceStore mPrefs;

   // Store a list of geofences to add
   List<Geofence> mCurrentGeofences;

   // Add geofences handler
   private GeofenceRequester mGeofenceRequester;
   // Remove geofences handler
   private GeofenceRemover mGeofenceRemover;
   // decimal formats for latitude, longitude, and radius
   private DecimalFormat mLatLngFormat;
   private DecimalFormat mRadiusFormat;

   /*
    * An instance of an inner class that receives broadcasts from listeners and from the
    * IntentService that receives geofence transition events
    */
   private GeofenceSampleReceiver mBroadcastReceiver;

   // An intent filter for the broadcast receiver
   private IntentFilter mIntentFilter;

   private HashMap<String, SimpleGeofence> CurrentFences;
   // Store the list of geofences to remove
   private List<String> mGeofenceIdsToRemove;
        
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync__center);
        Context mcontext = getBaseContext() ;
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        WifiManager wifiManager = (WifiManager) mcontext.getSystemService(Context.WIFI_SERVICE);
	    AudioManager audioManager = (AudioManager) mcontext.getSystemService(Context.AUDIO_SERVICE);
	    geofenceInit();
	    SmsManager sms = SmsManager.getDefault();
        TextView text = new TextView(this);
        text.setText("hackYale");
        
        plusButton = (ImageButton) findViewById(R.id.plus);
        plusButton.setOnClickListener(new OnClickListener()
        {
                public void onClick(View v)
                {
                        Toast.makeText(getApplicationContext(), "Button Pressed", Toast.LENGTH_SHORT).show();
                }
        });
        
        
        //deleteButton = (Button) findViewById(R.id.delete);
        
        //TODO: the 40 will be replaced by the number of previously stored thingies
        for(int i=0; i < 40; i++)
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
                ((ViewGroup) findViewById(R.id.linearLayout)).addView(button);
        }
        setContentView(findViewById(R.id.relativeLayout));
        
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
      public void geofenceInit()
      {
       // Create a new broadcast receiver to receive updates from the listeners and service
      mBroadcastReceiver = new GeofenceSampleReceiver();

      // Create an intent filter for the broadcast receiver
      mIntentFilter = new IntentFilter();

      // Action for broadcast Intents that report successful addition of geofences
      mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCES_ADDED);

      // Action for broadcast Intents that report successful removal of geofences
      mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCES_REMOVED);

      // Action for broadcast Intents containing various types of geofencing errors
      mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCE_ERROR);

      // All Location Services sample apps use this category
      mIntentFilter.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES);

      // Instantiate a new geofence storage area
      mPrefs = new SimpleGeofenceStore(this);

      // Instantiate the current List of geofences
      mCurrentGeofences = new ArrayList<Geofence>();

      // Instantiate a Geofence requester
      mGeofenceRequester = new GeofenceRequester(this);

      // Instantiate a Geofence remover
      mGeofenceRemover = new GeofenceRemover(this);
      
      CurrentFences = new HashMap<String,SimpleGeofence>();
      //TODO: need to instantiate from DB first start running 
      
      if (CreateGeoFence("Home", "145 Route 45, Salem, NJ 08079", 500,Geofence.GEOFENCE_TRANSITION_ENTER))
      	System.out.println("Josh is super awesome!! Boom baby!");
      
      }
      
      boolean CreateGeoFence(String Name, String strAddress, float radius, int transition)
      {
    	  mRequestType = GeofenceUtils.REQUEST_TYPE.ADD;
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
      
      private boolean servicesConnected() {
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
      
        protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
              // Choose what to do based on the request code
              switch (requestCode) {

                  // If the request code matches the code sent in onConnectionFailed
                  case GeofenceUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST :

                      switch (resultCode) {
                          // If Google Play services resolved the problem
                          case Activity.RESULT_OK:

                              // If the request was to add geofences
                              if (GeofenceUtils.REQUEST_TYPE.ADD == mRequestType) {

                                  // Toggle the request flag and send a new request
                                  mGeofenceRequester.setInProgressFlag(false);

                                  // Restart the process of adding the current geofences
                                  mGeofenceRequester.addGeofences(mCurrentGeofences);

                              // If the request was to remove geofences
                              } else if (GeofenceUtils.REQUEST_TYPE.REMOVE == mRequestType ){

                                  // Toggle the removal flag and send a new removal request
                                  mGeofenceRemover.setInProgressFlag(false);

                                  // If the removal was by Intent
                                  if (GeofenceUtils.REMOVE_TYPE.INTENT == mRemoveType) {

                                      // Restart the removal of all geofences for the PendingIntent
                                      mGeofenceRemover.removeGeofencesByIntent(
                                          mGeofenceRequester.getRequestPendingIntent());

                                  // If the removal was by a List of geofence IDs
                                  } else {

                                      // Restart the removal of the geofence list
                                      mGeofenceRemover.removeGeofencesById(mGeofenceIdsToRemove);
                                  }
                              }
                          break;
                      }



                     break;
              }
          }

          /*
           * Whenever the Activity resumes, reconnect the client to Location
           * Services and reload the last geofences that were set
           */
          @Override
          protected void onResume() {
              super.onResume();
              // Register the broadcast receiver to receive status updates
              LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, mIntentFilter);
              /*
               * Get existing geofences from the latitude, longitude, and
               * radius values stored in SharedPreferences. If no values
               * exist, null is returned.
               */
              }

          public class GeofenceSampleReceiver extends BroadcastReceiver {
              /*
               * Define the required method for broadcast receivers
               * This method is invoked when a broadcast Intent triggers the receiver
               */
              @Override
              public void onReceive(Context context, Intent intent) {

                  // Check the action code and determine what to do
                  String action = intent.getAction();

                  // Intent contains information about errors in adding or removing geofences
                  if (TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCE_ERROR)) {

                      handleGeofenceError(context, intent);

                  // Intent contains information about successful addition or removal of geofences
                  } else if (
                          TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCES_ADDED)
                          ||
                          TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCES_REMOVED)) {

                      handleGeofenceStatus(context, intent);

                  // Intent contains information about a geofence transition
                  } else if (TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCE_TRANSITION)) {

                      handleGeofenceTransition(context, intent);

                  // The Intent contained an invalid action
                  }
              }

              /**
               * If you want to display a UI message about adding or removing geofences, put it here.
               *
               * @param context A Context for this component
               * @param intent The received broadcast Intent
               */
              private void handleGeofenceStatus(Context context, Intent intent) {

              }

              /**
               * Report geofence transitions to the UI
               *
               * @param context A Context for this component
               * @param intent The Intent containing the transition
               */
              private void handleGeofenceTransition(Context context, Intent intent) {
                  /*
                   * If you want to change the UI when a transition occurs, put the code
                   * here. The current design of the app uses a notification to inform the
                   * user that a transition has occurred.
                   */
              }

              /**
               * Report addition or removal errors to the UI, using a Toast
               *
               * @param intent A broadcast Intent sent by ReceiveTransitionsIntentService
               */
              private void handleGeofenceError(Context context, Intent intent) {
                  String msg = intent.getStringExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS);
                  Log.e(GeofenceUtils.APPTAG, msg);
                  Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
              }
          }

          /**
           * Define a DialogFragment to display the error dialog generated in
           * showErrorDialog.
           */
          public static class ErrorDialogFragment extends DialogFragment {

              // Global field to contain the error dialog
              private Dialog mDialog;

              /**
               * Default constructor. Sets the dialog field to null
               */
              public ErrorDialogFragment() {
                  super();
                  mDialog = null;
              }

              /**
               * Set the dialog to display
               *
               * @param dialog An error dialog
               */
              public void setDialog(Dialog dialog) {
                  mDialog = dialog;
              }

              /*
               * This method must return a Dialog to the DialogFragment.
               */
              @Override
              public Dialog onCreateDialog(Bundle savedInstanceState) {
                  return mDialog;
              }
          }
}