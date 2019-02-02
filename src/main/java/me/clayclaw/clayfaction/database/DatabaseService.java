package me.clayclaw.clayfaction.database;

import io.reactivex.Completable;
import me.clayclaw.clayfaction.IService;

public class DatabaseService implements IService {


    @Override
    public Completable load() {
        return Completable.complete();
    }

    @Override
    public Completable unload() {
        return Completable.complete();
    }

    enum DatabaseType {

        YAML(null),
        SQL(SQLDatabase.class);

        Class<? extends IDatabase> targetClass;

        DatabaseType(Class<? extends IDatabase> targetClass){
            this.targetClass = targetClass;
        }
    }
}
