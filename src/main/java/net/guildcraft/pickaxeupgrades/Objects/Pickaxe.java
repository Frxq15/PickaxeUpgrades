package net.guildcraft.pickaxeupgrades.Objects;

import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Pickaxe {
    private ItemStack pick;

    public Pickaxe(ItemStack pickaxe) {
        pick = pickaxe;
    }
    public ItemMeta getMeta() { return pick.getItemMeta(); }
    public String getName() { return getMeta().getDisplayName(); }
    public List<String> getLore() { return getMeta().getLore(); }
    public Integer getEnchantLevel(String enchant) {
        if(isCustomEnchant(enchant)) {
            return PickaxeUpgrades.getInstance().getEnchantmentsManager().getCustomLevel(pick, enchant);
        }
        return pick.getEnchantmentLevel(Enchantment.getByName(enchant)); }
    public Material getType() { return pick.getType(); }
    public short getDurability() { return pick.getDurability(); }
    public void setDurability(short durability) { pick.setDurability(durability); }
    public void setEnchant(Enchantment enchant, int amount) { pick.addUnsafeEnchantment(enchant, amount); }
    public boolean hasEnchant(String enchant) {
        if(PickaxeUpgrades.getInstance().getEnchantmentsManager().getCustomLevel(pick, enchant) > 0) {
            return true;
        }
        return false;
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
    public boolean isCustomEnchant(String enchant) {
        if(PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile().getBoolean("ENCHANTMENTS."+enchant+".CUSTOM")) {
            return true;
        }
        return false;
    }
    //getupgrade cost
}
