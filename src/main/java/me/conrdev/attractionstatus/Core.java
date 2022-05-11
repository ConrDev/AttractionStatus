package me.conrdev.attractionstatus;

import com.google.common.collect.Maps;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.Util;

import me.conrdev.lib.gui.MenuAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;

public final class Core extends JavaPlugin {

    private static Core plugin;
    private Util util;

    public static YamlConfiguration lang;
    public static File langFile;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Loading Settings
        PluginManager pm = Bukkit.getServer().getPluginManager();
        plugin = this;

        // Loading Constructors
        ConfigManager.getInstance().setPlugin(this);
        Configs.getInstance().setPlugin(this);

//        new Configs(this);

        // Loading Configs
        boolean ConfigsLoaded = Configs.getInstance().loadConfigs();

        // TODO: Load.AllFiles();

        // Loading Util
//        util = new Util(this);

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
}
