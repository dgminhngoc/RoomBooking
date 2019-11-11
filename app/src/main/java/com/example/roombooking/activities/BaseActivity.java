package com.example.roombooking.activities;

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
		SwitchFragmentController.switchFragment(R.id.containerView, getSupportFragmentManager(), pBaseChildFragment, true);
	}

	/**
	 * switch to a fragment, and add it to back stack if defined
	 *
	 * @param pBaseChildFragment: the instance of fragment that want to show
	 * @param isAddToBackStack: add pBaseChildFragment to back stack if needed
	 */
	protected <T extends BaseFragment> void switchFragment (T pBaseChildFragment, boolean isAddToBackStack)
	{
		SwitchFragmentController.switchFragment(R.id.containerView, getSupportFragmentManager(), pBaseChildFragment, isAddToBackStack);
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
