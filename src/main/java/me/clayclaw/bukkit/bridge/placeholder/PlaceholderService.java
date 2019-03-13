package me.clayclaw.bukkit.bridge.placeholder;

import io.reactivex.Completable;
import me.clayclaw.clayfaction.ClayFaction;
import me.clayclaw.clayfaction.IService;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderService implements IService {

    private static Map<String, PlaceholderResponse> responseMap;

    @Override
    public Completable load() {
        responseMap = new HashMap<>();
        new PlaceholderServiceExpansion().register();
        return Completable.complete();
    }

    @Override
    public Completable unload() {
        responseMap.clear();
        return Completable.complete();
    }

    public static void addPlaceholder(String s, PlaceholderResponse r){
        responseMap.put(s, r);
    }

    private PlaceholderResponse getResponse(String key){
        return responseMap.get(key);
    }

    private class PlaceholderServiceExpansion extends PlaceholderExpansion {

        @Override
        public String getIdentifier() {
            return ClayFaction.plugin.getDescription().getName().toLowerCase();
        }

        @Override
        public String getPlugin() {
            return null;
        }

        @Override
        public String getAuthor() {
            return ClayFaction.plugin.getDescription().getAuthors().toString();
        }

        @Override
        public String getVersion() {
            return ClayFaction.plugin.getDescription().getVersion();
        }

        @Override
        public String onPlaceholderRequest(Player player, String s) {
            return getResponse(s).response(player);
        }

    }

}
