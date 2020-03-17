package com.example.roombooking.manager

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var userToken: String?
        get() = sharedPreferences.getString(PREFS_KEY_USER_TOKEN, null)
        set(token) {
            sharedPreferences.edit().putString(PREFS_KEY_USER_TOKEN, token).apply()
        }

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "room_booking_prefs"
        private const val PREFS_KEY_USER_TOKEN = "prefs_key_user_token"
        private var prefsManager: SharedPreferencesManager? = null
        @JvmStatic
		  fun getInstance(context: Context): SharedPreferencesManager? {
            if (prefsManager == null) {
                prefsManager = SharedPreferencesManager(context)
            }
            return prefsManager
        }
    }
}