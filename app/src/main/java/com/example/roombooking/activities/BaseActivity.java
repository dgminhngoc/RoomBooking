package com.example.roombooking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.roombooking.controllers.SwitchFragmentController;
import com.example.roombooking.fragments.BaseFragment;

public class BaseActivity extends AppCompatActivity
{
	/**
	 * switch to a fragment,and add it to back stack
	 *
	 * @param pBaseChildFragment: the instance of fragment that want to show
	 */
	protected <T extends BaseFragment> void switchFragment (int containerView,
																			  FragmentManager pFragmentManager,
																			  T pBaseChildFragment,
																			  boolean isAddToBackstack)
	{
		SwitchFragmentController.switchFragment(containerView, pFragmentManager, pBaseChildFragment, isAddToBackstack);
	}
}
