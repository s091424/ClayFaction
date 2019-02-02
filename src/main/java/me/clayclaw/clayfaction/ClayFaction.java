package me.clayclaw.clayfaction;

import com.ilummc.tlib.annotations.Dependency;
import com.ilummc.tlib.logger.TLogger;
import me.clayclaw.clayfaction.database.DatabaseService;
import me.clayclaw.clayfaction.faction.FactionService;
import me.skymc.taboolib.commands.builder.SimpleCommandBuilder;
import me.skymc.taboolib.common.configuration.TConfiguration;
import me.skymc.taboolib.common.inject.TInject;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;

@Dependency(type = Dependency.Type.LIBRARY, maven = "io.reactivex.rxjava2:rxjava:2.2.6")
public class ClayFaction extends JavaPlugin {

    @TInject
    public static Plugin plugin;
    @TInject("ClayFaction")
    public static TLogger logger;
    @TInject("config.yml")
    public static TConfiguration config;

    private static HashMap<Class<? extends IService>, IService> serviceMap;

    @Override
    public void onEnable(){
        serviceMap = new HashMap<>();
        initService();
        initCommand();
        logger.info("\n" +
                "   _____ _             ______         _   _             \n" +
                "  / ____| |           |  ____|       | | (_)            \n" +
                " | |    | | __ _ _   _| |__ __ _  ___| |_ _  ___  _ __  \n" +
                " | |    | |/ _` | | | |  __/ _` |/ __| __| |/ _ \\| '_ \\ \n" +
                " | |____| | (_| | |_| | | | (_| | (__| |_| | (_) | | | |\n" +
                "  \\_____|_|\\__,_|\\__, |_|  \\__,_|\\___|\\__|_|\\___/|_| |_|\n" +
                "                  __/ |                                 \n" +
                "                 |___/                                  \n");
    }

    @Override
    public void onDisable(){
        destroyService();
    }

    private void destroyService(){
        serviceMap.values().forEach(services ->
                services.unload().subscribe(() ->
                        logger.info(services.getClass().toGenericString() + " is unloaded.")));
        serviceMap.clear();
    }
    private void initCommand(){
        SimpleCommandBuilder.create("cf", this)
                .description("Command for ClayFaction")
                .execute((sender, args) -> {

                    return true;
                })
                .build();
    }
    private void initService(){
        Arrays.stream(Services.values())
                .forEach(services -> {
                    try {
                        serviceMap.put(services.targetClass, services.targetClass.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
        );
        serviceMap.values().forEach(services ->
                services.load().subscribe(() ->
                        logger.info(services.getClass().toGenericString() + " is loaded.")));
    }

    public static IService getService(Class<? extends IService> targetClass){
        return serviceMap.get(targetClass);
    }

    enum Services {

        DATABASE(DatabaseService.class),
        FACTION(FactionService.class);

        Class<? extends IService> targetClass;
        Services(Class<? extends IService> targetClass){
            this.targetClass = targetClass;
        }
    }

}
