package me.clayclaw.clayfaction.database;

import me.clayclaw.clayfaction.faction.Faction;

import java.util.ArrayList;
import java.util.List;

public class FactionData {

    public String uuid;
    public String name;
    public List<String> usersAndPermissions;
    public int capacity;

    public String settingDefaultRank;

    protected FactionData(){}

    public FactionData(Faction faction){
        uuid = faction.getUUID().toString();
        name = faction.getName();
        usersAndPermissions = new ArrayList<>();
        faction.getRankMap().forEach((key, value) -> usersAndPermissions.add(key+":"+value));
        capacity = faction.getCapacity();

        settingDefaultRank = faction.getSetting().getDefaultRank().toString();
    }

}
