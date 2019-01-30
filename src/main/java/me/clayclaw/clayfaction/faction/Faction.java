package me.clayclaw.clayfaction.faction;

import java.util.HashMap;

public class Faction {

    private String uuid;
    private String name;
    private HashMap<String, FactionRank> rankMap;
    private int capacity;

    public Faction(){
        rankMap = new HashMap<>();
    }

    public String getUUID(){
        return uuid;
    }
    public String getName(){
        return name;
    }
    public HashMap<String, FactionRank> getRankMap(){
        return rankMap;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
