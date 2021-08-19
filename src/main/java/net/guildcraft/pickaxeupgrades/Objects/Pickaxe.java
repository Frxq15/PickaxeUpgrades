package net.guildcraft.pickaxeupgrades.Objects;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Pickaxe {
    private ItemStack pick;

    public Pickaxe(ItemStack pickaxe) {
        pick = pickaxe;
    }
    public ItemMeta getMeta() { return pick.getItemMeta(); }
    public String getName() { return getMeta().getDisplayName(); }
}
