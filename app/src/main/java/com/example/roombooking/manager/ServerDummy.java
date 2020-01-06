package com.example.roombooking.manager;

import androidx.annotation.NonNull;

import com.example.roombooking.utils.CommonUtils;
import com.example.roombooking.utils.ConstRequestResult;

import java.util.HashMap;

public class ServerDummy
{
	private static ServerDummy instance = null;
	private HashMap<String, String> accounts;

	public static final String USER_TOKEN = "user_token";

	private ServerDummy()
	{
		accounts = new HashMap<>();
		String USER_PASSWORD = "password";
		accounts.put("peter@fh-bielefeld.de", USER_PASSWORD);
		accounts.put("karl@fh-bielefeld.de", USER_PASSWORD);
		accounts.put("kala@fh-bielefeld.de", USER_PASSWORD);
	}

	public static ServerDummy getInstance()
	{
		if (ServerDummy.instance == null)
		{
			ServerDummy.instance = new ServerDummy();
		}
		return ServerDummy.instance;
	}

	public boolean checkValidUser(String username, String password)
	{
		if (CommonUtils.isEMailValid(username))
		{
			return accounts.containsKey(username) && accounts.get(username).equals(password);
		}
		return false;
	}

	public int checkRoomAvailability(@NonNull String userToken, long timeInMillis, int duration)
	{
		if (timeInMillis > (System.currentTimeMillis() + 1000000))
		{
			if (duration > 180)
				return ConstRequestResult.RE_ERR_DURATION_TOO_LONG;
			else
				return ConstRequestResult.RE_ERR_ROOM_NOT_AVAILABLE;
		}
		else
			return ConstRequestResult.RE_ERR_ROOM_NOT_AVAILABLE;
	}
}
