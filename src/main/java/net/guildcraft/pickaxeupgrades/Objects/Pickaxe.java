package net.guildcraft.pickaxeupgrades.Objects;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Pickaxe {
    private ItemStack pick;

    public Pickaxe(ItemStack pickaxe) {
        pick = pickaxe;
    }
    public ItemMeta getMeta() { return pick.getItemMeta(); }
    public String getName() { return getMeta().getDisplayName(); }
    public List<String> getLore() { return getMeta().getLore(); }
    public Integer getEnchantLevel(Enchantment enchant) { return pick.getEnchantmentLevel(enchant); }
    public Material getType() { return pick.getType(); }
    public short getDurability() { return pick.getDurability(); }
    public void setDurability(short durability) { pick.setDurability(durability); }
    public void setEnchant(Enchantment enchant, int amount) { pick.addUnsafeEnchantment(enchant, amount); }
    public boolean hasEnchant(String enchant) {
        if(pick.getEnchantments().containsKey(Enchantment.getByName(enchant))) { return true; }
        return false;
    }
    public void increaseEnchant(Enchantment enchant, int amount) {
        int upgrade = getEnchantLevel(enchant) + amount;
        pick.addUnsafeEnchantment(enchant, upgrade);
    }
    public boolean isPickaxe() {
        if(getType().toString().toLowerCase().contains("pickaxe")) {
            return true;
        }
        return false;
    }
    public ItemStack asItem() {
        return pick;
    }
    //getmaxupgrade level
    //getupgrade cost

}
