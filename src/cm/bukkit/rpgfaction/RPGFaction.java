package cm.bukkit.rpgfaction;

import cm.bukkit.rpgfaction.handle.AbstractHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RPGFaction extends JavaPlugin {

    private static RPGFactionHandle factionHandle;
    private static FactionCommand factionCommand;
    private static final String PLUGIN_NAME = "RPGFaction";

    @Override
    public void onEnable(){
        factionHandle = new RPGFactionHandle();
        factionHandle.load();
        this.getServer().getPluginCommand("faction").setExecutor(factionCommand = new FactionCommand());
    }

    @Override
    public void onDisable(){
        factionHandle.unload();
    }

    public static RPGFaction getPlugin(){
        if(!Bukkit.getPluginManager().isPluginEnabled(PLUGIN_NAME))
            throw new IllegalStateException("PLUGIN NOT FOUND");

        return (RPGFaction) Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);
    }
    public static <T extends AbstractHandler> T getHandler(Class<? extends AbstractHandler> abstractClass){
        return (T) factionHandle.getHandler(abstractClass);
    }

}
