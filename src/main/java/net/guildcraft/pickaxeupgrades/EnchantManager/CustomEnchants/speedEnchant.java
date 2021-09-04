package net.guildcraft.pickaxeupgrades.EnchantManager.CustomEnchants;

import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class speedEnchant extends Enchantment {
    public speedEnchant() {
        super(new NamespacedKey(PickaxeUpgrades.getInstance(), "SPEED"));
    }
    @Override
    public String getName() {
        return "SPEED";
    }

    @Override
    public int getMaxLevel() {
        return 1000;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        if(itemStack.getType().toString().toLowerCase().contains("pickaxe")) {
            return true;
        }
        return false;
    }
}
