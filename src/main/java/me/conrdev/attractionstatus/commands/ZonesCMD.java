package me.conrdev.attractionstatus.commands;

import me.conrdev.attractionstatus.Core;
import me.conrdev.attractionstatus.commands.abstracts.DefaultAttractionStatusCMD;
import me.conrdev.attractionstatus.commands.abstracts.DefaultZonesCMD;
import me.conrdev.attractionstatus.commands.zones.HelpCMD;
import me.conrdev.attractionstatus.commands.zones.MainCMD;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.utils.MsgUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashSet;
import java.util.Set;

public class ZonesCMD implements CommandExecutor {

//    private static ZonesCMD instance = null;

    private final Set<DefaultZonesCMD> commands = new LinkedHashSet<>();
//    private Core plugin = null;

    public ZonesCMD(Core plugin) {
        Configs configs = plugin.getConfigs();
        ConfigManager configManager = plugin.getConfigManager();

        commands.add(new MainCMD(this, configs, configManager));
        commands.add(new HelpCMD(this, configs, configManager));
    }

//    public static ZonesCMD getInstance() {
//        if (instance == null) instance = new ZonesCMD();
//
//        return instance;
//    }
//
//    public void setPlugin(Core plugin) {
//        this.plugin = plugin;
//    }

    // Zones Commands

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        Player player = (Player) sender;

        if (args.length == 0) {
            DefaultZonesCMD cmd = commands.iterator().next();
            cmd.run(sender, args);
            return false;
        }

        if (!(sender instanceof Player)) {
            MsgUtil.NOTPLR.msg(sender);
            return false;
        }

        for (DefaultZonesCMD cmd : commands) {
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

    // Zones Tab Completions

//    @Override
//    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
//        return null;
//    }

    public Set<DefaultZonesCMD> getCommands() { return commands; }

}
