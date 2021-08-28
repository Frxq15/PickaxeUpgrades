package net.guildcraft.pickaxeupgrades;

import net.guildcraft.pickaxeupgrades.Objects.Pickaxe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {
    public File LogFile;
    public FileConfiguration LogConfig;

    public FileConfiguration getLogFile() {
        return this.LogConfig;
    }

    public void reloadLogFile() {
        LogConfig = YamlConfiguration.loadConfiguration(LogFile);
    }
    public void saveLogFile() {
        try {
            LogConfig.save(LogFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createLogFile() {
        LogFile = new File(PickaxeUpgrades.getInstance().getDataFolder(), "logger.yml");
        if (!LogFile.exists()) {
            LogFile.getParentFile().mkdirs();
            PickaxeUpgrades.getInstance().log("log.yml was created successfully");
            PickaxeUpgrades.getInstance().saveResource("log.yml", false);
        }

        LogConfig = new YamlConfiguration();
        try {
            LogConfig.load(LogFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
