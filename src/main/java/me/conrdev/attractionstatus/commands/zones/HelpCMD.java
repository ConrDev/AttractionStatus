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

public class HelpCMD extends DefaultZonesCMD {

    private final ZonesCMD executor;
    private final Configs configs;
    private final ConfigManager configManager;

    public HelpCMD(ZonesCMD executor, Configs configs, ConfigManager configManager) {
        super("Help",
                "/zones help",
                configManager.getRawString(configs.getLang(), "commands.zones.help"),
                "attractionstatus.zones.help");

        this.executor = executor;
        this.configs = configs;
        this.configManager = configManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Set<DefaultZonesCMD> commands = executor.getCommands();
        Iterator<DefaultZonesCMD> iterator = commands.iterator();

        Util.centerMsg(sender, "&eZones");
        Util.centerMsg(sender, "&7―――――");
        for (DefaultZonesCMD cmd : commands) {
            Util.msg(sender,"&e" + cmd.getUsage() + "&8 - " + cmd.getDescription());
        }
        Util.centerMsg(sender, "&7―――――");
    }
}
