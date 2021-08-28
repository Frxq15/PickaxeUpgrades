package net.guildcraft.pickaxeupgrades.GUIs;

import net.guildcraft.gctokenmanager.SQLManagement.PlayerData;
import net.guildcraft.pickaxeupgrades.Objects.Pickaxe;
import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PickaxeGUI extends GUITemplate {
    private Player p;
    private Pickaxe pickaxe;
    private FileConfiguration fc = PickaxeUpgrades.getInstance().getConfig();
    public PickaxeGUI(Player p) {
        super(6, PickaxeUpgrades.formatMsg("GUIS.PICKAXE_GUI.TITLE"));
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
        setItem(fc.getInt("GUIS.PICKAXE_GUI.ITEMS.PLAYER_PICKAXE.SLOT"), pickaxe.asItem());
    }
}
