package me.clayclaw.bukkit.confirmpack;

import io.reactivex.Completable;
import me.clayclaw.clayfaction.ClayFaction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ConfirmPack {

    private UUID uuid;

    private Player target;
    private Completable onAccept;
    private Completable onDecline;
    private Completable onExpire;

    private int confirmpackCountdown;
    private int countdownTaskId;

    public ConfirmPack(Player target, int responseTime){
        this.uuid = UUID.randomUUID();
        this.target = target;
        ConfirmPackService.addConfirmPack(this);
        countdownTaskId = Bukkit.getScheduler().runTaskTimerAsynchronously(ClayFaction.plugin, () -> {
            confirmpackCountdown++;
            if(confirmpackCountdown >= (responseTime * 20)){
                expire();
            }
        }, 1, 1).getTaskId();
    }

    public ConfirmPack onAccept(Completable completable){
        this.onAccept = completable;
        return this;
    }
    public ConfirmPack onDecline(Completable completable){
        this.onDecline = completable;
        return this;
    }
    public ConfirmPack onExpire(Completable completable){
        this.onExpire = completable;
        return this;
    }
    public ConfirmPack register(){
        ConfirmPackService.addConfirmPack(this);
        return this;
    }

    public UUID getUUID() {
        return uuid;
    }
    public Player getPlayer() {
        return target;
    }

    protected void onCancel(){
        Bukkit.getScheduler().cancelTask(countdownTaskId);
    }

    public void confirm(Reply reply){
        switch (reply){
            case ACCEPT:
                onAccept.subscribe();
                break;
            case DECLINE:
                onDecline.subscribe();
                break;
        }
        ((ConfirmPackService) ClayFaction.getService(ConfirmPackService.class)).remove(uuid);
    }

    private void expire(){
        onExpire.subscribe();
        ((ConfirmPackService) ClayFaction.getService(ConfirmPackService.class)).remove(uuid);
    }


}
