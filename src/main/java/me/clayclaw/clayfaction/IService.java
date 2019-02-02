package me.clayclaw.clayfaction;

import io.reactivex.Completable;

public interface IService {

    Completable load();
    Completable unload();

}
