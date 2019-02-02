package me.clayclaw.clayfaction.database;

import io.reactivex.Completable;
import me.clayclaw.clayfaction.ClayFaction;
import me.clayclaw.clayfaction.IService;

import java.util.List;

public class DatabaseService implements IService {

    private IDatabase database;
    private List<FactionData> dataList;

    @Override
    public Completable load() {
        DatabaseType selected = DatabaseType.valueOf(ClayFaction.config.getString("DatabaseType").toUpperCase());
        return Completable.fromRunnable(() -> {
            try {
                database = selected.targetClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            dataList = database.extractData();
        });
    }

    @Override
    public Completable unload() {
        return Completable.fromRunnable(() -> dataList.forEach(database::insertData));
    }

    public List<FactionData> getDataList(){
        return dataList;
    }

    enum DatabaseType {

        YAML(YamlDatabase.class),
        SQL(SQLDatabase.class);

        Class<? extends IDatabase> targetClass;

        DatabaseType(Class<? extends IDatabase> targetClass){
            this.targetClass = targetClass;
        }
    }
}
