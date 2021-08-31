package net.guildcraft.pickaxeupgrades.Objects;

import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UpgradeManager {
    private ItemStack pick;
    private Pickaxe pickaxe;
    public UpgradeManager(Player p) {
        this.pick = p.getInventory().getItemInMainHand();
        this.pickaxe = new Pickaxe(pick);
    }
    public boolean isMaximumLevel(Enchantment enchant) {
        if(pick.getEnchantmentLevel(enchant) >= PickaxeUpgrades.getInstance().getEnchantmentsManager().getMaxLevel(enchant.getName())) {
            return true;
        }
        return false;
    }
    public String getUpgradeCostText(Enchantment enchant) {
        if(isMaximumLevel(enchant)) {
            return "N/A";
        }
        int current = pickaxe.getEnchantLevel(enchant) + 1;
        String newup = String.valueOf(current);
        return PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile().getInt("ENCHANTMENTS."+enchant.getName()+".UPGRADE_COSTS."+newup)+" Tokens";
    }
    public int getUpgradeCost(Enchantment enchant) {
        if(isMaximumLevel(enchant)) {
            return 0;
        }
        int current = pickaxe.getEnchantLevel(enchant);
        String newup = String.valueOf((current+1));
        return PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile().getInt("ENCHANTMENTS."+enchant.getName()+".UPGRADE_COSTS."+newup);
    }
}
