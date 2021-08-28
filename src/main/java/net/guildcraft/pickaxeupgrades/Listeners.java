package net.guildcraft.pickaxeupgrades;

import net.guildcraft.pickaxeupgrades.GUIs.GUITemplate;
import net.guildcraft.pickaxeupgrades.GUIs.PickaxeGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class Listeners implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (!(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
        if (!e.getPlayer().isSneaking()) return;
        if (!e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)) return;
        PickaxeGUI gui = new PickaxeGUI(e.getPlayer());
        gui.open(e.getPlayer());
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();
        UUID inventoryUUID = PickaxeGUI.openInventories.get(playerUUID);
        if (inventoryUUID != null) {
            e.setCancelled(true);
            GUITemplate gui = GUITemplate.getInventoriesByUUID().get(inventoryUUID);
            PickaxeGUI.GUIAction action = gui.getActions().get(e.getSlot());
            if(e.getClickedInventory() != player.getOpenInventory().getTopInventory()) return;
            if (action != null) {
                action.click(player);
            }
        }
    }
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        UUID playerUUID = player.getUniqueId();
        PickaxeGUI.openInventories.remove(playerUUID);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = (Player) e.getPlayer();
        UUID playerUUID = player.getUniqueId();
        PickaxeGUI.openInventories.remove(playerUUID);
    }
}
