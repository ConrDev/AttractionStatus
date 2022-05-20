package me.conrdev.attractionstatus.commands.as;

import me.conrdev.attractionstatus.commands.AttractionStatusCMD;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Set;

public class HelpCMD extends DefaultCMD {

    private final AttractionStatusCMD executor;

    public HelpCMD(AttractionStatusCMD executor, Configs configs, ConfigManager configManager) {
        super("Help",
                "/attractionstatus",
                configManager.getRawString(configs.getLang(), "commands.as-help"),
                "attractionstatus.help");
        this.executor = executor;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Set<DefaultCMD> commands = executor.getCommands();
        Iterator<DefaultCMD> iterator = commands.iterator();

        Util.centerMsg(sender, "&eAttractionStatus");

        for (DefaultCMD cmd : commands) {
            Util.msg(sender,"&e" + getUsage() + "&8 - " + getDescription());
        }

    }
}
