package me.conrdev.attractionstatus.config;

import me.conrdev.attractionstatus.Core;
import me.conrdev.attractionstatus.utils.JarUtil;
import me.conrdev.attractionstatus.utils.Util;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Configs {

    private Core plugin = null;

    private static Configs instance;
    private ConfigManager configManager;

//    public static FileConfiguration configFile;
    public static ArrayList<FileConfiguration> langList = new ArrayList<FileConfiguration>();
//    public static ArrayList<FileConfiguration> attractionList = new ArrayList<>();

    Configs() {
        this.configManager = Core.getConfigManager();
    }

    public static Configs getInstance() {
        if (instance == null) instance = new Configs();

        return instance;
    }

    public void setPlugin(Core plugin) {
        this.plugin = plugin;
    }

    // loading configs



    public boolean loadConfigs() {
        Util.msg(Bukkit.getConsoleSender(), plugin.CONSOLE_PREFIX + "Loading configuration...");

        if (!loadConfigFile()) {
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return false;
        }

        loadLangFiles();
        loadAttractionsFile();
        loadZonesFile();

        return true;
    }

    private boolean loadConfigFile() {
        FileConfiguration configFile = getConfig();

        if (configFile == null) {
            Util.msg(Bukkit.getConsoleSender(), plugin.CONSOLE_PREFIX + "config.yml: &cFailed");

            return false;
        }

        Util.msg(Bukkit.getConsoleSender(), plugin.CONSOLE_PREFIX + "config.yml: &aSuccess");

        return true;
    }

    private void loadLangFiles() {
        String jarFolder = "lang";
//        String langFolder = plugin.getDataFolder() + File.separator + jarFolder;

        try {
            JarUtil.copyFolderFromJar(jarFolder, getPluginFolder(), JarUtil.CopyOption.COPY_IF_NOT_EXIST);
        } catch (IOException e) {
            e.printStackTrace();
            Util.msg(Bukkit.getConsoleSender(), plugin.CONSOLE_PREFIX + "Loading lang files: &cFailed");
        }

        File[] langFiles = new File(getLangFolder()).listFiles();

        for (File langFile : langFiles) {
            FileConfiguration File = getLang(langFile.getName());

            if (File == null) {
                Util.msg(Bukkit.getConsoleSender(), plugin.CONSOLE_PREFIX + "lang/" + langFile.getName() + ": &cFailed");
            } else {
                langList.add(File);
                Util.msg(Bukkit.getConsoleSender(), plugin.CONSOLE_PREFIX + "lang/" + langFile.getName() + ": &aSuccess");
            }
        }
    }

    private void loadAttractionsFile() {
        FileConfiguration attractionsFile = getAttractions();

        if (attractionsFile == null) {
            Util.msg(Bukkit.getConsoleSender(), plugin.CONSOLE_PREFIX + "data/attractions.yml: &cFailed");

            return;
        }

        Util.msg(Bukkit.getConsoleSender(), plugin.CONSOLE_PREFIX + "data/attractions.yml: &aSuccess");
    }

    private void loadZonesFile() {
        FileConfiguration zonesFile = getZones();

        if (zonesFile == null) {
            Util.msg(Bukkit.getConsoleSender(), plugin.CONSOLE_PREFIX + "data/zones.yml: &cFailed");

            return;
        }

        Util.msg(Bukkit.getConsoleSender(), plugin.CONSOLE_PREFIX + "data/zones.yml: &aSuccess");
    }

    // Get configs

    public FileConfiguration getConfig() {
        return configManager.getConfigFile("config.yml");
    }

    public FileConfiguration getLang() {
        return configManager.getConfigFile(plugin.getPluginLang(), getLangFolder());
    }

    public FileConfiguration getLang(String name) {
        return configManager.getConfigFile(name, getLangFolder());
    }

    public FileConfiguration getAttractions() {
        return configManager.getConfigFile("attractions.yml", getDataFolder());
    }

    public FileConfiguration getZones() {
        return configManager.getConfigFile("zones.yml", getDataFolder());
    }

    // Get folders

    public String getLangFolder() {
        return getPluginFolder() + "/lang";
    }

    public String getDataFolder() {
        return getPluginFolder() + "/data";
    }

    public File getPluginFolder() {
        return plugin.getDataFolder();
    }
}
