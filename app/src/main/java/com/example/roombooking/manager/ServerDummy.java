package com.example.roombooking.manager;

public class ServerDummy {
    private static ServerDummy instance = null;

    private static final String[] USER_ARRAY = {"Peter", "Karl", "Kala"};
    private static final String[] USER_PASSWORD = {"password"};
    //private final

    private ServerDummy(){
        // ...
    }

    public static ServerDummy getInstance(){
        System.out.println("Hello in SERVER DUMMY");
        if(ServerDummy.instance == null){
            ServerDummy.instance = new ServerDummy();
        }
        return ServerDummy.instance;
    }

    public boolean checkValidUser(String user, String password){
        for(String name : ServerDummy.USER_ARRAY){
            if(user.equals(name)){
                for(String pwd : ServerDummy.USER_PASSWORD) {
                    if (pwd.equals(password)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
