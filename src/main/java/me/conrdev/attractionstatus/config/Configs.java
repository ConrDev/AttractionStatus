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

    public static FileConfiguration configFile;
    public static ArrayList<FileConfiguration> langList = new ArrayList<>();

    Configs() {
        this.configManager = ConfigManager.getInstance();

    }

    public static Configs getInstance() {
        if (instance == null) instance = new Configs();

        return instance;
    }

    public void setPlugin(Core plugin) {
        this.plugin = plugin;
    }

    public boolean loadConfigs() {
        Util.msg(Bukkit.getConsoleSender(), "Loading config files...");

        if (!loadConfigFile()) {
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return false;
        }

        loadLangFiles();

//        configManager.getConfig("nl-NL.yml", langFolder);
//        configManager.getConfig("en-US.yml", langFolder);
        return true;
    }

    private boolean loadConfigFile() {
        configFile = configManager.getConfig("config.yml");
        if (configFile == null) {
            Util.msg(Bukkit.getConsoleSender(), "&bconfig.yml: &cFailed");
            return false;
        }

        Util.msg(Bukkit.getConsoleSender(), "&bconfig.yml: &aSuccess");
        return true;
    }

    private void loadLangFiles() {
        String jarFolder = "lang";
        String langFolder = plugin.getDataFolder() + File.separator + jarFolder;

//        if (!Files.exists(Paths.get(langFolder))) {
            try {
                JarUtil.copyFolderFromJar(jarFolder, plugin.getDataFolder(), JarUtil.CopyOption.COPY_IF_NOT_EXIST);
            } catch (IOException e) {
                e.printStackTrace();
                Util.msg(Bukkit.getConsoleSender(), "&bLoading lang files: &cFailed");
            }
//        }

        File[] langFiles = new File(langFolder).listFiles();

        for (File langFile : langFiles) {
//            Util.msg(Bukkit.getConsoleSender(), configManager.getConfig("lang/" + langFile.getName()).toString());
            FileConfiguration File = configManager.getConfig(langFile.getName(), langFolder);

            if (File == null) {
                Util.msg(Bukkit.getConsoleSender(), "&blang/" + langFile.getName() + ": &cFailed");
            } else {
                langList.add(File);
                Util.msg(Bukkit.getConsoleSender(), "&blang/" + langFile.getName() + ": &aSuccess");
            }
        }

    }
}
