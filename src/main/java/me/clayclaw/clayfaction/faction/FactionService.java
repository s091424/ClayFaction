package me.clayclaw.clayfaction.faction;

import io.reactivex.Completable;
import io.reactivex.Observable;
import me.clayclaw.clayfaction.AbstractService;

public class FactionService extends AbstractService {

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
