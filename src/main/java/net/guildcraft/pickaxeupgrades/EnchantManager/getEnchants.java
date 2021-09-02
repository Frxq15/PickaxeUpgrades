package net.guildcraft.pickaxeupgrades.EnchantManager;

import net.guildcraft.pickaxeupgrades.EnchantManager.CustomEnchants.explosionEnchant;

public class getEnchants {
    public explosionEnchant explosionEnchant;

    public void initialize() {
        explosionEnchant = new explosionEnchant();
    }
    public explosionEnchant getTestEnchant() { return explosionEnchant; }
}
