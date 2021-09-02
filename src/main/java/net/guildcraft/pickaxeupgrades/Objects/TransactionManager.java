package net.guildcraft.pickaxeupgrades.Objects;

import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionManager {
    private Player p;
    private FileConfiguration fc = PickaxeUpgrades.getInstance().getFileManager().getLogFile();
    public TransactionManager(Player p) {
        this.p = p;
    }
    public void createLog(String enchant, int level, int cost) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
        Date time = new Date(System.currentTimeMillis());
        fc.set(p.getName()+"."+formatter.format(date)+"."+formatter2.format(time)+".UPGRADE.ENCHANTMENT", enchant);
        fc.set(p.getName()+"."+formatter.format(date)+"."+formatter2.format(time)+".UPGRADE.NEW_LEVEL", level);
        fc.set(p.getName()+"."+formatter.format(date)+"."+formatter2.format(time)+".UPGRADE.COST", cost+" Tokens");
        PickaxeUpgrades.getInstance().getFileManager().saveLogFile();
        PickaxeUpgrades.getInstance().getFileManager().reloadLogFile();
    }
    public void getLogs(Player p) {
        p.sendMessage(PickaxeUpgrades.formatMsg("MESSAGES.LOG_HEADER"));
        fc.getConfigurationSection(p.getName()).getKeys(false).forEach(log -> {
            p.sendMessage(PickaxeUpgrades.formatMsg("MESSAGES.LOG_FORMAT").replace("%date%", "test"));
        });
    }
}
