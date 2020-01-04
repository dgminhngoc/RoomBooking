package com.example.roombooking.manager;

import com.example.roombooking.utils.CommonUtils;

import java.util.HashMap;

public class ServerDummy {
    private static ServerDummy instance = null;
    private HashMap<String, String> accounts;

    public static final String USER_TOKEN = "user_token";

    private ServerDummy(){
        accounts = new HashMap<>();
        String USER_PASSWORD = "password";
        accounts.put("peter@fh-bielefeld.de", USER_PASSWORD);
        accounts.put("karl@fh-bielefeld.de", USER_PASSWORD);
        accounts.put("kala@fh-bielefeld.de", USER_PASSWORD);
    }

    public static ServerDummy getInstance(){
        if(ServerDummy.instance == null){
            ServerDummy.instance = new ServerDummy();
        }
        return ServerDummy.instance;
    }

    public boolean checkValidUser(String username, String password)
    {
        if(CommonUtils.isEMailValid(username))
        {
          return accounts.containsKey(username) && accounts.get(username).equals(password);
        }
        return false;
    }

    public boolean checkRoomAvailability(String userToken, String date, String time, String duration)
    {
        if(date != null && time != null)
            return date.equals("30/02/2020") && time.equals("12:00") && Integer.parseInt(duration) <= 3;
        else
            return false;
    }
}
