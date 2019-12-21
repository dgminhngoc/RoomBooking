package com.example.roombooking.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.roombooking.R;
import com.example.roombooking.fragments.LoginScreenFragment;

public class LoginActivity extends BaseActivity
{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		System.out.println("Hello in LOGIN ACTIVITY");
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.fragment_login_screen);
		setContentView(R.layout.activity_login);

		switchFragment(new LoginScreenFragment());
		/*
		Fragment mFragment = null;
		mFragment = new LoginScreenFragment();
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.containerView, mFragment).commit();

		 */
	}
}
