package me.clayclaw.clayfaction.database;

import java.util.List;

public interface IDatabase {

    List<FactionData> extractData();
    void insertData(FactionData data);

}
