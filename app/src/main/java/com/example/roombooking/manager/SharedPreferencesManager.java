package com.example.roombooking.manager;

import android.content.Context;

public class SharedPreferencesManager
{
	private static SharedPreferencesManager prefsManager = null;

	private SharedPreferencesManager ()
	{
		//do nothing
	}

	public static SharedPreferencesManager getInstance (Context context)
	{
		if(SharedPreferencesManager.prefsManager == null){
			SharedPreferencesManager.prefsManager = new SharedPreferencesManager();
		}
		return SharedPreferencesManager.prefsManager;
	}
}
