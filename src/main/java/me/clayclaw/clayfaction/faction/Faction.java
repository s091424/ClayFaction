package me.clayclaw.clayfaction.faction;

import me.clayclaw.clayfaction.ClayFaction;
import me.clayclaw.clayfaction.database.FactionData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Faction {

    private UUID uuid;
    private String name;
    private HashMap<UUID, FactionRank> rankMap;
    private int capacity;
    private FactionSetting setting;

    protected Faction(Player owner, String name){
        rankMap = new HashMap<>();
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.rankMap.put(owner.getUniqueId(), FactionRank.LEADER);
        this.capacity = ClayFaction.config.getInt("Default.capacity");
        setting = new FactionSetting();
    }

    public Faction(FactionData data) {
        rankMap = new HashMap<>();
        this.uuid = UUID.fromString(data.uuid);
        this.name = data.name;
        this.capacity = data.capacity;
        data.usersAndPermissions.forEach(s ->
                rankMap.put(UUID.fromString(s.split(":")[0]), FactionRank.valueOf(s.split(":")[1])));
        setting = new FactionSetting();
    }

    public UUID getUUID(){
        return uuid;
    }
    public String getName(){
        return name;
    }
    public HashMap<UUID, FactionRank> getRankMap(){
        return rankMap;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public FactionSetting getSetting() {
        return setting;
    }
}
