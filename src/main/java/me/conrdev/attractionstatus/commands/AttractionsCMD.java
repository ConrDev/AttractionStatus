package me.conrdev.attractionstatus.commands;

import me.conrdev.attractionstatus.Core;
import me.conrdev.attractionstatus.Objects.Attraction;
import me.conrdev.attractionstatus.Objects.Zone;
import me.conrdev.attractionstatus.commands.abstracts.DefaultAttractionStatusCMD;
import me.conrdev.attractionstatus.commands.abstracts.DefaultAttractionsCMD;
import me.conrdev.attractionstatus.commands.attractions.*;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.managers.AttractionManager;
import me.conrdev.attractionstatus.managers.ZoneManager;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AttractionsCMD implements CommandExecutor, TabCompleter {

//    private static AttractionsCMD instance = null;

    private final Set<DefaultAttractionsCMD> commands = new LinkedHashSet<>();
//    private Core plugin = null;

    private final ConfigManager configManager;
    private final Configs configs;

    private final AttractionManager attractionsManager;
    private final ZoneManager zoneManager;

    public AttractionsCMD(Core plugin) {
        configManager = Core.getConfigManager();
        configs = Core.getConfigs();
        attractionsManager = plugin.getAttractionManager();
        zoneManager = plugin.getZoneManager();

        commands.add(new MainCMD(this, configs, configManager));
        commands.add(new HelpCMD(this, configs, configManager));
        commands.add(new AddCMD(this, configs, configManager, plugin.getAttractionManager()));
        commands.add(new RemoveCMD(this, configs, configManager, plugin.getAttractionManager()));
        commands.add(new TpCMD(this, configs, configManager, plugin.getAttractionManager()));
    }

//    public static AttractionsCMD getInstance() {
//        if (instance == null) instance = new AttractionsCMD();
//
//        return instance;
//    }

//    public void setPlugin(Core plugin) {
//        this.plugin = plugin;
//    }

    // Attractions Commands

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        Player player = (Player) sender;

        if (args.length == 0) {
            DefaultAttractionsCMD cmd = commands.iterator().next();
            cmd.run(sender, args);
            return false;
        }

        if (!(sender instanceof Player)) {
            MsgUtil.NOTPLR.msg(sender);
            return false;
        }

        for (DefaultAttractionsCMD cmd : commands) {
            if (!cmd.getName().equalsIgnoreCase(args[0])) continue;
            if (!cmd.getPermission().isEmpty() && !sender.hasPermission(cmd.getPermission())) {
                MsgUtil.NOPERM.msg(sender);
                return false;
            }

            cmd.run(sender, args);
            return true;
        }


        return false;
    }

    // Attractions Tab Completions

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> attractionNames = new ArrayList<>();
        List<String> zoneNames = new ArrayList<>();
        List<String> defaultStatuses = new ArrayList<>();


        for(Attraction attraction : attractionsManager.getAttractions().values()) {
            attractionNames.add(attraction.getName());
        }

        for(Zone zone : zoneManager.getZones().values()) {
            zoneNames.add(zone.getName());
        }

        defaultStatuses.add(MsgUtil.OPENED.getMessage());
        defaultStatuses.add(MsgUtil.MAINTENANCE.getMessage());
        defaultStatuses.add(MsgUtil.CLOSED.getMessage());

        switch (args.length) {
            case 1:
                switch (args[0].toLowerCase()) {
                    case "add":
                        return null;
                }
            case 2:
                switch (args[0].toLowerCase()) {
                    case "tp":
                    case "remove":
                    case "setzone":
                    case "setstatus":
                        return attractionNames;
                }
            case 3:
                switch (args[0].toLowerCase()) {
                    case "setzone":
                        return zoneNames;
                    case "setstatus":
                        return defaultStatuses;
                }
        }
//        if (args[0] == "add") return null;
//
//        if (args[0] == "tp") {
//            if (args.length == 1) return attractionNames;
//            return null;
//        }
//
//        if (args[0] == "setzone") {
//            if (args.length == 1) {
//                return attractionNames;
//            } else if (args.length == 2) {
//                return zoneNames;
//            }
//            return null;
//        }
//
//        if (args[0] == "setstatus") {
//            if (args.length == 1) {
//                return attractionNames;
//            } else if (args.length == 2) {
//                return defaultStatuses;
//            }
//            return null;
//        }
//
        return null;
    }
//
    public Set<DefaultAttractionsCMD> getCommands() { return commands; }


}
