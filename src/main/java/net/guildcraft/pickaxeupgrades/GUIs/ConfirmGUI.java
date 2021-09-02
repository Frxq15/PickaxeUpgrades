package net.guildcraft.pickaxeupgrades.GUIs;

import net.guildcraft.pickaxeupgrades.Objects.Pickaxe;
import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
        int newlvl = pick.getEnchantLevel(enchant)+1;
        setItem(fc.getInt("GUIS.CONFIRM_GUI.ITEMS.CANCEL_UPGRADE.SLOT"), getItemFromConfig("GUIS.CONFIRM_GUI.ITEMS.CANCEL_UPGRADE", p), player -> {
            delete();
            p.sendMessage(PickaxeUpgrades.formatMsg("MESSAGES.UPGRADE_CANCELLED"));
        });
        setItem(fc.getInt("GUIS.CONFIRM_GUI.ITEMS.PLAYER_PICKAXE.SLOT"), currentlyUpgrading(pick.asItem()));
        setItem(fc.getInt("GUIS.CONFIRM_GUI.ITEMS.INFORMATION.SLOT"), getUpgradeInfo(p, enchant, "INFORMATION"));
        for(String c : fc.getStringList("GUIS.CONFIRM_GUI.ITEMS.CONFIRM.SLOTS")) {
            int slot = Integer.parseInt(c);
            setItem(slot, getUpgradeInfo(p, enchant, "CONFIRM"), player -> {
                p.sendMessage(PickaxeUpgrades.formatMsg("MESSAGES.UPGRADE_COMPLETE")
                        .replace("%cost%", cost+"")
                        .replace("%enchant%", name).replace("%new%", newlvl+""));
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                PickaxeUpgrades.getInstance().getUpgradeManager(p).completeUpgrade(enchant, cost);
                delete();
            });
        }
        for(String c : fc.getStringList("GUIS.CONFIRM_GUI.ITEMS.CANCEL.SLOTS")) {
            int slot = Integer.parseInt(c);
            setItem(slot, getItemFromConfig("GUIS.CONFIRM_GUI.ITEMS.CANCEL", p), player -> {
                delete();
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                p.sendMessage(PickaxeUpgrades.formatMsg("MESSAGES.UPGRADE_CANCELLED"));
            });
        }
    }
}
