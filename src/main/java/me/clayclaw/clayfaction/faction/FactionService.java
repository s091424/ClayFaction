package me.clayclaw.clayfaction.faction;

import io.reactivex.Completable;
import me.clayclaw.bukkit.confirmpack.ConfirmPack;
import me.clayclaw.bukkit.confirmpack.ConfirmPackService;
import me.clayclaw.clayfaction.ClayFaction;
import me.clayclaw.clayfaction.IService;
import me.clayclaw.clayfaction.database.DatabaseService;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

public class FactionService implements IService {

    private HashSet<Faction> factions;
    private DatabaseService dbservice;
    private ConfirmPackService confirmPackService;

    @Override
    public Completable load() {
        dbservice = (DatabaseService) ClayFaction.getService(DatabaseService.class);
        confirmPackService = (ConfirmPackService) ClayFaction.getService(ConfirmPackService.class);
        factions = new HashSet<>();
        dbservice.getDataList().forEach(data -> factions.add(new Faction(data)));
        return Completable.fromRunnable(() -> dbservice.getDataList().forEach(Faction::new));
    }

    @Override
    public Completable unload() {
        dbservice.getDataList().clear();
        return Completable.complete();
    }

    public Optional<Faction> getFaction(Player player){
        return factions.stream()
                .filter(faction -> faction.getRankMap().keySet().contains(player.getUniqueId()))
                .findFirst();
    }

    /***
     * @param uuid Faction UUID
     */
    public Optional<Faction> getFaction(UUID uuid){
        return factions.stream().filter(faction -> faction.getUUID().equals(uuid)).findFirst();
    }

    /***
     * Join a faction without invitation
     * @param uuid Faction UUID
     * @return success to join or not
     */
    public boolean joinFaction(Player player, UUID uuid){
        if(!getFaction(uuid).isPresent()) throw new IllegalArgumentException("Faction UUID not found.");
        if(getFaction(player).isPresent()) {
            getFaction(uuid).get().getRankMap().put(player.getUniqueId(), getFaction(uuid).get().getSetting().getDefaultRank());
            return true;
        }
        return false;
    }

    public void createFaction(Player player, String name){
        factions.add(new Faction(player,  name));
    }

    public void removeFaction(UUID uuid){
        factions.remove(getFaction(uuid).get());
    }

    /***
     * Send invitation to a player who haven't join a faction
     */
    public void sendInvitation(UUID uuid, Player player){

        Optional<Faction> playerFactionOptional = getFaction(player);
        if(playerFactionOptional.isPresent())
            throw new IllegalArgumentException("Player has already joined a faction.");

        Optional<Faction> factionOptional = getFaction(uuid);
        if(!factionOptional.isPresent())
            throw new IllegalArgumentException("UUID not found.");

        Faction sender = factionOptional.get();

        ConfirmPack pack = new ConfirmPack(player, ClayFaction.config.getInt("Invite.responseTime"));
        String packUUID = pack.getUUID().toString();

        pack.onAccept(Completable.fromAction(() -> {

        })).onDecline(Completable.fromAction(() -> {

        })).onExpire(Completable.fromAction(() -> {

        })).register();

    }



}
