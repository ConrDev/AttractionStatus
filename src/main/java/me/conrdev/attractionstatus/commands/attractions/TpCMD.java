package me.conrdev.attractionstatus.commands.attractions;

import me.conrdev.attractionstatus.objects.Attraction;
import me.conrdev.attractionstatus.commands.AttractionsCMD;
import me.conrdev.attractionstatus.commands.abstracts.DefaultAttractionsCMD;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.managers.AttractionManager;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.TpUtil;
import org.bukkit.command.CommandSender;

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
                "attractionstatus.attractions.tp",
                false);

        this.executor = executor;
        this.configs = configs;
        this.configManager = configManager;
        this.attractionManager = attractionManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {
//        String attractionName = String.join(" ", (CharSequence[]) ArrayUtils.remove(args, 0));
        Map<String, String> cmdMap = new HashMap<>();
        cmdMap.put("%command%", this.getUsage());

        if (args.length != 2) {
            MsgUtil.WRONGCMD.msg(sender, cmdMap, false);
            return;
        }

        Attraction attraction = attractionManager.getAttraction(args[1]);

        if (attraction == null) {
            Map<String, String> map = new HashMap<>();
            map.put("%attraction%", args[1]);

            MsgUtil.DONT_EXISTS.msg(sender, map, false);
            return;
        }

        if (attraction.telepertPlayer(sender)) {
            Map<String, String> map = new HashMap<>();
            map.put("%object%", attraction.getName());

            MsgUtil.SUCCES_TP.msg(sender, map, false);
        }
    }
}
