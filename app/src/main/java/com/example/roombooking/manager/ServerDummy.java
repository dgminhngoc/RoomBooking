package com.example.roombooking.manager;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.roombooking.utils.CommonUtils;
import com.example.roombooking.utils.ConstRequestResult;

import java.util.HashMap;

public class ServerDummy
{
	private static ServerDummy instance = null;
	private HashMap<String, String> accounts;

	public static final String USER_TOKEN = "user_token";

	// Predefined room objects
	public static final Room rooms [] = new Room[]{
			new Room("FHA112"),
			new Room("FHA113"),
			new Room("FHA114"),
			new Room("FHB110"),
	};

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

	public int checkRoomAvailability(@NonNull String userToken, long timeInMillis, int duration, String roomName)
	{

		for(int i = 0; i < rooms.length; i++){
			Log.i("Rooms", rooms[i].name);
			System.out.println("FHATime"+timeInMillis);
			System.out.println("FHAdur"+duration);
			System.out.println(rooms[i].name);
			if(rooms[i].name.equals(roomName)){



				RoomContainer.bookRoom(rooms[i], 1200, duration);
			}
		}

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
