package me.conrdev.attractionstatus.commands;

import com.google.common.collect.ImmutableList;
import me.conrdev.attractionstatus.Core;
import me.conrdev.attractionstatus.commands.as.DefaultCMD;
import me.conrdev.attractionstatus.commands.as.HelpCMD;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.utils.MsgUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AttractionStatusCMD implements CommandExecutor {

    private static AttractionStatusCMD instance = null;

    private final ConfigManager configManager = Core.getConfigManager();
    private final Configs configs = Core.getConfigs();

    private final Set<DefaultCMD> commands = new LinkedHashSet<>();
    private Core plugin = null;

    public AttractionStatusCMD() {
        commands.add(new HelpCMD(this, configs, configManager ));
    }

    public static AttractionStatusCMD getInstance() {
        if (instance == null) instance = new AttractionStatusCMD();

        return instance;
    }

    public void setPlugin(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        Player player = (Player) sender;

        if (args.length == 0) {
            DefaultCMD cmd = commands.iterator().next();
            cmd.run(sender, args);
            return true;
        }

        if (!(sender instanceof Player)) {
            assert false;
            MsgUtil.NOTPLR.msg(sender);
        }

        for (DefaultCMD cmd : commands) {
            if (!cmd.getName().equalsIgnoreCase(args[0])) return false;
            if (cmd.getPermission() != null && !sender.hasPermission(cmd.getPermission())) {
                MsgUtil.NOPERM.msg(sender);
                return false;
            }

            cmd.run(sender, args);
        }


        return false;
    }

    public Set<DefaultCMD> getCommands() { return commands; }
}
