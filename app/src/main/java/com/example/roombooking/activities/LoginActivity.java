package com.example.roombooking.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.roombooking.R;
import com.example.roombooking.fragments.SplashScreenFragment;

public class LoginActivity extends BaseActivity
{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		switchFragment(new SplashScreenFragment());
	}
}
