package me.conrdev.attractionstatus.commands.attractions;

import me.conrdev.attractionstatus.Objects.Attraction;
import me.conrdev.attractionstatus.commands.AttractionsCMD;
import me.conrdev.attractionstatus.commands.abstracts.DefaultAttractionsCMD;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.managers.AttractionManager;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.TpUtil;
import me.conrdev.attractionstatus.utils.Util;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TpCMD extends DefaultAttractionsCMD {

    private final AttractionsCMD executor;
    private final Configs configs;
    private final ConfigManager configManager;
    private final AttractionManager attractionManager;

    public TpCMD(AttractionsCMD executor, Configs configs, ConfigManager configManager, AttractionManager attractionManager) {
        super("Tp",
                "/attractions tp <Attraction Name>",
                configManager.getRawString(configs.getLang(), "commands.attractions.tp"),
                "attractionstatus.attractions.tp");

        this.executor = executor;
        this.configs = configs;
        this.configManager = configManager;
        this.attractionManager = attractionManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {
//        String attractionName = String.join(" ", (CharSequence[]) ArrayUtils.remove(args, 0));
        Attraction attraction = AttractionManager.getAttraction(args[1]);

        if (attraction == null || args[1] == null) {
            Map<String, String> map = new HashMap<>();
            map.put("%command%", this.getUsage());

            MsgUtil.WRONGCMD.msg(sender, map, false);
            return;
        }

        if (TpUtil.tp(sender, attraction.getLocation())) {
            Map<String, String> map = new HashMap<>();
            map.put("%object%", attraction.getName());

            MsgUtil.SUCCES_TP.msg(sender, map, false);
        }
    }
}
