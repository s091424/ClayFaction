package cm.bukkit.rpgfaction.handle;

import cm.bukkit.rpgfaction.RPGFaction;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class GuiHandler extends AbstractHandler implements Listener {

    public static GuiHandler INSTANCE;
    private HashMap<ItemStack, Runnable> itemAction;

    protected GuiHandler() {}

    public void registerItemAction(ItemStack item, Runnable runnable){
        if(!itemAction.containsKey(item)) itemAction.put(item, runnable);
    }

    @Override
    public void onLoad() {
        itemAction = new HashMap<>();
        Bukkit.getServer().getPluginManager().registerEvents(this, RPGFaction.getPlugin());
        INSTANCE = RPGFaction.getHandler(this.getClass());
    }

    @Override
    public void onUnload() {
        itemAction.clear();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(!itemAction.containsKey(e.getCurrentItem())) return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(
                RPGFaction.getPlugin(),
                itemAction.get(e.getCurrentItem()),
                1
        );
    }
}
