package com.example.hackyaleandroid;

import android.provider.ContactsContract.CommonDataKinds.Phone;

public class TextMessage {
	public String mmessage;
	public String mnumber;
	
	public TextMessage(String number, String message)
	{
		mmessage = message;
		mnumber = number;
	}
}
