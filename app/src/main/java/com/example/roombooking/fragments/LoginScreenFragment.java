package com.example.roombooking.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roombooking.R;
import com.example.roombooking.activities.LoginActivity;
import com.example.roombooking.activities.ScannerActivity;
import com.example.roombooking.manager.ServerDummy;


public class LoginScreenFragment extends BaseFragment
{
	@Override
	protected int getLayoutContentID()
	{
		return R.layout.fragment_login_screen;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		final EditText txtUserName = view.findViewById(R.id.txt_user);
		EditText txtPassword = view.findViewById(R.id.txt_password);
		Button loginBtn = view.findViewById(R.id.btn_login);

		loginBtn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view)
			{
				ServerDummy server = ServerDummy.getInstance();

				String userName = txtUserName.getText().toString();

				if (server.checkValidUser(userName))
				{
					Toast.makeText(getContext(), "User " + userName + " eingeloggt", Toast.LENGTH_LONG).show();

					Intent scanner = new Intent(getActivity(), ScannerActivity.class);
					startActivity(scanner);

					getActivity().finish();
				}
				else
				{
					Toast.makeText(getContext(), "Failed!", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
