package net.guildcraft.pickaxeupgrades;

import net.guildcraft.gctokenmanager.GCTokenManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PickaxeUpgrades extends JavaPlugin {
    private static PickaxeUpgrades instance;
    public FileManager fileManager;

    @Override
    public void onEnable() {
        initializeClasses();
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        getFileManager().createLogFile();
        log("Plugin enabled successfully.");
        dependencyCheck();
    }

    @Override
    public void onDisable() {
        log("Plugin disabled.");
    }

    public void initializeClasses() {
        instance = this;
        fileManager = new FileManager();
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
    }
    public void dependencyCheck() {
        if(!Bukkit.getPluginManager().isPluginEnabled(GCTokenManager.getInstance())) {
            log("GCTokenManager dependency not found, cannot start plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
    public static PickaxeUpgrades getInstance() { return instance; }
    public FileManager getFileManager() { return fileManager; }
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
}
