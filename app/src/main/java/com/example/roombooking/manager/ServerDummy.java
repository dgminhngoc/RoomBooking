package com.example.roombooking.manager;

public class ServerDummy {
    private static ServerDummy instance = null;

    private static final String[] USER_ARRAY = {"Peter", "Karl", "Kala"};
    //private final


    private ServerDummy(){
    }

    public static ServerDummy getInstance(){
        if(ServerDummy.instance == null){
            ServerDummy.instance = new ServerDummy();
        }
        return ServerDummy.instance;
    }

    public boolean checkValidUser(String user){
        for(String name : ServerDummy.USER_ARRAY){
            if(user.equals(name)){
                return true;
            }
        }
        return false;
    }
}
