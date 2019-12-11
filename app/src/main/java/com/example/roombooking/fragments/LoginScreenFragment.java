	package com.example.roombooking.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roombooking.R;
import com.example.roombooking.activities.ScannerActivity;
import com.example.roombooking.manager.ServerDummy;


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
		EditText text_pass = view.findViewById(R.id.txt_Pass);
		Button loginBtn = view.findViewById(R.id.btn_login);

		loginBtn.setOnClickListener( new View.OnClickListener()
		{
			public void onClick(View view)
			{
				ServerDummy server = ServerDummy.getInstance();

				String user = text.getText().toString();

				if(server.checkValidUser(user)) {
					Toast.makeText(getContext(), "User "+ user + " eingeloggt" , Toast.LENGTH_LONG).show();

					Intent scanner = new Intent(getActivity(), ScannerActivity.class);
					startActivity(scanner);


				}
				else {
					Toast.makeText(getContext(), "Failed!", Toast.LENGTH_LONG).show();
				}
			}
		});









		return view;

	}


}
