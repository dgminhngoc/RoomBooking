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
import java.util.Calendar;
import java.util.Date;

public class BookingFragment extends BaseFragment
{
	private EditText edtRoomName;
	private EditText edtDate;
	private EditText edtStartTime;
	private EditText edtDuration;

	private static final int BOOKING_LIMIT = 5;

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
			String [] time = getTime();

			edtDate.setText(currentDateTime[0]);
			edtStartTime.setText(time[0]+ ":" + time[1]);
		}
	}

	private String [] getTime(){
		String bookingTime = new SimpleDateFormat("HHmm").format(Calendar.getInstance().getTime());
		String hour = bookingTime.substring(0, 2);
		String minute = bookingTime.substring(2, 4);

		int intHour = Integer.parseInt(hour);
		int intMinute = Integer.parseInt(minute);

		if (intMinute >= 30){
			if (intMinute % 30 > BOOKING_LIMIT){
				intHour += 1;
				intMinute = 0;
			}
			else {
				intMinute = 30;
			}
		}
		else if (intMinute < 30){
			if (intMinute % 30 > BOOKING_LIMIT){
				intMinute = 30;
			}
			else {
				intMinute = 0;
			}
		}

		String strHour = Integer.toString(intHour);
		String strMinute;

		if (intMinute < 10){
			strMinute = "0" + Integer.toString(intMinute);
		}else{
			strMinute = Integer.toString(intMinute);
		}

		return new String[]{strHour, strMinute};
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
							sendBookingRequestToServer(userToken, dateInMillis, Integer.parseInt(duration), edtRoomName.getText().toString());
						}

					} catch (ParseException e)
					{
						e.printStackTrace();
					}
				}
			}
		};
	}

	private void sendBookingRequestToServer(String userToken, long startTimeInMillis, int duration, String roomName)
	{
		RoomBookingTask roomBookingTask = new RoomBookingTask(this, userToken, startTimeInMillis, duration, roomName);
		roomBookingTask.execute();
	}

	static class RoomBookingTask extends AsyncTask<Void, Void, Integer>
	{
		private String userToken;
		private BookingFragment bookingFragment;
		private long startTimeInMillis;
		private int duration;
		private String roomName;

		RoomBookingTask(BookingFragment bookingFragment, String userToken, long startTimeInMillis, int duration, String roomName)
		{
			this.userToken = userToken;
			this.bookingFragment = bookingFragment;
			this.startTimeInMillis = startTimeInMillis;
			this.duration = duration;
			this.roomName = roomName;
		}

		@Override
		protected Integer doInBackground(Void... voids)
		{
			return ServerDummy.getInstance().checkRoomAvailability(userToken, startTimeInMillis, duration, roomName);
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
						Toast.makeText(bookingFragment.getActivity(), bookingFragment.getActivity().getResources().getString(R.string.txt_room_reserve_success), Toast.LENGTH_SHORT).show();
						bookingFragment.onBackPressed();
						break;
					case ConstRequestResult.RE_ERR_ROOM_NOT_AVAILABLE:
						Toast.makeText(bookingFragment.getActivity(), bookingFragment.getActivity().getResources().getString(R.string.txt_romm_reserve_not_available), Toast.LENGTH_SHORT).show();
						break;
					case ConstRequestResult.RE_ERR_DURATION_TOO_LONG:
						Toast.makeText(bookingFragment.getActivity(), bookingFragment.getActivity().getResources().getString(R.string.txt_romm_reserve_duration_too_long), Toast.LENGTH_SHORT).show();
						break;
					default:
						break;
				}
			}
		}
	}
}
