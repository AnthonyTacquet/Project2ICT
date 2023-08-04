package be.ikdoeict.aion2.global.Main;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class FaveriteAlarm {
    private ArrayList<LocalDateTime> faveriteAlarm;
    public FaveriteAlarm(){
        faveriteAlarm = new ArrayList<>();
    }
    public FaveriteAlarm(LocalDateTime alarm){
        faveriteAlarm = new ArrayList<>();
        if (!faveriteAlarm.contains(alarm)){
            faveriteAlarm.add(alarm);
        }
    }
    public FaveriteAlarm(ArrayList<LocalDateTime> alarms){
        faveriteAlarm = new ArrayList<>();
        for (LocalDateTime x:alarms) {
            if (!faveriteAlarm.contains(x)){
                faveriteAlarm.add(x);
            }
        }
    }
    public void AddFaveriteAlarm(LocalDateTime alarm){
        if (!faveriteAlarm.contains(alarm)){
            faveriteAlarm.add(alarm);
        }
    }
    public void RemoveFaveriteAlarm(LocalDateTime alarm){
        if (faveriteAlarm.contains(alarm)){
            faveriteAlarm.remove(alarm);
        }
    }
    public ArrayList<LocalDateTime> GetFaveriteAlarm(){
        return faveriteAlarm;
    }
}
