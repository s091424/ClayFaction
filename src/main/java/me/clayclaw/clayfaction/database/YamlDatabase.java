package me.clayclaw.clayfaction.database;

import me.skymc.taboolib.common.configuration.TConfiguration;
import me.skymc.taboolib.common.inject.TInject;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class YamlDatabase implements IDatabase {

    @TInject("db.yml")
    public static TConfiguration database;

    @Override
    public List<FactionData> extractData() {
        ConfigurationSection path = database.getConfigurationSection("Factions");
        ArrayList<FactionData> fdlist = new ArrayList<>();
        path.getKeys(true).forEach(s -> {
            FactionData data = new FactionData();
            data.uuid = s;
            data.name = database.getStringColored("Factions." + s + ".name");
            data.capacity = database.getInt("Factions." + s + ".capacity");
            data.usersAndPermissions = database.getStringList("Factions." + s + ".usersAndPermissions");
            data.settingDefaultRank = database.getString("Factions." + s + ".setting.defaultRank");
            fdlist.add(data);
        });
        database.set("Factions", "");
        return fdlist;
    }

    @Override
    public void insertData(FactionData data) {
        database.set("Factions."+data.uuid+".name", data.name);
        database.set("Factions."+data.uuid+".capacity", data.capacity);
        database.set("Factions."+data.uuid+".usersAndPermissions", data.usersAndPermissions);
        database.set("Factions."+data.uuid+".setting.defaultRank", data.settingDefaultRank);
    }

}
