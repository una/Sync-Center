package com.example.hackyaleandroid;

import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;

public class Action {
	Context mcontext ;
	WifiManager wifiManager = (WifiManager) this.mcontext.getSystemService(Context.WIFI_SERVICE);
	AudioManager audioManager = (AudioManager) mcontext.getSystemService(Context.AUDIO_SERVICE);

	public Action() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	//turns on wifi if true, turns off if false
	public void wifiToggle(boolean turnOn)
	{
		wifiManager.setWifiEnabled(turnOn);
	}

	public void soundToggle(int state)
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
}
