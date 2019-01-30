package me.clayclaw.clayfaction;

import io.reactivex.Completable;

public abstract class AbstractService {

    abstract public Completable load();
    abstract public Completable unload();

}
