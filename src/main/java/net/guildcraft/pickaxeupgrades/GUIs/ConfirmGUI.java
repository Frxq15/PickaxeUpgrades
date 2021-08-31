package net.guildcraft.pickaxeupgrades.GUIs;

import net.guildcraft.pickaxeupgrades.Objects.Pickaxe;
import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class ConfirmGUI extends GUITemplate {
    private Player p;
    private Pickaxe pick;
    private Enchantment enchant;
    private int cost;
    private String name;
    private FileConfiguration fc = PickaxeUpgrades.getInstance().getConfig();
    public ConfirmGUI(Player p, Pickaxe pickaxe, Enchantment enchant, int cost) {
        super(5, PickaxeUpgrades.formatMsg("GUIS.CONFIRM_GUI.TITLE"));
        this.p = p;
        this.pick = pickaxe;
        this.enchant = enchant;
        this.cost = cost;
        this.name = PickaxeUpgrades.getInstance().getEnchantmentsManager().getEnchantmentName(enchant);
        initializeItems();
    }
    public void initializeItems() {
        setItem(fc.getInt("GUIS.CONFIRM_GUI.ITEMS.CANCEL_UPGRADE.SLOT"), getItemFromConfig("GUIS.CONFIRM_GUI.ITEMS.CANCEL_UPGRADE", p), player -> {
            p.getOpenInventory().close();
            p.sendMessage(PickaxeUpgrades.formatMsg("MESSAGES.UPGRADE_CANCELLED"));
            delete();
        });
    }
}
