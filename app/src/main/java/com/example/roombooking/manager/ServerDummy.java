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


	public boolean bookRoom(String roomName, String timeframe){


		return false;
	}

	public boolean checkValidUser(String username, String password)
	{
		if (CommonUtils.isEMailValid(username))
		{
			return accounts.containsKey(username) && accounts.get(username).equals(password);
		}
		return false;
	}

	public int checkRoomAvailability(@NonNull String userToken, int timeInMillis, int duration, String roomName)
	{
		for (Room r : ServerDummy.rooms){
			if(r.name.equals(roomName)){

				if(RoomContainer.checkTimeSlotFree(r, timeInMillis, duration)){
					RoomContainer.bookRoom(r, timeInMillis, duration);
					return ConstRequestResult.RE_AVAILABLE;
				}else{
					return ConstRequestResult.RE_ERR_ROOM_NOT_AVAILABLE;
				}
			}
		}
		return ConstRequestResult.RE_ERR_ROOM_NOT_AVAILABLE;
	}
}
