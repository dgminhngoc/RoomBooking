package com.example.roombooking.manager;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RoomContainer {

    public static final Map<Room, Map<Integer, Integer>> container = new HashMap<Room, Map<Integer, Integer>>();

    public RoomContainer(){
        // empty
    }

    public static final List<String> showAlternatives(String roomName){
        Set s = container.keySet();

        int myObjIndex = -1;
        for (int i = 0; i < ServerDummy.rooms.length; i++){
            if (ServerDummy.rooms[i].name.equals(roomName)) {
                myObjIndex = i;
            }
        }

        Map a = container.get(ServerDummy.rooms[myObjIndex]);
        int myValue = -1;
        for(Object value : a.values()){
            String strTemp = value.toString();
            int intTemp = Integer.parseInt(strTemp);
            if (intTemp == 0 || intTemp == -1){
                // nothing
            }else{
                String myk = value.toString();
                myValue = Integer.parseInt(myk);
            }
        }

        int myKey = -1;
        for (Map.Entry<Integer, Integer> entry : ServerDummy.rooms[myObjIndex].dateContainer.entrySet()) {
            if (entry.getValue().equals(myValue)) {
                myKey = entry.getKey();
            }
        }

        List<String> alternativeRooms = new ArrayList<String>();

        for (int i = 0; i < ServerDummy.rooms.length; i++){
            if (ServerDummy.rooms[i].name.equals(roomName)) {
                // nothing
            }else{
                if(checkTimeSlotFree(ServerDummy.rooms[i], myKey, myValue)){
                    alternativeRooms.add(ServerDummy.rooms[i].name);
                }
            }
        }
        System.out.println("HELLO");
        System.out.println("HELLO ALTERNATIVE: " + alternativeRooms);
        return alternativeRooms;
    }

    public static final void bookRoom(Room room, int time, int duration){
        room.dateContainer.put(time, duration);
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
        container.put(room, room.dateContainer);
    }

    public static final Boolean checkTimeSlotFree(Room room, int time, int duration){
        if (room.dateContainer.containsKey(time) && room.dateContainer.get(time) != 0){
            return false;
        }
        else{
            // room.dateContainer.put(time, duration);
            return true;
        }
    }

    public static final Boolean checkRoomExists(Room room){
        if (container.containsKey(room)){ return true; } else { return false; }
    }
}
