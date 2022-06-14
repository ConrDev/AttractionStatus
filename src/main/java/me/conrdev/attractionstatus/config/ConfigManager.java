package me.conrdev.attractionstatus.config;

import me.conrdev.attractionstatus.Core;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ConfigManager {
    private static ConfigManager instance = null;
//    private final ArrayList<FileConfiguration> configs = new ArrayList<>();
//    private final ArrayList<String> configNames = new ArrayList<>();
    private final LinkedHashMap<String, FileConfiguration> configs = new LinkedHashMap<>();

    public Core plugin = null;

    public static ConfigManager getInstance() {
        if (instance == null) instance = new ConfigManager();

        return instance;
    }

    public void setPlugin(Core plugin) {
        this.plugin = plugin;
    }

    public void reloadConfigs() {
        configs.clear();
//        configNames.clear();
    }

    public void showConfigs() {
        Util.msg(Bukkit.getConsoleSender(), plugin.CONSOLE_PREFIX + configs.keySet());
    }

    public FileConfiguration getConfigFile(String name) {
        if (configs.size() > 0) {
            for (Map.Entry<String, FileConfiguration> config : configs.entrySet()) {
                if (config.getKey().equalsIgnoreCase(name)) return config.getValue();
            }
        }

        return createNewConfig(name, "");
    }

    public FileConfiguration getConfigFile(String name, String path) {
        if (configs.size() > 0) {
//            for (FileConfiguration config : configs) {
//                if (config.getName().equalsIgnoreCase(name)) return config;
//            }
            for (Map.Entry<String, FileConfiguration> config : configs.entrySet()) {
                if (config.getKey().equalsIgnoreCase(name)) return config.getValue();
            }

        }

        return createNewConfig(name, path);
    }

    private FileConfiguration createNewConfig(String name, String path) {
        if (path.isEmpty()) path = plugin.getDataFolder().getPath();
        FileConfiguration fileConfig;
        File configFile = new File(path, name);
//        Util.msg(Bukkit.getConsoleSender(), plugin.CONSOLE_PREFIX + configFile.getPath());

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();

            if (plugin.getResource(name) != null) {
                plugin.saveResource(name, false);
            } else {
                try {
                    Files.createFile(Paths.get(configFile.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        fileConfig = new YamlConfiguration();

        try {
            fileConfig.load(configFile);
            configs.put(path + "/" + name, fileConfig);
//            configs.add(fileConfig);
//            configNames.add(name);

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
//            config.save(new File(plugin.getDataFolder(), configNames.get(configs.indexOf(config))));
//            Util.msg(Bukkit.getConsoleSender(), plugin.CONSOLE_PREFIX + config.getParent());
            for (Map.Entry<String, FileConfiguration> file : configs.entrySet()) {
                if (file.getValue() == config) config.save(new File(file.getKey()));
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public String getRawString(FileConfiguration config, String path) {
        return config.getString(path);
    }

    public String getString(FileConfiguration config, String path) {
        if (!config.contains(path)) setData(config, path, "dummy string");

        return config.getString(path);
    }

    public String[] getRawStrings(FileConfiguration config, String path) {
        return new String[]{config.getString(path)};
    }

    public UUID getUUID(FileConfiguration config, String path) {
        if (!config.contains(path)) return null;

        return UUID.fromString(config.getString(path));
    }

    public int getInt(FileConfiguration config, String path) {
        if (!config.contains(path)) setData(config, path, 1);

        return config.getInt(path);
    }

    public int getRawInt(FileConfiguration config, String path) {
        return config.getInt(path);
    }

    public float getFloat(FileConfiguration config, String path) {
        if (!config.contains(path)) setData(config, path, 1);

        return (float) config.getDouble(path);
    }

    public float getRawFloat(FileConfiguration config, String path) {

        return (float) config.getDouble(path);
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

    public List<?> getRawList(FileConfiguration config, String path) {

        return config.getList(path);
    }

    public boolean setLocation(FileConfiguration config, String path, Location location) {
        config.set(String.format("%s.world", path), location.getWorld().getName());
        config.set(String.format("%s.x", path), location.getX());
        config.set(String.format("%s.y", path), location.getY());
        config.set(String.format("%s.z", path), location.getZ());
        config.set(String.format("%s.yaw", path), location.getYaw());
        config.set(String.format("%s.pitch", path), location.getPitch());

        return saveData(config);
    }

    public Location getRawLocation(FileConfiguration config, String path) {
        String worldName = getString(config, String.format("%s.world", path));

//        Bukkit.getServer().createWorld(new WorldCreator(worldName));

        World world = Bukkit.getWorld(worldName);
        double x = getDouble(config, String.format("%s.x", path));
        double y = getDouble(config, String.format("%s.y", path));
        double z = getDouble(config, String.format("%s.z", path));
        float yaw = getRawFloat(config, String.format("%s.yaw", path));
        float pitch = getRawFloat(config, String.format("%s.pitch", path));

        return new Location(world, x, y, z, yaw, pitch);
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