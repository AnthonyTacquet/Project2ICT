package be.ikdoeict.aion2.global.Main;


import java.util.ArrayList;

import be.ikdoeict.aion2.global.NMBS.Connection;

public class FaveriteConnection {
    private ArrayList<Connection> faveriteConection;
    public FaveriteConnection(){
        faveriteConection = new ArrayList<>();
    }
    public FaveriteConnection(Connection conn){
        faveriteConection = new ArrayList<>();
        if (!faveriteConection.contains(conn)){
            faveriteConection.add(conn);
        }
    }
    public FaveriteConnection(ArrayList<Connection> conns){
        faveriteConection = new ArrayList<>();
        for (Connection x:conns) {
            if (!faveriteConection.contains(x)){
                faveriteConection.add(x);
            }
        }
    }
    public void AddFaveriteConection(Connection conn){
        if (!faveriteConection.contains(conn)){
            faveriteConection.add(conn);
        }
    }
    public void RemoveFaveriteConnection(Connection conn){
        if (faveriteConection.contains(conn)){
            faveriteConection.remove(conn);
        }
    }
    public ArrayList<Connection> GetFaveriteConnection(){
        return faveriteConection;
    }
}
