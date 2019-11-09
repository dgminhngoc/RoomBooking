package com.example.roombooking.activities;

import android.os.Bundle;

import com.example.roombooking.R;
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
}
