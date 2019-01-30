package me.clayclaw.clayfaction.database;

import java.util.List;

public abstract class AbstractDatabase {

    abstract public List<FactionData> extractData();
    abstract public void insertData(FactionData data);

}
