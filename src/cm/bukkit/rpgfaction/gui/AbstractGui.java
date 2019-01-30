package cm.bukkit.rpgfaction.gui;

import cm.bukkit.rpgfaction.RPGFaction;
import cm.bukkit.rpgfaction.handle.GuiHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractGui {

    abstract public String getName();

    abstract public void build(Player player);

    public void registerEventClick(ItemStack item, Runnable runnable){
        ((GuiHandler) RPGFaction.getHandler(GuiHandler.class)).registerItemAction(item, runnable);
    }

}
