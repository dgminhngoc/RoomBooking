package com.example.roombooking.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roombooking.R;




public class LoginScreenFragment extends BaseFragment
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

		final EditText text = view.findViewById(R.id.txt_User);
		final String user = text.getText().toString();
		EditText text_pass = view.findViewById(R.id.txt_Pass);
		String pass = text_pass.getText().toString();
		final String u = "laser";
		Button loginBtn = view.findViewById(R.id.btn_login);
		loginBtn.setOnClickListener( new View.OnClickListener()
		{
			public void onClick(View view)
			{
				if(text.getText().toString().equals("Tim")) {
					Toast.makeText(getContext(), "User "+ text.getText().toString() + " eingeloggt" , Toast.LENGTH_LONG).show();

				}
				else {
					Toast.makeText(getContext(), "Failed!", Toast.LENGTH_LONG).show();
				}
				Log.v("EditText", text.getText().toString());
				Log.d("benutzer", user);

			}
		});









		return view;

	}


}
