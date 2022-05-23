package me.conrdev.attractionstatus;

import com.google.common.collect.Maps;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.managers.AttractionManager;
import me.conrdev.attractionstatus.managers.ZoneManager;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.Util;

import me.conrdev.lib.gui.MenuAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;

public final class Core extends JavaPlugin {

    private static Core plugin;

    private static ConfigManager configManager;
    private static Configs configs;
    private static AttractionManager attractionManager;
    private static ZoneManager zoneManager;
    private Util util;

    public YamlConfiguration lang;
    public File langFile;

    private String LANG;

    public String CHAT_PREFIX;
    public String CONSOLE_PREFIX;


    @Override
    public void onEnable() {
        // Plugin startup logic
        this.CONSOLE_PREFIX = "[" + getDescription().getPrefix() + "] " + ChatColor.RESET;

        // Loading Settings
        PluginManager pm = Bukkit.getServer().getPluginManager();
        plugin = this;

        // Loading Constructors
        setConfigManager();
        setConfigs();
//        ConfigManager.getInstance().setPlugin(this);
//        ConfigManager configManager = ConfigManager.getInstance();

//        Configs.getInstance().setPlugin(this);
//        Configs configs = Configs.getInstance();

//        new Configs(this);

        // Loading Configs
        boolean ConfigsLoaded = configs.loadConfigs();

        setPluginLang(configManager.getString(configs.getConfig(), "AttractionStatus.Lang"));
        setPrefix(configManager.getString(configs.getConfig(), "AttractionStatus.Prefix"));

        // TODO: Load.AllFiles();

        // Loading Managers
        setAttractionManager();
        setZoneManager();

        // Loading Commands
        // TODO

        // Loading Folders
        // TODO

        // Finished
//        Util.msg(Bukkit.getConsoleSender(), MsgUtil.BANNER.getMessages());
        if (ConfigsLoaded) consoleBanner(1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        consoleBanner(0);
    }

    private void consoleBanner(int status) {
        String[] statusString = { "&cDisabled", "&aEnabled" };

        final Map<String, String> map = Maps.newHashMap();

        map.put("%status%", statusString[status]);
        map.put("%pluginName%", getPluginName());
        map.put("%pluginVersion%", getPluginVersion());

        MsgUtil.BANNER.msg(Bukkit.getConsoleSender(), map, false);
    }

    public static String getPluginName() {
        return plugin.getDescription().getName();
    }

    public static String getPluginVersion() {
        return plugin.getDescription().getVersion();
    }

    public String getPluginLang() {
        return this.LANG;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static Configs getConfigs() {
        return configs;
    }

    public static AttractionManager getAttractionManager() {
        return attractionManager;
    }

    public static ZoneManager getZoneManager() {
        return zoneManager;
    }

    private void setPluginLang(String language) {
        this.LANG = language;
        Util.msg(plugin.getServer().getConsoleSender(),  CONSOLE_PREFIX + "Language set to: &d" + language);
    }

    private void setPrefix(String prefix) {
        this.CHAT_PREFIX = "[" + prefix + "] " + ChatColor.RESET;
        Util.msg(plugin.getServer().getConsoleSender(),  CONSOLE_PREFIX + "Chat Prefix set to: &d" + prefix);
    }

    private void setConfigManager() {
        configManager = ConfigManager.getInstance();
        configManager.setPlugin(this);
    }

    private void setConfigs() {
        configs = Configs.getInstance();
        configs.setPlugin(this);
    }

    private void setAttractionManager() {
        attractionManager = AttractionManager.getInstance();
        attractionManager.setPlugin(this);
    }

    private void setZoneManager() {
        zoneManager = ZoneManager.getInstance();
        zoneManager.setPlugin(this);
    }
}
