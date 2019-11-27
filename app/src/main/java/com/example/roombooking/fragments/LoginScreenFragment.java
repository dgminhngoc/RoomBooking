package com.example.roombooking.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roombooking.R;




public class LoginScreenFragment extends BaseFragment implements View.OnClickListener
{


	@Override
	protected int getLayoutContentID()
	{
		return 0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_login_screen, container, false);

		Button loginBtn = view.findViewById(R.id.btn_login);
		loginBtn.setOnClickListener(this);

		EditText text = view.findViewById(R.id.txt_User);
		String user = text.getText().toString();
		EditText text_pass = view.findViewById(R.id.txt_Pass);
		String pass = text_pass.getText().toString();



		return view;

	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_login:
				//ToDo: Validate

				Toast.makeText(getContext(), "Einloggen: ", Toast.LENGTH_SHORT).show();
				break;

		}

	}
}
