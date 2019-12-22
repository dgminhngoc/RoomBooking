package com.example.roombooking.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils
{
	private static final String ROOM_PREFIX = "FH";

	/**
	 * do validate email address
	 *
	 * @param emailAddress : the email address need to validate
	 * @return : true if that email is valid, false if otherwise
	 */
	public static boolean isEMailValid (String emailAddress)
	{
		if (emailAddress != null && !emailAddress.isEmpty())
		{
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(emailAddress);

			return matcher.matches();
		}
		return false;
	}

	/**
	 * do validate email address
	 *
	 * @param qrString : the String generated from QRCode need to validate
	 */
	public static boolean isQRCodeValid(String qrString)
	{
		return qrString != null && qrString.length() > 2 && (qrString.substring(0, 1).equals(ROOM_PREFIX));
	}

	/**
	 * do check internet connection's availability
	 *
	 * @param context
	 * @return : true if connected, false if otherwise
	 */
	public static boolean checkIfNetworkAvailable (Context context)
	{
		ConnectivityManager connectivityManager
				= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

		if(activeNetworkInfo != null)
			return activeNetworkInfo.isConnected();

		return false;
	}

	/**
	 * do hide soft keyboard
	 *
	 * @param activity: current Activity in which soft keyboard should be hidden
	 */
	public static void hideSoftKeyboard (Activity activity)
	{
		if (activity != null)
		{
			InputMethodManager inputMethodManager = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			View viewFocus = activity.getCurrentFocus();
			if (viewFocus != null)
			{
				inputMethodManager.hideSoftInputFromWindow(viewFocus.getWindowToken(), 0);
			}
			activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		}
	}

}
