package com.example.hackyaleandroid;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.hackyaleandroid.GeofenceUtils.REQUEST_TYPE;

import android.R;
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

   // Add geofences handler
   public static BluetoothAdapter mBluetoothAdapter;
   public static WifiManager mwifiManager;
   public static AudioManager maudioManager;
   public static SmsManager msms;
   public static Context mcontext;
   /*
    * An instance of an inner class that receives broadcasts from listeners and from the
    * IntentService that receives geofence transition events
    */
   private GeofenceSampleReceiver mBroadcastReceiver;

   // An intent filter for the broadcast receiver
   private IntentFilter mIntentFilter;

   private HashMap<String, Sync> currentSyncs;
   // Store the list of geofences to remove
   private List<String> mGeofenceIdsToRemove;
        
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync__center);
        mcontext = getBaseContext() ;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mwifiManager = (WifiManager) mcontext.getSystemService(Context.WIFI_SERVICE);
	    maudioManager = (AudioManager) mcontext.getSystemService(Context.AUDIO_SERVICE);
	    msms = SmsManager.getDefault();
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
}