package me.conrdev.attractionstatus.commands.attractions;

import me.conrdev.attractionstatus.objects.Attraction;
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

public class AddCMD extends DefaultAttractionsCMD {

    private final AttractionsCMD executor;
    private final Configs configs;
    private final ConfigManager configManager;
    private final AttractionManager attractionManager;

    public AddCMD(AttractionsCMD executor, Configs configs, ConfigManager configManager, AttractionManager attractionManager) {
        super("Add",
                "/attractions add <Attraction Name>",
                configManager.getRawString(configs.getLang(), "commands.attractions.add"),
                "attractionstatus.attractions.add",
                false);
        this.executor = executor;
        this.configs = configs;
        this.configManager = configManager;
        this.attractionManager = attractionManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length != 2) {
            Map<String, String> map = new HashMap<>();
            map.put("%command%", this.getUsage());

            MsgUtil.WRONGCMD.msg(sender, map, false);
            return;
        }

        String attractionName = args[1];

        Map<String, String> map = new HashMap<>();
        map.put("%object%", attractionName);
        map.put("%attraction%", attractionName);

        if (attractionManager.getAttraction(attractionName) != null) {
            MsgUtil.EXISTS.msg(sender, map, false);
            return;
        }

        MsgUtil.ATTRACTION_CREATING_DATA.msg(sender, map, false);
        Attraction attraction = attractionManager.createAttraction(
                attractionName,
                MsgUtil.CLOSED.name(),
                player.getLocation().clone(),
                MsgUtil.ATTRACTION_NO_ZONE.getMessage(),
                player.getUniqueId()
        );

        if (attraction == null) {
            Util.msg(sender, "&cError");
            return;
        }

        MsgUtil.SUCCES_CREATED.msg(sender, map, false);
    }
}
