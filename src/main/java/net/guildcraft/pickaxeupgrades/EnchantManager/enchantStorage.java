package net.guildcraft.pickaxeupgrades.EnchantManager;

import net.guildcraft.pickaxeupgrades.EnchantManager.CustomEnchants.explosionEnchant;
import net.guildcraft.pickaxeupgrades.EnchantManager.CustomEnchants.hasteEnchant;
import net.guildcraft.pickaxeupgrades.EnchantManager.CustomEnchants.speedEnchant;

public class enchantStorage {
    public explosionEnchant explosionEnchant;
    public speedEnchant speedEnchant;
    public hasteEnchant hasteEnchant;

    public void initialize() {
        explosionEnchant = new explosionEnchant();
        speedEnchant = new speedEnchant();
        hasteEnchant = new hasteEnchant();
    }
    public explosionEnchant getExplosionEnchant() { return explosionEnchant; }
    public speedEnchant getSpeedEnchant() { return speedEnchant; }
    public hasteEnchant getHasteEnchant() { return hasteEnchant; }
}
