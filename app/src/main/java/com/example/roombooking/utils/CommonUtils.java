package com.example.roombooking.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.roombooking.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils
{
	/**
	 * do validate email address
	 *
	 * @param emailAddress : the email address need to validate
	 * @return : true if that email is valid, false if otherwise
	 */
	public static boolean doValidateEmailAddress (String emailAddress)
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
	
	/**
	 * add animation when switch between fragments
	 *
	 * @param pFragmentTransaction
	 */
	public  static synchronized  void doAddAnimation(FragmentTransaction pFragmentTransaction) {
		if (pFragmentTransaction != null) {
			pFragmentTransaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit,
					R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
		}
	}

	/**
	 * pop fragment from backstack by tag
	 * @param tag :
	 */
	public  static synchronized  void popToFragmentByTag(FragmentManager pFragmentManager, String tag) {
		if (pFragmentManager != null && tag != null) {
			pFragmentManager.popBackStackImmediate(tag, 0);
		}
	}

	/**
	 * clear all fragment has been existed on back stack
	 * @param pFragmentManager
	 */
	public  static synchronized  void clearFragment(FragmentManager pFragmentManager) {
		if (pFragmentManager != null) {
			while (pFragmentManager.getBackStackEntryCount() > 0) {
				pFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
		}
	}

	/**
	 * get current fragment at a container view
	 * @param pFragmentManager : the fragment manager manage fragments
	 * @param containerViewID : the container contain fragments
	 * @return : the current fragment , null if otherwise
	 */
	public  static synchronized Fragment getCurrentFragment(FragmentManager pFragmentManager, int containerViewID){
		Fragment currentFragment = null;
		if (pFragmentManager != null){
			currentFragment = pFragmentManager.findFragmentById(containerViewID);
		}
		return currentFragment;
	}
}
