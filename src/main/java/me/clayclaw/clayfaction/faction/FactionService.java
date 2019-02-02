package me.clayclaw.clayfaction.faction;

import io.reactivex.Completable;
import io.reactivex.Observable;
import me.clayclaw.clayfaction.IService;

public class FactionService implements IService {

    private Observable<Faction> factionObservable;

    public FactionService(){
        factionObservable = Observable.fromCallable(() -> {
            return null;
        });
    }


    @Override
    public Completable load() {
        return null;
    }

    @Override
    public Completable unload() {
        return null;
    }
}
