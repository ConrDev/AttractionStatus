package me.conrdev.attractionstatus.commands.attractionstatus;

import me.conrdev.attractionstatus.commands.AttractionStatusCMD;
import me.conrdev.attractionstatus.commands.abstracts.DefaultAttractionStatusCMD;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.command.CommandSender;

import java.util.Iterator;
import java.util.Set;

public class MainCMD extends DefaultAttractionStatusCMD {

    private final AttractionStatusCMD executor;

    private final ConfigManager configManager;
    private final Configs configs;

    public MainCMD(AttractionStatusCMD executor, Configs configs, ConfigManager configManager) {
        super("Help",
                "/attractionstatus",
                configManager.getRawString(configs.getLang(), "commands.attractionstatus.help"),
                "attractionstatus.help");

        this.executor = executor;
        this.configManager = configManager;
        this.configs = configs;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Set<DefaultAttractionStatusCMD> commands = executor.getCommands();
        Iterator<DefaultAttractionStatusCMD> iterator = commands.iterator();

        Util.centerMsg(sender, "&eAttraction Status");
        Util.centerMsg(sender, "&7&m―――――");
        Util.msg(sender, "&e/attractions help &8- " + configManager.getRawString(configs.getLang(), "commands.attractions.help"));
        Util.msg(sender, "&e/zones help &8- " + configManager.getRawString(configs.getLang(), "commands.zones.help"));
        Util.centerMsg(sender, "&7&m―――――");
        for (DefaultAttractionStatusCMD cmd : commands) {
            Util.msg(sender, "&e" + cmd.getUsage() + "&8 - " + cmd.getDescription());
        }
        Util.centerMsg(sender, "&7&m―――――");
    }
}
