package net.guildcraft.pickaxeupgrades.EnchantManager.CustomEnchants;

import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class explosionEnchant extends Enchantment {

    public explosionEnchant() {
        super(new NamespacedKey(PickaxeUpgrades.getInstance(), "EXPLOSION"));
    }

    @Override
    public String getName() {
        return "EXPLOSION";
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
        return true;
    }
}
