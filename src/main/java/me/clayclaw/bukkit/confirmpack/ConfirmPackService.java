package me.clayclaw.bukkit.confirmpack;

import io.reactivex.Completable;
import me.clayclaw.clayfaction.IService;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

public class ConfirmPackService implements IService {

    private static HashSet<ConfirmPack> confirmpackSet;

    @Override
    public Completable load() {
        confirmpackSet = new HashSet<>();
        return Completable.complete();
    }

    @Override
    public Completable unload() {
        return Completable.complete();
    }

    public void remove(UUID uuid){
        confirmpackSet.stream().filter(confirmPack -> confirmPack.getUUID().equals(uuid)).forEach(ConfirmPack::onCancel);
        confirmpackSet.removeIf(confirmPack -> confirmPack.getUUID().equals(uuid));
    }

    public Optional<ConfirmPack> getConfirmPack(UUID uuid){
        return confirmpackSet.stream().filter(confirmPack -> confirmPack.getUUID().equals(uuid)).findFirst();
    }

    protected static void addConfirmPack(ConfirmPack confirmPack){
        confirmpackSet.add(confirmPack);
    }

}
