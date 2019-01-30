package cm.bukkit.rpgfaction.handle;

import cm.bukkit.rpgfaction.Faction;
import cm.bukkit.rpgfaction.RPGFaction;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class FactionHandler extends AbstractHandler{

    public Set<Faction> factionList;

    @Override
    public void onLoad() {
        this.factionList = new HashSet<>();
    }

    @Override
    public void onUnload() {
        factionList.clear();
    }

    public void createFaction(Player creator, String name){
        if(haveFaction(creator)) return;
        Faction faction = new Faction(creator.getName(), name);
        factionList.add(faction);
    }
    public boolean haveFaction(Player player){
        return factionList.stream().anyMatch(faction -> faction.isMember(player.getName()));
    }
    public boolean haveFaction(String factionId){
        return factionList.stream().anyMatch(faction -> faction.getUUID().equals(factionId));
    }
    public Faction findFaction(Player player){
        return factionList.stream().filter(f -> f.isMember(player.getName())).findFirst().orElse(null);
    }
    public Faction findFaction(String factionId){
        return factionList.stream().filter(f -> f.getName().equals(factionId)).findFirst().orElse(null);
    }
    /**
     * @return success to kick or not
     */
    public boolean kickPlayer(Player player, String targetName){
        return findFaction(player).kickMember(player.getName(), targetName);
    }

    public static FactionHandler getHandler(){
        return RPGFaction.getHandler(FactionHandler.class);
    }

}
