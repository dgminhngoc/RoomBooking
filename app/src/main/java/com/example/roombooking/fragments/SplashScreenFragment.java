package com.example.roombooking.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roombooking.R;
import com.example.roombooking.activities.MainActivity;
import com.example.roombooking.manager.SharedPreferencesManager;

public class SplashScreenFragment extends BaseFragment
{
	@Override
	protected int getLayoutContentID()
	{
		return R.layout.fragment_splash_screen;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		Runnable runnable = new Runnable()
		{
			@Override
			public void run()
			{
				String token = SharedPreferencesManager.getInstance(getActivity()).getUserToken();
				if(SharedPreferencesManager.getInstance(getActivity()).getUserToken() != null)
				{
					startActivity(new Intent(getActivity(), MainActivity.class));
					getActivity().finish();				}
				else
				{
					switchFragment(new LoginScreenFragment(), null, false);
				}
			}
		};

		Handler checkUserTokenHandler = new Handler();
		checkUserTokenHandler.postDelayed(runnable, 2000);
	}
}
