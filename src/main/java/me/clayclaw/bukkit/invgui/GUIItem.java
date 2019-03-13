package me.clayclaw.bukkit.invgui;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUIItem {

    private ItemStack displayItem;

    private HashMap<ClickType, List<GUIItemAction>> clickMap;

    public GUIItem(ItemStack displayItem){
        clickMap = new HashMap<>();
        clickMap.put(ClickType.LEFT, new ArrayList<>());
        clickMap.put(ClickType.RIGHT, new ArrayList<>());
        this.displayItem = displayItem;
    }

    public GUIItem registerItemAction(ClickType type, GUIItemAction action){
        clickMap.get(type).add(action);
        return this;
    }

    protected List<GUIItemAction> getActions(ClickType type){
        return clickMap.get(type);
    }

}

