package com.example.roombooking.manager;

import com.example.roombooking.utils.CommonUtils;

import java.util.HashMap;

public class ServerDummy {
    private static ServerDummy instance = null;
    private HashMap<String, String> accounts;

    private ServerDummy(){
        accounts = new HashMap<>();
        String USER_PASSWORD = "password";
        accounts.put("peter@fh-bielefeld.de", USER_PASSWORD);
        accounts.put("karl@fh-bielefeld.de", USER_PASSWORD);
        accounts.put("kala@fh-bielefeld.de", USER_PASSWORD);
    }

    public static ServerDummy getInstance(){
        System.out.println("Hello in SERVER DUMMY");
        if(ServerDummy.instance == null){
            ServerDummy.instance = new ServerDummy();
        }
        return ServerDummy.instance;
    }

    public boolean checkValidUser(String user, String password)
    {
        if(CommonUtils.isEMailValid(user))
        {
          return accounts.containsKey(user) && accounts.get(user).equals(password);
        }
        return false;
    }


}
