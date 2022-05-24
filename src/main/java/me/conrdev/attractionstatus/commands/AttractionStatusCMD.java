package me.conrdev.attractionstatus.commands;

import me.conrdev.attractionstatus.Core;
import me.conrdev.attractionstatus.commands.abstracts.DefaultAttractionStatusCMD;
import me.conrdev.attractionstatus.commands.attractionstatus.MainCMD;
import me.conrdev.attractionstatus.commands.attractionstatus.ListCMD;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class AttractionStatusCMD implements CommandExecutor {

//    private static AttractionStatusCMD instance = null;

    private final Set<DefaultAttractionStatusCMD> commands = new LinkedHashSet<>();

//    private Core plugin = null;

    public AttractionStatusCMD(Core plugin) {
        ConfigManager configManager = plugin.getConfigManager();
        Configs configs = plugin.getConfigs();

        commands.add(new MainCMD(this, configs, configManager));
        commands.add(new ListCMD(this, configs, configManager, plugin.getAttractionManager()));
    }

//    public static AttractionStatusCMD getInstance() {
//        if (instance == null) instance = new AttractionStatusCMD();
//
//        return instance;
//    }
//
//    public void setPlugin(Core plugin) {
//        this.plugin = plugin;
//    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        Player player = (Player) sender;

        if (args.length == 0) {
            DefaultAttractionStatusCMD cmd = commands.iterator().next();
            cmd.run(sender, args);
            return true;
        }

        if (!(sender instanceof Player)) {
            MsgUtil.NOTPLR.msg(sender);
            return false;
        }

        for (DefaultAttractionStatusCMD cmd : commands) {
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

    public Set<DefaultAttractionStatusCMD> getCommands() { return commands; }
}