package com.example.roombooking.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.roombooking.R;
import com.example.roombooking.fragments.LoginScreenFragment;

public class MainActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		View.OnClickListener listener = new View.OnClickListener() {
			public void onClick(View view) {
				Fragment fragment = null;
				if(view == findViewById(R.id.btn_login)){
					fragment = new LoginScreenFragment();
				}
				FragmentManager manager = getSupportFragmentManager();
				FragmentTransaction transaction = manager.beginTransaction();
				transaction.replace(R.id.containerView, fragment);
				transaction.commit();
			}
		};
		Button btn1 = (Button)findViewById(R.id.btn_login);
		btn1.setOnClickListener(listener);

	}


}
