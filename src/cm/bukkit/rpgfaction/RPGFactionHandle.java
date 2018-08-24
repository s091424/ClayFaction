package cm.bukkit.rpgfaction;

import cm.bukkit.rpgfaction.handle.*;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.HashMap;

public class RPGFactionHandle {

    private HashMap<Class<? extends AbstractHandler>, AbstractHandler> handlersMap;

    public RPGFactionHandle(){}

    public void load(){
        handlersMap = new HashMap<>();
        Arrays.stream(Handlers.values()).forEach(handler -> {
            try {
                AbstractHandler objectHandler = handler.getHandler().newInstance();
                handlersMap.put(handler.getHandler(), objectHandler);
                objectHandler.onLoad();
                Bukkit.getLogger().info("RPGFaction Handler Loaded: " + handler.toString());
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                Bukkit.getLogger().warning("RPGFaction Handler Failed: " + handler.toString());
            }
        });
    }
    public void unload(){
        handlersMap.values().forEach(AbstractHandler::onUnload);
        handlersMap.clear();
    }

    public <T extends AbstractHandler> T getHandler(Class<? extends AbstractHandler> handlerClass){
        return (T) handlersMap.get(handlerClass);
    }

    private enum Handlers {

        INVITATION(InviteHandler.class),
        FACTION(FactionHandler.class),
        GUI(GuiHandler.class),
        MESSAGE(MessageHandler.class);

        private Class<? extends AbstractHandler> handler;

        Handlers(Class<? extends AbstractHandler> handler){
            this.handler = handler;
        }

        public Class<? extends AbstractHandler> getHandler(){
            return this.handler;
        }

    }

}
