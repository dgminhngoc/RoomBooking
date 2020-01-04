package com.example.roombooking.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roombooking.R;
import com.example.roombooking.manager.SharedPreferencesManager;
import com.example.roombooking.utils.ConstKeyBundle;

public class BookingFragment extends BaseFragment
{
	private EditText edtRoomName;
	private EditText edtDate;
	private EditText edtStartTime;
	private EditText edtDuration;

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
			edtRoomName 	= view.findViewById(R.id.edt_room_name);
			edtDate 			= view.findViewById(R.id.edt_date);
			edtStartTime 	= view.findViewById(R.id.edt_start_time);
			edtDuration 	= view.findViewById(R.id.edt_duration);

			String roomName = dataBundle.getString(ConstKeyBundle.KEY_ROOM_NAME);
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
				String userToken = SharedPreferencesManager.getInstance(getActivity()).getUserToken();
				String date = edtDate.getText().toString();
				String startTime = edtStartTime.getText().toString();
				String duration = edtDuration.getText().toString();

				if(userToken != null
					&& date != null
					&& startTime != null
					&& duration != null)
				{

				}
			}
		};
	}

	private void sendBookingRequestToServer()
	{

	}

	static class RoomBookingTask extends AsyncTask<Void, Void, Integer>
	{
		private String userToken;
		private BookingFragment bookingFragment;
		private long startTimeInMillis;
		private int duration;

		RoomBookingTask(String userToken, BookingFragment bookingFragment, long startTimeInMillis, int duration)
		{
			this.userToken = userToken;
			this.bookingFragment = bookingFragment;
			this.startTimeInMillis = startTimeInMillis;
			this.duration = duration;
		}

		@Override
		protected Integer doInBackground(Void... voids)
		{
			return null;
		}

		@Override
		protected void onPostExecute(Integer integer)
		{
			super.onPostExecute(integer);
		}
	}
}
