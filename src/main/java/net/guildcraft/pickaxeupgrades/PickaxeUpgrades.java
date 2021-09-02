package net.guildcraft.pickaxeupgrades;

import net.guildcraft.gctokenmanager.GCTokenManager;
import net.guildcraft.pickaxeupgrades.Commands.pUpgradesCommand;
import net.guildcraft.pickaxeupgrades.EnchantManager.EnchantmentRegistry;
import net.guildcraft.pickaxeupgrades.EnchantManager.Enchantments;
import net.guildcraft.pickaxeupgrades.EnchantManager.getEnchants;
import net.guildcraft.pickaxeupgrades.Objects.TransactionManager;
import net.guildcraft.pickaxeupgrades.Objects.UpgradeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PickaxeUpgrades extends JavaPlugin {
    private static PickaxeUpgrades instance;
    public FileManager fileManager;
    public Enchantments enchantments;
    public UpgradeManager upgradeManager;
    public TransactionManager transactionManager;
    public EnchantmentRegistry enchantmentRegistry;
    public getEnchants getEnchantments;

    @Override
    public void onEnable() {
        initializeClasses();
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        getFileManager().createLogFile();
        getFileManager().createUpgradesFile();
        getEnchantmentsList().initialize();
        getEnchantmentRegistry().registration();
        log("Plugin enabled successfully.");
        dependencyCheck();
    }

    @Override
    public void onDisable() {
        log("Plugin disabled.");
        getEnchantmentRegistry().unregisterEnchants();
    }

    public void initializeClasses() {
        instance = this;
        fileManager = new FileManager();
        enchantments = new Enchantments();
        enchantmentRegistry = new EnchantmentRegistry();
        getEnchantments = new getEnchants();
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        getCommand("pupgrades").setExecutor(new pUpgradesCommand());
    }
    public void dependencyCheck() {
        if(!Bukkit.getPluginManager().isPluginEnabled("GCTokenManager")) {
            log("GCTokenManager dependency not found, cannot start plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
    public static PickaxeUpgrades getInstance() { return instance; }
    public FileManager getFileManager() { return fileManager; }
    public Enchantments getEnchantmentsManager() { return enchantments; }
    public getEnchants getEnchantmentsList() { return getEnchantments; }
    public UpgradeManager getUpgradeManager(Player p) {
        upgradeManager = new UpgradeManager(p);
        return upgradeManager; }
        public EnchantmentRegistry getEnchantmentRegistry() { return enchantmentRegistry; }
    public TransactionManager getTransactionManager(Player p) {
        transactionManager = new TransactionManager(p);
        return transactionManager; }
    public void log(String log) { Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+"[PickaxeUpgrades] "+log); }
    public static String colourize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
    public static List<String> colourize(List<String> input) {
        List<String> newList = new ArrayList<>();
        for(String entry : input) {
            newList.add(colourize(entry));
        }
        return newList;
    }
    public static String formatMsg(String input) { return ChatColor.translateAlternateColorCodes('&', getInstance().getConfig().getString(input)); }
    public static String formatUpgradesMsg(String input) { return ChatColor.translateAlternateColorCodes('&', getInstance().getFileManager().getUpgradesFile().getString(input)); }
}
