package cm.bukkit.rpgfaction;

import cm.bukkit.rpgfaction.handle.ChatHandler;
import cm.bukkit.rpgfaction.handle.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class Faction {

    private String name;
    private String uuid;
    private HashMap<String, FactionRole> players;
    private HashMap<FactionRole, Set<FactionPermission>> rolePermission;

    public Faction(String creator, String name){
        this.name = name;
        this.players = new HashMap<>();
        this.rolePermission = new HashMap<>();
        iniNewValue(creator);
    }

    private void iniNewValue(String creator){
        uuid = UUID.randomUUID().toString();

        Set<FactionPermission> ownerperm = new HashSet<>();
        ownerperm.add(FactionPermission.EDIT_PERMISSION);
        ownerperm.add(FactionPermission.INVITE_MEMBER);
        ownerperm.add(FactionPermission.KICK_MEMBER);
        ownerperm.add(FactionPermission.PROMOTE_LEADER);
        Set<FactionPermission> modperm = new HashSet<>();
        modperm.add(FactionPermission.INVITE_MEMBER);
        modperm.add(FactionPermission.KICK_MEMBER);
        Set<FactionPermission> memberperm = new HashSet<>();

        rolePermission.put(FactionRole.LEADER, ownerperm);
        rolePermission.put(FactionRole.MODERATOR, modperm);
        rolePermission.put(FactionRole.MEMBER, memberperm);

        players.put(creator, FactionRole.LEADER);
    }

    public boolean togglePermission(String editor, FactionRole role, FactionPermission perm){
        if(!checkPermission(editor, FactionPermission.EDIT_PERMISSION) ||
                perm == FactionPermission.PROMOTE_LEADER){
            return false;
        }

        if(checkPermission(role, perm)){
            rolePermission.get(role).remove(perm);
        }else{
            rolePermission.get(role).add(perm);
        }
        return true;
    }
    public boolean checkPermission(FactionRole role, FactionPermission perm){
        return rolePermission.get(role).contains(perm);
    }
    public boolean checkPermission(String name, FactionPermission perm){
        return checkPermission(getRoleByName(name), perm);
    }
    public FactionRole getRoleByName(String name){
        return players.get(name);
    }
    public Set<String> getMembersByRole(FactionRole role){
        Set<String> members = new HashSet<>();
        players.forEach((key, value) -> {
            if (value == role) members.add(key);
        });
        return members;
    }
    public boolean isMember(String name){
        return players.containsKey(name);
    }
    public boolean kickMember(String operator, String name){
        if(checkPermission(operator, FactionPermission.KICK_MEMBER)){
            return false;
        }
        players.remove(name);
        ChatHandler.getHandler().sendConsoleFactionMessage(this,
                MessageHandler.getHandler().getMessage(MessageHandler.FactionMessage.KICKED_MEMBER)
                        .replace("!player", name)
        .replace("!operator", operator));
        return true;
    }
    public void removeMember(String name){
        players.remove(name);
        ChatHandler.getHandler().sendConsoleFactionMessage(this,
                MessageHandler.getHandler().getMessage(MessageHandler.FactionMessage.MEMBER_LEAVE)
        .replace("!member", name));
    }
    public String getName() {
        return this.name;
    }
    public String getUUID() {
        return uuid;
    }
    public Set<Player> getOnlinePlayers(){
        HashSet<Player> online = new HashSet<>();
        players.keySet().stream()
                .filter(i -> !Objects.isNull(Bukkit.getServer().getPlayer(i)))
                .forEach(i -> online.add(Bukkit.getServer().getPlayer(i)));
        return online;
    }
}
