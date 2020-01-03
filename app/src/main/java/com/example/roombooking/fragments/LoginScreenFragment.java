package com.example.roombooking.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roombooking.R;
import com.example.roombooking.activities.MainActivity;
import com.example.roombooking.manager.ServerDummy;
import com.example.roombooking.manager.SharedPreferencesManager;
import com.example.roombooking.utils.CommonUtils;


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

		final EditText edtUsername = view.findViewById(R.id.edt_username);
		final EditText edtPassword = view.findViewById(R.id.edt_password);

		Button btnLogin = view.findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view)
			{
				String userName = edtUsername.getText().toString();
				String password = edtPassword.getText().toString();

				if(CommonUtils.isEMailValid(userName))
				{
					login(userName, password);
				}
				else
				{
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.txt_login_err_wrong_account), Toast.LENGTH_SHORT).show();
				}
			}
		});

		Button btnCancel = view.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				edtUsername.getText().clear();
				edtPassword.getText().clear();
			}
		});
	}

	private void login(String userName, String password)
	{
		RequestLoginTask requestLoginTask = new RequestLoginTask(this, userName, password);
		requestLoginTask.execute();
	}

	static class RequestLoginTask extends AsyncTask<Void, Void, String>
	{
		private String 	userName;
		private String 	password;
		private Fragment 	fragment;

		RequestLoginTask(Fragment fragment, String userName, String password)
		{
			this.fragment = fragment;
			this.userName = userName;
			this.password = password;
		}

		@Override
		protected String doInBackground(Void... voids)
		{
			ServerDummy server = ServerDummy.getInstance();

			return server.checkValidUser(userName, password) ? ServerDummy.LOGIN_TOKEN : null;
		}

		@Override
		protected void onPostExecute(String loginToken)
		{
			super.onPostExecute(loginToken);

			if(fragment != null && fragment.getActivity() != null)
			{
				if(loginToken != null)
				{
					SharedPreferencesManager.getInstance(fragment.getActivity()).setUserToken(loginToken);

					fragment.startActivity(new Intent(fragment.getActivity(), MainActivity.class));
					fragment.getActivity().finish();
				}
				else
				{
					Toast.makeText(fragment.getActivity(), fragment.getActivity().getResources().getString(R.string.txt_login_err_wrong_account), Toast.LENGTH_SHORT).show();
				}

				fragment = null;
			}
		}
	}
}
