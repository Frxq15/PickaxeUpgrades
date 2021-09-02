package net.guildcraft.pickaxeupgrades.GUIs;

import net.guildcraft.gctokenmanager.SQLManagement.PlayerData;
import net.guildcraft.pickaxeupgrades.Objects.Pickaxe;
import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PickaxeGUI extends GUITemplate {
    private Player p;
    private Pickaxe pickaxe;
    private FileConfiguration fc = PickaxeUpgrades.getInstance().getConfig();
    public PickaxeGUI(Player p) {
        super(5, PickaxeUpgrades.formatMsg("GUIS.PICKAXE_GUI.TITLE"));
        this.p = p;
        this.pickaxe = new Pickaxe(p.getInventory().getItemInMainHand());
        initializeItems();
    }
    public void initializeItems() {
        registerMiscItems();
    }
    public void registerMiscItems() {
        setItem(fc.getInt("GUIS.PICKAXE_GUI.ITEMS.TOKENS.SLOT"), getItemFromConfig("GUIS.PICKAXE_GUI.ITEMS.TOKENS", p));
        setItem(fc.getInt("GUIS.PICKAXE_GUI.ITEMS.INFORMATION.SLOT"), getItemFromConfig("GUIS.PICKAXE_GUI.ITEMS.INFORMATION", p));
        setItem(fc.getInt("GUIS.PICKAXE_GUI.ITEMS.PLAYER_PICKAXE.SLOT"), currentlyUpgrading(pickaxe.asItem()));
        fc.getConfigurationSection("GUIS.PICKAXE_GUI.ITEMS.MISC_ITEMS").getKeys(false).forEach(m -> {
            if(fc.getBoolean("GUIS.PICKAXE_GUI.ITEMS.MISC_ITEMS."+m+".ENABLED")) {
                setItem(fc.getInt("GUIS.PICKAXE_GUI.ITEMS.MISC_ITEMS." + m + ".SLOT"), getItemFromConfig("GUIS.PICKAXE_GUI.ITEMS.MISC_ITEMS." + m, p));
            }
        });
        if(fc.getBoolean("GUIS.PICKAXE_GUI.ITEMS.ENABLE_COMING_SOON")) {
            int i = 31;
            while(i < 35) {
                setItem(i, createGuiItem(Material.GRAY_DYE, "&cComing Soon.."));
                i++;
            }
        }
        PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
        PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile().getConfigurationSection("ENCHANTMENTS").getKeys(false).forEach(ench -> {
            FileConfiguration f = PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile();
            if(PickaxeUpgrades.getInstance().getUpgradeManager(p).isMaximumLevel(Enchantment.getByName(ench))) {
                setItem(f.getInt("ENCHANTMENTS."+ench+".GUI_SLOT"), getItem(ench));
            }
            if(!PickaxeUpgrades.getInstance().getUpgradeManager(p).isMaximumLevel(Enchantment.getByName(ench))) {
                setItem(f.getInt("ENCHANTMENTS."+ench+".GUI_SLOT"), getItem(ench), player -> {
                    new ConfirmGUI(p, pickaxe, Enchantment.getByName(ench),
                            PickaxeUpgrades.getInstance().getUpgradeManager(p).getUpgradeCost(Enchantment.getByName(ench))).open(p);
                });
            }
            if(pd.getTokens() <  PickaxeUpgrades.getInstance().getUpgradeManager(p).getUpgradeCost(Enchantment.getByName(ench))) {
                setItem(f.getInt("ENCHANTMENTS."+ench+".GUI_SLOT"), getItem(ench), player -> {
                    p.sendMessage(PickaxeUpgrades.formatMsg("MESSAGES.CANNOT_AFFORD"));
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    delete();
                });
            }
        });
    }
    public ItemStack getItem(String enchant) {
        if(PickaxeUpgrades.getInstance().getUpgradeManager(p).isMaximumLevel(Enchantment.getByName(enchant))) {
            return getEnchantmentItem("GUIS.PICKAXE_GUI.ITEMS.ENCHANTMENT_ITEM_FORMAT_MAXIMUM", p, enchant, Enchantment.getByName(enchant));
        }
        return getEnchantmentItem("GUIS.PICKAXE_GUI.ITEMS.ENCHANTMENT_ITEM_FORMAT", p, enchant, Enchantment.getByName(enchant));
    }
}
