package cm.bukkit.rpgfaction.handle;

import cm.bukkit.rpgfaction.Faction;
import cm.bukkit.rpgfaction.RPGFaction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class InviteHandler extends AbstractHandler{

    private static HashMap<Player, Set<Faction>> invitations;

    public static boolean sendInvitation(String senderName, String playerName){

        Player sender = Bukkit.getPlayer(senderName);
        Player player = Bukkit.getPlayer(playerName);

        if(!FactionHandler.getHandler().haveFaction(sender)){
            sender.sendMessage(
                    MessageHandler.getHandler().getMessage(
                            MessageHandler.FactionMessage.INVITE_FAIL_NO_FACTION));
            return false;
        }

        Faction faction = FactionHandler.getHandler().findFaction(sender);

        if(player == null || !player.isOnline()){
            sender.sendMessage(
                    MessageHandler.getHandler().getMessage(
                            MessageHandler.FactionMessage.INVITE_FAIL_NOT_ONLINE)
            .replace("!player", playerName));
            return false;
        }

        if(!checkInviteDuplication(player, faction)){
            invitations.get(player).add(faction);
            player.sendMessage(
                    MessageHandler.getHandler().getMessage(
                            MessageHandler.FactionMessage.INVITE_MESSAGE)
                    .replace("!sender", sender.getName())
                    .replace("!faction", faction.getName())
            );
            sender.sendMessage(
                    MessageHandler.getHandler().getMessage(
                            MessageHandler.FactionMessage.INVITED_MEMBER
                    )
            );
            return true;
        }else{
            sender.sendMessage(
                    MessageHandler.getHandler().getMessage(
                            MessageHandler.FactionMessage.ALREADY_INVITED));
            return false;
        }
    }
    public static boolean responseInvitation(Player player, String factionId, String response){

        initPlayer(player);

        if(!FactionHandler.getHandler().haveFaction(factionId)){
            player.sendMessage(
                    MessageHandler.getHandler().getMessage(
                            MessageHandler.FactionMessage.FACTION_NOT_FOUND
                    )
            );
            return false;
        }

        Faction inviteFaction = FactionHandler.getHandler().findFaction(factionId);
        if(!invitations.get(player).contains(inviteFaction)){
            player.sendMessage(
                    MessageHandler.getHandler().getMessage(
                            MessageHandler.FactionMessage.INVITE_FAIL_FACTION_NOT_FOUND
                    )
            );
            return false;
        }

        switch(response){
            case "accept":
                if(FactionHandler.getHandler().haveFaction(player))
                    FactionHandler.getHandler().findFaction(player).removeMember(player.getName());

                return true;
            case "reject":

                return true;
            default:
                player.sendMessage(
                        MessageHandler.getHandler().getMessage(
                                MessageHandler.FactionMessage.INVITE_RESPONSE_INCORRECT
                        )
                );
                break;
        }

        return false;
    }
    private static boolean checkInviteDuplication(Player player, Faction faction){
        initPlayer(player);
        return invitations.get(player).contains(faction);
    }
    private static void initPlayer(Player player){
        if(!invitations.containsKey(player)) invitations.put(player, new HashSet<>());
    }

    @Override
    public void onLoad() {
        invitations = new HashMap<>();
    }

    @Override
    public void onUnload() {
        invitations.clear();
    }

    public static InviteHandler getHandler(){
        return RPGFaction.getHandler(InviteHandler.class);
    }
}
