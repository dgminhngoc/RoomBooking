package com.example.roombooking.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roombooking.R;
import com.example.roombooking.utils.ConstKeyBundle;

public class BookingFragment extends BaseFragment
{
	@Override
	protected int getLayoutContentID()
	{
		return R.layout.fragment_room_booking;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		Bundle dataBundle = getDataBundle();
		if(dataBundle != null)
		{
			String roomName = dataBundle.getString(ConstKeyBundle.KEY_ROOM_NAME);
			EditText edtRoomName = view.findViewById(R.id.edt_room_name);
			edtRoomName.setText(roomName);

			Button btnCancel = view.findViewById(R.id.btn_cancel);
			btnCancel.setOnClickListener(btnCancelOnClickListener());

			Button btnBook = view.findViewById(R.id.btn_book);
			btnBook.setOnClickListener(btnBookOnClickListener());
		}
	}

	private View.OnClickListener btnCancelOnClickListener()
	{
		return new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				onBackPressed();
			}
		};
	}

	private View.OnClickListener btnBookOnClickListener()
	{
		return new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{

			}
		};
	}
}
