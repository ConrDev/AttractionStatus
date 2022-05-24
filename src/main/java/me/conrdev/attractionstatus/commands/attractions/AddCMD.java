package me.conrdev.attractionstatus.commands.attractions;

import me.conrdev.attractionstatus.commands.AttractionsCMD;
import me.conrdev.attractionstatus.commands.abstracts.DefaultAttractionsCMD;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AddCMD extends DefaultAttractionsCMD {

    private final AttractionsCMD executor;

    public AddCMD(AttractionsCMD executor, Configs configs, ConfigManager configManager) {
        super("Add",
                "/attractions add <Attraction Name>",
                configManager.getRawString(configs.getLang(), "commands.attractions.add"),
                "attractionstatus.attractions.add");
        this.executor = executor;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Util.centerMsg(sender, "&eAttractions");

        if (args[1] == null) {
            Map<String, String> map = new HashMap<>();
            map.put("%command%", this.getUsage());

            MsgUtil.WRONGCMD.msg(sender, map, false);
        }

    }
}
