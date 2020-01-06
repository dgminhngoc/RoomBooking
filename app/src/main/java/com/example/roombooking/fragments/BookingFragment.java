package com.example.roombooking.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roombooking.R;
import com.example.roombooking.manager.ServerDummy;
import com.example.roombooking.manager.SharedPreferencesManager;
import com.example.roombooking.utils.CommonUtils;
import com.example.roombooking.utils.ConstKeyBundle;
import com.example.roombooking.utils.ConstRequestResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

			String[] currentDateTime = CommonUtils.getCurrentDateTime().split(" ");
			edtDate.setText(currentDateTime[0]);
			edtStartTime.setText(currentDateTime[1]);
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
				if(!CommonUtils.isNetworkAvailable(getActivity()))
				{
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.txt_err_no_connection), Toast.LENGTH_SHORT).show();
					return;
				}

				String userToken = SharedPreferencesManager.getInstance(getActivity()).getUserToken();
				String date = edtDate.getText().toString();
				String startTime = edtStartTime.getText().toString();
				String duration = edtDuration.getText().toString();

				if(userToken != null
					&& date != null
					&& startTime != null
					&& duration != null)
				{
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String dateInString = date;
					try
					{
						Date mdate = formatter.parse(dateInString);
						String[] mStartTime = startTime.split(":");
						long dateInMillis = mdate.getTime() + (Integer.parseInt(mStartTime[0])*60 + Integer.parseInt(mStartTime[1])*60*1000);
						if(!CommonUtils.isStartTimeValid(dateInMillis))
						{
							Toast.makeText(getActivity(), "Invalid start time", Toast.LENGTH_SHORT).show();
							return;
						}
						else
						{
							sendBookingRequestToServer(userToken, dateInMillis, Integer.parseInt(duration));
						}

					} catch (ParseException e)
					{
						e.printStackTrace();
					}
				}
			}
		};
	}

	private void sendBookingRequestToServer(String userToken, long startTimeInMillis, int duration)
	{
		RoomBookingTask roomBookingTask = new RoomBookingTask(this, userToken, startTimeInMillis, duration);
		roomBookingTask.execute();
	}

	static class RoomBookingTask extends AsyncTask<Void, Void, Integer>
	{
		private String userToken;
		private BookingFragment bookingFragment;
		private long startTimeInMillis;
		private int duration;

		RoomBookingTask(BookingFragment bookingFragment, String userToken, long startTimeInMillis, int duration)
		{
			this.userToken = userToken;
			this.bookingFragment = bookingFragment;
			this.startTimeInMillis = startTimeInMillis;
			this.duration = duration;
		}

		@Override
		protected Integer doInBackground(Void... voids)
		{
			return ServerDummy.getInstance().checkRoomAvailability(userToken, startTimeInMillis, duration);
		}

		@Override
		protected void onPostExecute(Integer result)
		{
			super.onPostExecute(result);

			if(bookingFragment != null)
			{
				switch (result)
				{
					case ConstRequestResult.RE_AVAILABLE:
						Toast.makeText(bookingFragment.getActivity(), "OK", Toast.LENGTH_SHORT).show();
						bookingFragment.onBackPressed();
						break;
					case ConstRequestResult.RE_ERR_ROOM_NOT_AVAILABLE:
						Toast.makeText(bookingFragment.getActivity(), "Room is not available", Toast.LENGTH_SHORT).show();
						break;
					case ConstRequestResult.RE_ERR_DURATION_TOO_LONG:
						Toast.makeText(bookingFragment.getActivity(), "Duration is too long", Toast.LENGTH_SHORT).show();
						break;
					default:
						break;
				}
			}
		}
	}
}
