package com.example.roombooking.manager;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesManager
{
	private static final String FILENAME = "PREFERECES_FILENAME";


	private static SharedPreferencesManager prefsManager = null;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;


	private SharedPreferencesManager (Context context)
	{
		this.sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		this.editor = this.sharedPreferences.edit();
	}

	public static SharedPreferencesManager getInstance (Context context)
	{
		if(SharedPreferencesManager.prefsManager == null){
			SharedPreferencesManager.prefsManager = new SharedPreferencesManager(context);
		}
		return SharedPreferencesManager.prefsManager;
	}

	public void setValue(String key, String value){
		this.editor.putString(key, value);
		this.editor.apply();
	}

	public void setValue(String key, int value){
		this.editor.putInt(key, value);
		this.editor.apply();
	}

	public void setValue(String key, float value){
		this.editor.putFloat(key, value);
		this.editor.apply();
	}

	public void setValue(String key, boolean value){
		this.editor.putBoolean(key, value);
		this.editor.apply();
	}

	public String getString(String key){
		return this.sharedPreferences.getString(key, "");
	}

	public int getInt(String key){
		return this.sharedPreferences.getInt(key, 0);
	}

	public float getFloat(String key){
		return this.sharedPreferences.getFloat(key, 0.0f);
	}

	public boolean getBoolean(String key){
		return this.sharedPreferences.getBoolean(key, false);
	}
}


