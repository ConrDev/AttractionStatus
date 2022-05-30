package me.conrdev.attractionstatus.commands.attractions;

import me.conrdev.attractionstatus.Objects.Attraction;
import me.conrdev.attractionstatus.commands.AttractionsCMD;
import me.conrdev.attractionstatus.commands.abstracts.DefaultAttractionsCMD;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.managers.AttractionManager;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RemoveCMD extends DefaultAttractionsCMD {

    private final AttractionsCMD executor;
    private final Configs configs;
    private final ConfigManager configManager;
    private final AttractionManager attractionManager;

    public RemoveCMD(AttractionsCMD executor, Configs configs, ConfigManager configManager, AttractionManager attractionManager) {
        super("Remove",
                "/attractions remove <Attraction Name>",
                configManager.getRawString(configs.getLang(), "commands.attractions.remove"),
                "attractionstatus.attractions.remove");
        this.executor = executor;
        this.configs = configs;
        this.configManager = configManager;
        this.attractionManager = attractionManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        if (args.length != 2) {
            Map<String, String> map = new HashMap<>();
            map.put("%command%", this.getUsage());

            MsgUtil.WRONGCMD.msg(sender, map, false);
            return;
        }

        String attractionName = args[1];

        Attraction attraction = attractionManager.getAttraction(attractionName);

        Map<String, String> map = new HashMap<>();
        map.put("%object%", attractionName);

        if (attraction == null) {
            MsgUtil.DONT_EXISTS.msg(sender, map, false);
            return;
        }

        attractionManager.removeAttraction(attraction);

        MsgUtil.SUCCES_REMOVED.msg(sender, map, false);
    }
}
