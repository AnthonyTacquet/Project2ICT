package be.ikdoeict.aion2.global.DeLijn.Interface;


import java.util.ArrayList;

import be.ikdoeict.aion2.global.DeLijn.Normal.StopTime;

public interface IStopTime {
    public void AddStopTime(StopTime stopTime);
    public void AddStopTimes(ArrayList<StopTime> stopTimes);
}
