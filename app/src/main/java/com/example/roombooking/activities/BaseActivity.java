package com.example.roombooking.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roombooking.R;
import com.example.roombooking.controllers.SwitchFragmentController;
import com.example.roombooking.fragments.BaseFragment;

public abstract class BaseActivity extends AppCompatActivity
{
	/**
	 * switch to a fragment, and add it to back stack
	 *
	 * @param pBaseChildFragment: the instance of fragment that want to show
	 */
	protected <T extends BaseFragment> void switchFragment (T pBaseChildFragment)
	{
		SwitchFragmentController.switchFragment(R.id.containerView, getSupportFragmentManager(), pBaseChildFragment, null, true);
	}

	/**
	 * switch to a fragment, and add it to back stack
	 *
	 * @param pBaseChildFragment: the instance of fragment that want to show
	 */
	protected <T extends BaseFragment> void switchFragment (T pBaseChildFragment, Bundle dataBundle)
	{
		SwitchFragmentController.switchFragment(R.id.containerView, getSupportFragmentManager(), pBaseChildFragment, dataBundle, true);
	}

	/**
	 * switch to a fragment, and add it to back stack if defined
	 *
	 * @param pBaseChildFragment: the instance of fragment that want to show
	 * @param isAddToBackStack: add pBaseChildFragment to back stack if needed
	 */
	protected <T extends BaseFragment> void switchFragment (T pBaseChildFragment, Bundle dataBundle, boolean isAddToBackStack)
	{
		SwitchFragmentController.switchFragment(R.id.containerView, getSupportFragmentManager(), pBaseChildFragment, dataBundle, isAddToBackStack);
	}

	@Override
	public void onBackPressed()
	{
		BaseFragment mFragment = SwitchFragmentController.getCurrentFragment(getSupportFragmentManager());
		if(mFragment != null)
			mFragment.onBackPressed();

		if(getSupportFragmentManager().getBackStackEntryCount() == 0)
			finish();
	}
}
