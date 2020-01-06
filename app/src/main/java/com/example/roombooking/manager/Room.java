package com.example.roombooking.manager;

import java.util.HashMap;
import java.util.Map;

public class Room {
    String name;
    Map<Integer, Integer> dateContainer = new HashMap<Integer, Integer>();  // <Time, Duration>

    public Room(String name){
        this.name = name;
    }
}