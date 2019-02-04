package me.clayclaw.clayfaction.database;

import me.clayclaw.clayfaction.ClayFaction;
import me.skymc.taboolib.mysql.IHost;
import me.skymc.taboolib.mysql.builder.SQLColumn;
import me.skymc.taboolib.mysql.builder.SQLColumnType;
import me.skymc.taboolib.mysql.builder.SQLHost;
import me.skymc.taboolib.mysql.builder.SQLTable;
import me.skymc.taboolib.mysql.hikari.HikariHandler;

import javax.sql.DataSource;
import java.util.List;

public class SQLDatabase implements IDatabase {

    private SQLHost host;
    private SQLTable table;
    private DataSource dataSource;

    public SQLDatabase(){
        host = new SQLHost(ClayFaction.config.getConfigurationSection("SQLDatabase"), ClayFaction.plugin);
        table = new SQLTable("FactionData", SQLColumn.PRIMARY_KEY_ID,
                new SQLColumn(SQLColumnType.TEXT, "uuid"),
                new SQLColumn(SQLColumnType.TEXT, "name"),
                new SQLColumn(SQLColumnType.INT, "capacity"),
                new SQLColumn(SQLColumnType.LONGTEXT, "users"));
        try {
            dataSource = HikariHandler.createDataSource((IHost) host);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        table.executeUpdate(table.createQuery())
                .dataSource(dataSource)
                .run();
    }

    @Override
    public List<FactionData> extractData() {
        table.executeQuery("");
        return null;
    }

    @Override
    public void insertData(FactionData data) {
        table.executeInsert("?, ?, ?, ?")
                .statement(s -> {
                    s.setString(1, data.uuid);
                    s.setString(2, data.name);
                    s.setInt(3, data.capacity);
                    s.setNString(4, convertUsersToString(data.usersAndPermissions));
                })
                .run();
    }

    private String convertUsersToString(List<String> usersAndPermissions){
        StringBuilder builder = new StringBuilder();
        usersAndPermissions.forEach(s -> builder.append(s));
        return builder.toString();
    }
}
