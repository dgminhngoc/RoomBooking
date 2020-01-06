package com.example.roombooking.manager;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RoomContainer {

    public static final Map<Room, Map<Integer, Integer>> container = new HashMap<Room, Map<Integer, Integer>>();

    public RoomContainer(){
        // empty
    }

    public static  final void bookRoom(Room room, int time, int duration){

        if (checkTimeSlotFree(room, time, duration)){
            int endTime = time;
            endTime += (100 * (duration / 60)) + (duration % 60);

            for(int intervall = 30; time < endTime;){
                time += intervall;

                if (time % 100 == 60){
                    time += 40;
                }

                if (time == endTime){
                    room.dateContainer.put(time, 0);
                }else {
                    room.dateContainer.put(time, -1);
                }
            }
            System.out.println("Buchen erfolgreich");
        }
        else{
            System.out.println("Raum nicht frei!");
        }
    }

    public static final Boolean checkTimeSlotFree(Room room, int time, int duration){
        if (room.dateContainer.containsKey(time) && room.dateContainer.get(time) != 0){
            return false;
        }
        else{
            room.dateContainer.put(time, duration);
            return true;
        }
    }

    public static final Boolean checkRoomExists(Room room){
        if (container.containsKey(room)){ return true; } else { return false; }
    }
    /*
    public static void main(String [] args){
        bookRoom(A01, 1200, 60);
        bookRoom(A01, 1230, 30);
    }
     */
}
