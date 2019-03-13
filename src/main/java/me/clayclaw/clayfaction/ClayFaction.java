package me.clayclaw.clayfaction;

import com.ilummc.tlib.annotations.Dependency;
import com.ilummc.tlib.logger.TLogger;
import me.clayclaw.bukkit.bridge.placeholder.PlaceholderService;
import me.clayclaw.bukkit.confirmpack.ConfirmPackService;
import me.clayclaw.clayfaction.bstats.Metrics;
import me.clayclaw.clayfaction.database.DatabaseService;
import me.clayclaw.clayfaction.faction.FactionService;
import me.skymc.taboolib.commands.builder.SimpleCommandBuilder;
import me.skymc.taboolib.common.configuration.TConfiguration;
import me.skymc.taboolib.common.inject.TInject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

@Dependency(type = Dependency.Type.LIBRARY, maven = "io.reactivex.rxjava2:rxjava:2.2.6")
public class ClayFaction extends JavaPlugin {

    protected static final String LANGUAGE = "ZHTW";

    @TInject
    public static Plugin plugin;
    @TInject("ClayFaction")
    public static TLogger logger;
    @TInject("config.yml")
    public static TConfiguration config;
    @TInject("lang.yml")
    public static TConfiguration langConfig;

    private Metrics metrics;

    private static HashMap<Class<? extends IService>, IService> serviceMap;

    @Override
    public void onEnable(){
        serviceMap = new HashMap<>();
        initConfig();
        initService();
        initCommand();
        metrics = new Metrics(this);
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
        serviceMap.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        serviceMap.values().forEach(services -> services.unload().subscribe(() ->
                        logger.info(services.getClass().toGenericString() + " is unloaded.")));
        serviceMap.clear();
    }

    private void initConfig(){

    }

    private void initCommand(){
        SimpleCommandBuilder.create("cf", this)
                .description("Command for ClayFaction")
                .execute((sender, args) -> {
                    if(args.length <= 0){
                        Bukkit.dispatchCommand(sender, "cf help");
                        return false;
                    }
                    if(!(sender instanceof Player)){
                        sender.sendMessage(GlobalMessage.getMessage("MUSTBEPLAYER"));
                        return false;
                    }
                    if(sender.hasPermission("cf."+args[0])){
                        sender.sendMessage(langConfig.getStringColored("NoPermission"));
                        return false;
                    }
                    Player player = (Player) sender;
                    switch (args[0]){
                        case "reload":
                            config.reload();
                            langConfig.reload();
                            player.sendMessage(GlobalMessage.getMessage("RELOADED"));
                            break;
                        case "admin":

                            break;
                        case "invite":

                            break;
                        case "create":
                            if(Objects.isNull(((FactionService)getService(FactionService.class)).getFaction(player))){
                                if(args[1] != null){
                                    ((FactionService)getService(FactionService.class)).createFaction(player, args[1]);
                                    sender.sendMessage(langConfig.getStringColored("FactionSuccessfullyCreated"));
                                }else{
                                    sender.sendMessage(langConfig.getStringColored("FactionNameIsRequired"));
                                }
                            }else{
                                sender.sendMessage(langConfig.getStringColored("AlreadyJoinFaction"));
                            }
                            break;
                        default:
                            break;
                    }
                    return true;
                }).build();
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
        CONFIRM_PACK(ConfirmPackService.class),
        FACTION(FactionService.class),
        SUPPORT_PLACEHOLDER(PlaceholderService.class);

        Class<? extends IService> targetClass;
        Services(Class<? extends IService> targetClass){
            this.targetClass = targetClass;
        }
    }

}
