package com.example.roombooking.activities;

import android.os.Bundle;

import com.example.roombooking.R;
import com.example.roombooking.controllers.SwitchFragmentController;
import com.example.roombooking.fragments.TestFragmentA;

public class MainActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		switchFragment(new TestFragmentA(), true);

	}

	@Override
	public void onBackPressed()
	{
		SwitchFragmentController.getCurrentFragment(getSupportFragmentManager()).onBackPress();
		if(SwitchFragmentController.getCurrentFragment(getSupportFragmentManager()) == null)
			super.onBackPressed();
	}
}
