package com.example.roombooking.controllers;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.roombooking.R;
import com.example.roombooking.fragments.BaseFragment;

/**
 * Switch fragments controller.It control replaced fragment by other fragment or pop fragment,clear fragment...
 */
public class SwitchFragmentController
{
	/**
	 * switch to a fragment,and add it to back stack
	 * @param containerView: layout id in which fragment will exist
	 * @param pFragmentManager: FragmentManager instance from Root Activity
	 * @param pBaseChildFragment: the instance of fragment that needs to be shown
	 * @param dataBundle: data which will be passed pBaseChildFragment after transaction
	 * @param isAddToBackStack: set TRUE if pBaseChildFragment needs to be stored in BackStack
	 */
	public static synchronized <T extends BaseFragment> void switchFragment(int containerView,
																									FragmentManager pFragmentManager,
																									T pBaseChildFragment,
																									Bundle dataBundle,
																									boolean isAddToBackStack)
	{
		if (pFragmentManager != null) {
			FragmentTransaction pFragmentTransaction = pFragmentManager.beginTransaction();
			doAddAnimation(pFragmentTransaction);

			pBaseChildFragment.setDataBundle(dataBundle);
			pFragmentTransaction.replace(containerView, pBaseChildFragment);
			if (isAddToBackStack) {
				pFragmentTransaction.addToBackStack(pBaseChildFragment.getClass().getSimpleName());
			}
			pFragmentTransaction.commitAllowingStateLoss();
		}
	}

	/**
	 * switch to the previous fragment and remove current fragment
	 * @param pFragmentManager: FragmentManager instance from Root Activity
	 * @param dataBundle: data which will be passed pBaseChildFragment after transaction
	 */
	public static synchronized void switchToPreviousFragment(FragmentManager pFragmentManager, Bundle dataBundle)
	{
		if (pFragmentManager != null) {
			if (pFragmentManager.getBackStackEntryCount() >= 0) {
				pFragmentManager.popBackStackImmediate();
				getCurrentFragment(pFragmentManager).setDataBundle(dataBundle);
			}
		}
	}

	/**
	 * add animation when switch between fragments
	 *
	 * @param pFragmentTransaction
	 */
	public static synchronized void doAddAnimation(FragmentTransaction pFragmentTransaction) {
		if (pFragmentTransaction != null) {
			pFragmentTransaction.setCustomAnimations(	R.anim.fragment_slide_left_enter,
																	R.anim.fragment_slide_right_exit,
																	R.anim.fragment_slide_left_enter,
																	R.anim.fragment_slide_right_exit);
		}
	}

	/**
	 * pop fragment from backstack by tag
	 * @param pFragmentManager: FragmentManager instance from Root Activity
	 * @param tag: name of Fragment that you want to get
	 */
	public static synchronized void popToFragmentByTag(FragmentManager pFragmentManager, String tag) {
		if (pFragmentManager != null && tag != null) {
			pFragmentManager.popBackStackImmediate(tag, 0);
		}
	}

	/**
	 * clear all fragment has been existed on back stack
	 * @param pFragmentManager
	 */
	public static synchronized  void clearFragment(FragmentManager pFragmentManager)
	{
		if (pFragmentManager != null) {
			while (pFragmentManager.getBackStackEntryCount() > 0) {
				pFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
		}
	}

	/**
	 * get current fragment at a container view
	 * @param pFragmentManager : the fragment manager manage fragments
	 * @return : the current fragment , null if otherwise
	 */
	public static synchronized BaseFragment getCurrentFragment(FragmentManager pFragmentManager)
	{
		if (pFragmentManager != null)
			return (BaseFragment) pFragmentManager.findFragmentById(R.id.containerView);

		return null;
	}
}
