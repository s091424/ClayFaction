package cm.bukkit.rpgfaction.handle;

import cm.bukkit.rpgfaction.Faction;
import cm.bukkit.rpgfaction.FactionPermission;
import cm.bukkit.rpgfaction.RPGFaction;
import org.bukkit.entity.Player;

public class ChatHandler extends AbstractHandler {

    @Override
    public void onLoad() {

    }

    @Override
    public void onUnload() {

    }

    /**
     * @return success to send or not
     */
    public boolean sendFactionMessage(Player sender, String message){
        if(!FactionHandler.getHandler().haveFaction(sender) ||
                FactionHandler.getHandler().findFaction(sender).checkPermission(
                        sender.getName(), FactionPermission.SEND_MESSAGE
                )) return false;
        sendConsoleFactionMessage(FactionHandler.getHandler().findFaction(sender),
                MessageHandler.getHandler().getMessage(MessageHandler.FactionMessage.PLAYER_CHAT)
        .replace("!player", sender.getName()));
        return true;
    }

    public void sendConsoleFactionMessage(Faction faction, String message){
        faction.getOnlinePlayers().forEach(i -> i.sendMessage(MessageHandler.getHandler().translateColorCode(message)));
    }

    public static ChatHandler getHandler(){
        return RPGFaction.getHandler(ChatHandler.class);
    }
}
