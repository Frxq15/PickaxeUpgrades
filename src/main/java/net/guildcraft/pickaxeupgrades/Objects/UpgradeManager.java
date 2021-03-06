package net.guildcraft.pickaxeupgrades.Objects;

import net.guildcraft.gctokenmanager.SQLManagement.PlayerData;
import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UpgradeManager {
    private ItemStack pick;
    private Pickaxe pickaxe;
    private Player p;
    public UpgradeManager(Player p) {
        this.pick = p.getInventory().getItemInMainHand();
        this.pickaxe = new Pickaxe(pick);
        this.p = p;
    }
    public boolean isMaximumLevel(Enchantment enchant) {
        if(pickaxe.getEnchantLevel(enchant.getName()) >= PickaxeUpgrades.getInstance().getEnchantmentsManager().getMaxLevel(enchant.getName())) {
            return true;
        }
        return false;
    }
    public String getUpgradeCostText(Enchantment enchant) {
        if(isMaximumLevel(enchant)) {
            return "N/A";
        }
        int current = pickaxe.getEnchantLevel(enchant.getName()) + 1;
        String newup = String.valueOf(current);
        return PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile().getInt("ENCHANTMENTS."+enchant.getName()+".UPGRADE_COSTS."+newup)+" Tokens";
    }
    public int getUpgradeCost(Enchantment enchant) {
        if(isMaximumLevel(enchant)) {
            return 0;
        }
        int current = pickaxe.getEnchantLevel(enchant.getName());
        String newup = String.valueOf((current+1));
        return PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile().getInt("ENCHANTMENTS."+enchant.getName()+".UPGRADE_COSTS."+newup);
    }
    public void completeUpgrade(String enchant, int cost) {
        int newlvl = pickaxe.getEnchantLevel(enchant) + 1;
        ItemMeta meta = pick.getItemMeta();
        pick.setItemMeta(meta);
        String name = PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile().getString("ENCHANTMENTS."+enchant+".NAME");
        if(pickaxe.isCustomEnchant(enchant)) {
            PickaxeUpgrades.getInstance().getEnchantmentsManager().addEnchantmentLore(pick, name, newlvl);
        }
        meta.addEnchant(Enchantment.getByName(enchant), newlvl, true);
        PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
        pd.takeTokens(cost);
        PickaxeUpgrades.getInstance().getTransactionManager(p).createLog(enchant, newlvl, cost);
    }
}
