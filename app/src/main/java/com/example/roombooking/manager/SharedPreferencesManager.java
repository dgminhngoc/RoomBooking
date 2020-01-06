package com.example.roombooking.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager
{
	private static final String PREFS_NAME 				= "room_booking_prefs";
	private static final String PREFS_KEY_USER_TOKEN 	= "prefs_key_user_token";

	private static SharedPreferencesManager prefsManager = null;
	private SharedPreferences sharedPreferences;


	private SharedPreferencesManager (Context context)
	{
		this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	}

	public static SharedPreferencesManager getInstance (Context context)
	{
		if(SharedPreferencesManager.prefsManager == null){
			SharedPreferencesManager.prefsManager = new SharedPreferencesManager(context);
		}
		return SharedPreferencesManager.prefsManager;
	}

	public void setUserToken(String token)
	{
		sharedPreferences.edit().putString(PREFS_KEY_USER_TOKEN, token).apply();
	}

	public String getUserToken()
	{
		return sharedPreferences.getString(PREFS_KEY_USER_TOKEN, null);
	}

	public void clearData()
	{
		sharedPreferences.edit().clear().apply();
	}
}


