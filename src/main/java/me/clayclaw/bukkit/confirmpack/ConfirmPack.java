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

    private int confirmpackCountdown;
    private int countdownTaskId;

    public ConfirmPack(Player target, int responseTime){
        this.uuid = UUID.randomUUID();
        this.target = target;
        ConfirmPackService.addConfirmPack(this);
        countdownTaskId = Bukkit.getScheduler().runTaskTimerAsynchronously(ClayFaction.plugin, () -> {
            confirmpackCountdown++;
            if(confirmpackCountdown >= (responseTime * 20)){
                confirm(Reply.DECLINED);
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

    protected void onCancel(){
        Bukkit.getScheduler().cancelTask(countdownTaskId);
    }

    public void confirm(Reply reply){
        switch (reply){
            case ACCEPTED:
                onAccept.subscribe();
                break;
            case DECLINED:
                onDecline.subscribe();
                break;
        }
        ((ConfirmPackService) ClayFaction.getService(ConfirmPackService.class)).remove(uuid);
    }

    public UUID getUUID() {
        return uuid;
    }
    public Player getPlayer() {
        return target;
    }
}
