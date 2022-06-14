package me.conrdev.attractionstatus.commands.attractions;

import me.conrdev.attractionstatus.objects.Attraction;
import me.conrdev.attractionstatus.objects.Sign;
import me.conrdev.attractionstatus.commands.AttractionsCMD;
import me.conrdev.attractionstatus.commands.abstracts.DefaultAttractionsCMD;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.managers.AttractionManager;
import me.conrdev.attractionstatus.managers.SignsManager;
import me.conrdev.attractionstatus.utils.EffectUtil;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class StatusCMD extends DefaultAttractionsCMD {

    private final AttractionsCMD executor;
    private final Configs configs;
    private final ConfigManager configManager;
    private final AttractionManager attractionManager;

    public StatusCMD(AttractionsCMD executor, Configs configs, ConfigManager configManager, AttractionManager attractionManager) {
        super("SetStatus",
                "/attractions setstatus <Attraction Name> <Status>",
                configManager.getRawString(configs.getLang(), "commands.attractions.setstatus"),
                "attractionstatus.attractions.setstatus",
                true);

        this.executor = executor;
        this.configs = configs;
        this.configManager = configManager;
        this.attractionManager = attractionManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

//        Player player = (Player) sender;

        List<String> defaultStatuses = new ArrayList<>();

        for (StatusList status : StatusList.values()) {
            defaultStatuses.add(status.name().toLowerCase());
        }

        if (args.length != 3) {
            Map<String, String> map = new HashMap<>();
            map.put("%command%", this.getUsage());

            MsgUtil.WRONGCMD.msg(sender, map, false);
            return;
        }

        String attractionName = args[1];
        String status = args[2];

        if (!defaultStatuses.contains(status)) {
            Map<String, String> map = new HashMap<>();
            map.put("%list%", String.join(", ", defaultStatuses));

            MsgUtil.WRONGSTATUS.msg(sender, map, false);
            return;
        }

        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("%status%", status);

        Attraction attraction = attractionManager.getAttraction(attractionName);

        if (attraction == null) {
            Util.msg(sender, "&cError");
            return;
        }

        attraction.setStatus(status);
        attractionManager.saveAttraction(attraction);

        Set<Sign> signs = SignsManager.getInstance().getSigns(attractionName);

        for (Sign asSign : signs) {
            org.bukkit.block.Sign sign = (org.bukkit.block.Sign) asSign.getLocation().getBlock().getState();
            sign.setLine(2, Util.color(attraction.getStatus()));

            if (attraction.getStatus(false).equalsIgnoreCase(StatusList.OPENED.toString())) {
                EffectUtil.spawnParticleLoop(asSign.getLocation().getBlock(), Particle.VILLAGER_HAPPY);
            } else {
                EffectUtil.removeParticleLoop(asSign.getLocation().getBlock());
            }

            sign.update();
        }

        MsgUtil.STATUS_CHANGED.msg(sender, statusMap, false);
    }
}