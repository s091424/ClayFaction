package me.clayclaw.clayfaction.database;

import io.reactivex.Completable;
import me.clayclaw.clayfaction.AbstractService;

public class DatabaseService extends AbstractService {


    @Override
    public Completable load() {
        return Completable.complete();
    }

    @Override
    public Completable unload() {
        return Completable.complete();
    }
}
