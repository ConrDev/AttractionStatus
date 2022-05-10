package me.conrdev.attractionstatus.config;

import me.conrdev.attractionstatus.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private static ConfigManager single_instance = null;

    private ArrayList<FileConfiguration> configs = new ArrayList<>();
    private ArrayList<String> configNames = new ArrayList<>();

    private Core plugin = null;

    public static ConfigManager getInstance() {
        if (single_instance == null) single_instance = new ConfigManager();

        return single_instance;
    }

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

    public boolean setData(FileConfiguration config, String path, Object value) {
        config.set(path, value);

        return saveData(config);
    }

    public boolean saveData(FileConfiguration config) {
        try {
            config.save(new File(plugin.getDataFolder(), configNames.get(configs.indexOf(config))));
        } catch (IOException e) {
            return false;
        }

        return true;
    }


    public String getString(FileConfiguration config, String path) {
        if (!config.contains(path)) setData(config, path, "dummy string");

        return config.getString(path);
    }

    public int getInt(FileConfiguration config, String path) {
        if (!config.contains(path)) setData(config, path, 1);

        return config.getInt(path);
    }

    public double getDouble(FileConfiguration config, String path) {
        if (!config.contains(path)) setData(config, path, 1.0);

        return config.getDouble(path);
    }

    public boolean getBoolean(FileConfiguration config, String path) {
        if (!config.contains(path)) setData(config, path, false);

        return config.getBoolean(path);
    }

    public List<?> getList(FileConfiguration config, String path) {
        if (!config.contains(path))
            setData(config,
                    path,
                    new ArrayList<Location>()
                            .add(new Location(
                                    Bukkit.getWorld("world"),
                                    10,
                                    10,
                                    10)
                            )
            );

        return config.getList(path);
    }

    public boolean addLocation(FileConfiguration config, String path, Location location) {
        config.set(String.format("%s.world", path), location.getWorld().getName());
        config.set(String.format("%s.x", path), location.getX());
        config.set(String.format("%s.y", path), location.getY());
        config.set(String.format("%s.z", path), location.getZ());

        return saveData(config);
    }

    public Location getLocation(FileConfiguration config, String path) {
        String worldName = getString(config, String.format("%s.world", path));

        Bukkit.getServer().createWorld(new WorldCreator(worldName));

        World world = Bukkit.getWorld(worldName);
        int x = getInt(config, String.format("%s.x", path));
        int y = getInt(config, String.format("%s.y", path));
        int z = getInt(config, String.format("%s.z", path));

        return new Location(world, x, y, z);
    }

}