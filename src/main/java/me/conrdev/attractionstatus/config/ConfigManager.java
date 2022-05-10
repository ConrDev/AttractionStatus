package me.conrdev.attractionstatus.config;

import me.conrdev.attractionstatus.Core;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigManager {
    private static ConfigManager single_instance = null;

    private ArrayList<FileConfiguration> configs = new ArrayList<>();
    private ArrayList<String> configNames = new ArrayList<>();

    private Core plugin = null;

    public void setPlugin(Core plugin) {
        this.plugin = plugin;
    }

    public void reloadConfigs() {
        configs.clear();
        configNames.clear();
    }

    public FileConfiguration getConfig(String name) {
        if (configs.size() > 0) {
            for (FileConfiguration config : configs) {
                if (config.getName().equalsIgnoreCase(name)) return config;
            }
        }

        return createNewConfig(name);
    }

    private FileConfiguration createNewConfig(String name) {
        FileConfiguration fileConfig;
        File configFile = new File(plugin.getDataFolder(), name);

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }

        fileConfig = new YamlConfiguration();

        try {
            fileConfig.load(configFile);
            configs.add(fileConfig);
            configNames.add(name);

            return fileConfig;
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean saveData(FileConfiguration config) {
        try {
            config.save(new File(plugin.getDataFolder(), configNames.get(configs.indexOf(config))));
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public 
}
