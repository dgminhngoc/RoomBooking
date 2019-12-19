package com.example.roombooking.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.roombooking.R;
import com.example.roombooking.fragments.BarcodeScannerFragment;
import com.example.roombooking.fragments.LoginScreenFragment;

public class MainActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		System.out.println("Hello in MAIN ACTIVITY");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		switchFragment(new BarcodeScannerFragment());
    }
}
