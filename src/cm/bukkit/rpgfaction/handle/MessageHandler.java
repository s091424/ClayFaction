package cm.bukkit.rpgfaction.handle;

import cm.bukkit.rpgfaction.RPGFaction;
import org.bukkit.ChatColor;

public class MessageHandler extends AbstractHandler{

    public static MessageHandler INSTANCE;

    @Override
    public void onLoad() {
        INSTANCE = RPGFaction.getHandler(this.getClass());
    }

    @Override
    public void onUnload() {}

    public String getMessage(FactionMessage msg){
        return "MISSING: " + msg.toString();
    }
    public String translateColorCode(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public enum FactionMessage{

        CREATED_FACTION,
        KICKED_MEMBER,
        INVITED_MEMBER, //
        INVITE_FAIL_NOT_ONLINE, // !player
        INVITE_FAIL_NO_FACTION, //
        ALREADY_INVITED, //
        INVITE_FAIL_HAVE_FACTION,
        INVITE_CANCELLED,
        INVITE_REJECTED,
        INVITE_ACCEPTED,
        INVITE_MESSAGE, // !sender !faction
        EDITED_PERMISSION,
        CHANGED_ROLE,
        PROMOTED_LEADER,
        FACTION_NOT_FOUND //

    }

}
