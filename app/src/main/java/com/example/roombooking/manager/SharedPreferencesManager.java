package com.example.roombooking.manager;

import android.content.Context;

public class SharedPreferencesManager
{
	private static SharedPreferencesManager prefsManager;

	private SharedPreferencesManager ()
	{
		//do nothing
	}

	public static SharedPreferencesManager getInstance (Context context)
	{
		return prefsManager;
	}
}
