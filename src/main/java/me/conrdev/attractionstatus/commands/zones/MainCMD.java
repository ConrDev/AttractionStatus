package me.conrdev.attractionstatus.commands.zones;

import me.conrdev.attractionstatus.commands.ZonesCMD;
import me.conrdev.attractionstatus.commands.abstracts.DefaultAttractionStatusCMD;
import me.conrdev.attractionstatus.commands.abstracts.DefaultZonesCMD;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.command.CommandSender;

import java.util.Iterator;
import java.util.Set;

public class MainCMD extends DefaultZonesCMD {

    private final ZonesCMD executor;
    private final Configs configs;
    private final ConfigManager configManager;

    public MainCMD(ZonesCMD executor, Configs configs, ConfigManager configManager) {
        super("Menu",
                "/zones",
                configManager.getRawString(configs.getLang(), "commands.zones.menu"),
                "attractionstatus.zones.menu");

        this.executor = executor;
        this.configs = configs;
        this.configManager = configManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Util.msg(sender, "Zones Menu");

    }
}
