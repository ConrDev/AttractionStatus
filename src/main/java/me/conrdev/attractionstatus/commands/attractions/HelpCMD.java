package me.conrdev.attractionstatus.commands.attractions;

import me.conrdev.attractionstatus.commands.AttractionsCMD;
import me.conrdev.attractionstatus.commands.abstracts.DefaultAttractionsCMD;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.command.CommandSender;

import java.util.Iterator;
import java.util.Set;

public class HelpCMD extends DefaultAttractionsCMD {

    private final AttractionsCMD executor;
    private final Configs configs;
    private final ConfigManager configManager;

    public HelpCMD(AttractionsCMD executor, Configs configs, ConfigManager configManager) {
        super("Help",
                "/attractions help",
                configManager.getRawString(configs.getLang(), "commands.attractions.help"),
                "attractionstatus.attractions.help",
                false);

        this.executor = executor;
        this.configs = configs;
        this.configManager = configManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Set<DefaultAttractionsCMD> commands = executor.getCommands();
        Iterator<DefaultAttractionsCMD> iterator = commands.iterator();

        Util.centerMsg(sender, "&eAttractions");
        Util.centerMsg(sender, "&7&m―――――");
        for (DefaultAttractionsCMD cmd : commands) {
            Util.msg(sender, "&e" + cmd.getUsage() + "&8 - " + cmd.getDescription());
        }
        Util.centerMsg(sender, "&7&m―――――");
    }
}
