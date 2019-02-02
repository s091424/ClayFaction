package me.clayclaw.clayfaction.database;

import me.skymc.taboolib.common.configuration.TConfiguration;
import me.skymc.taboolib.common.inject.TInject;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YamlDatabase implements IDatabase {

    @TInject("db.yml")
    public static TConfiguration database;

    @Override
    public List<FactionData> extractData() {
        ConfigurationSection path = database.getConfigurationSection("Factions");
        ArrayList<FactionData> fdlist = new ArrayList<>();
        Arrays.asList(path.getKeys(true)).forEach(s  -> {
            FactionData data = new FactionData();
            data.name = database.getStringColored("Factions."+s+".name");
            data.capacity = database.getInt("Factions."+s+".capacity");
            data.usersAndPermissions = database.getStringList("Factions."+s+".usersAndPermissions");
            fdlist.add(data);
        });
        return fdlist;
    }

    @Override
    public void insertData(FactionData data) {
        database.set("Factions."+data.uuid+".name", data.name);
        database.set("Factions."+data.uuid+".capacity", data.capacity);
        database.set("Factions."+data.uuid+".usersAndPermissions", data.usersAndPermissions);
    }

}
