package cm.bukkit.rpgfaction.handle;

import cm.bukkit.rpgfaction.RPGFaction;
import org.bukkit.ChatColor;

public class MessageHandler extends AbstractHandler{

    @Override
    public void onLoad() {

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
        KICKED_MEMBER, //!operator !player
        MEMBER_LEAVE, // !member
        INVITED_MEMBER, //
        INVITE_FAIL_NOT_ONLINE, // !player
        INVITE_FAIL_NO_FACTION, //
        ALREADY_INVITED, //
        INVITE_FAIL_HAVE_FACTION,
        INVITE_FAIL_FACTION_NOT_FOUND,
        INVITE_RESPONSE_INCORRECT,
        INVITE_CANCELLED,
        INVITE_REJECTED,
        INVITE_ACCEPTED,
        INVITE_MESSAGE, // !sender !faction
        EDITED_PERMISSION,
        CHANGED_ROLE,
        PROMOTED_LEADER,
        FACTION_NOT_FOUND, //
        PLAYER_CHAT

    }

    public static MessageHandler getHandler(){
        return RPGFaction.getHandler(MessageHandler.class);
    }

}
