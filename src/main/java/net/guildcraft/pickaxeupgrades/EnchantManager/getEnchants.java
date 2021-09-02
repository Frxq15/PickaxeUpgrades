package net.guildcraft.pickaxeupgrades.EnchantManager;

import net.guildcraft.pickaxeupgrades.EnchantManager.CustomEnchants.testEnchantment;

public class getEnchants {
    public testEnchantment testEnchant;

    public void initialize() {
        testEnchant = new testEnchantment();
    }
    public testEnchantment getTestEnchant() { return testEnchant; }
}
