package net.guildcraft.pickaxeupgrades.EnchantManager;

import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Enchantments {
    public int getMaxLevel(String enchant) {
        return PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile().getInt("ENCHANTMENTS."+enchant+".MAXIMUM_LEVEL");
    }
    public String getAllEnchantments() {
        List<String> enchants = new ArrayList<>();
        PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile()
                .getConfigurationSection("ENCHANTMENTS").getKeys(false).forEach(enchant -> {
            enchants.add(enchant);
        });
        return enchants.toString().replace("[", "").replace("]", "");
    }
    public String getDescription(String enchant) {
        return PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile().getString("ENCHANTMENTS."+enchant+".DESCRIPTION");
    }
    public String getEnchantmentName(Enchantment enchant) {
        return PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile().getString("ENCHANTMENTS."+enchant+".NAME");
    }
}
