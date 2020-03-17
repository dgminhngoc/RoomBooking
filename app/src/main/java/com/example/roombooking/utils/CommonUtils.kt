package com.example.roombooking.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object CommonUtils {
    private const val ROOM_PREFIX = "FH"
    private const val EMAIL_POSTFIX = "fh-bielefeld.de"

    /**
     * do validate email address
     *
     * @param emailAddress : the email address need to validate
     * @return : true if that email is valid, false if otherwise
     */
	 @JvmStatic
	 fun isEMailValid(emailAddress: String): Boolean {
        if (emailAddress.isNotEmpty()) {
            val regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
            val pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(emailAddress)
            if (matcher.matches()) {
                val emailParts = emailAddress.split("@").toTypedArray()
                return emailParts[1] == EMAIL_POSTFIX
            }
            return false
        }
        return false
    }

//    fun isStartTimeValid(timeMillis: Long): Boolean {
//        return timeMillis > System.currentTimeMillis()
//    }

    /**
     * do validate email address
     *
     * @param qrString : the String generated from QRCode need to validate
     */
	 @JvmStatic
	 fun isQRCodeValid(qrString: String?): Boolean {
        return qrString != null && qrString.length > 2 && qrString.substring(0, 2) == ROOM_PREFIX
    }

    /**
     * do check internet connection's availability
     *
     * @param context
     * @return : true if connected, false if otherwise
     */
	 @JvmStatic
	 fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnected ?: false
    }

    /**
     * do hide soft keyboard
     *
     * @param activity: current Activity in which soft keyboard should be hidden
     */
	 @JvmStatic
	 fun hideSoftKeyboard(activity: Activity?) {
        if (activity != null) {
            val inputMethodManager = activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val viewFocus = activity.currentFocus
            if (viewFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(viewFocus.windowToken, 0)
            }
            activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }

    @JvmStatic
	 val currentDateTime: String
        get() = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().time)
}