package cm.bukkit.rpgfaction.handle;

import cm.bukkit.rpgfaction.Faction;
import cm.bukkit.rpgfaction.RPGFaction;
import org.bukkit.entity.Player;

public class ChatHandler extends AbstractHandler {

    public static ChatHandler INSTANCE;

    @Override
    public void onLoad() {
        INSTANCE = RPGFaction.getHandler(ChatHandler.class);
    }

    @Override
    public void onUnload() {

    }

    public boolean deployFactionMessage(Player sender, String message){
        return true;
    }
    public void deployConsoleFactionMessage(Faction faction, String message){
        faction.getOnlinePlayers()
                .forEach(i -> i.sendMessage(MessageHandler.INSTANCE.translateColorCode(message)));
    }


}
