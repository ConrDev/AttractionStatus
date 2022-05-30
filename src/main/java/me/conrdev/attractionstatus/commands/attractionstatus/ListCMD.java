package me.conrdev.attractionstatus.commands.attractionstatus;

import com.google.common.collect.Maps;
import me.conrdev.attractionstatus.Objects.Attraction;
import me.conrdev.attractionstatus.commands.AttractionStatusCMD;
import me.conrdev.attractionstatus.commands.abstracts.DefaultAttractionStatusCMD;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.managers.AttractionManager;
import me.conrdev.attractionstatus.utils.MsgUtil;
import me.conrdev.attractionstatus.utils.Util;
//import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class ListCMD extends DefaultAttractionStatusCMD {

    private final AttractionStatusCMD executor;

    private final ConfigManager configManager;
    private final Configs configs;
    private final AttractionManager attractionManager;

    public ListCMD(AttractionStatusCMD executor, Configs configs, ConfigManager configManager, AttractionManager attractionManager) {
        super("List",
                "/attractionstatus list",
                configManager.getRawString(configs.getLang(), "commands.attractionstatus.list"),
                "attractionstatus.list");
        this.executor = executor;
        this.configManager = configManager;
        this.configs = configs;
        this.attractionManager = attractionManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Map<Integer, Attraction> attractions = attractionManager.getAttractions();

        Util.centerMsg(sender, "&eAttraction List");
        Util.centerMsg(sender, "&7&m―――――");

        if (attractions.isEmpty()) {
            MsgUtil.NOATTRACTIONS.msg(sender);
            Util.centerMsg(sender, "&7&m―――――");
            return;
        }

        for (Map.Entry<Integer, Attraction> attraction : attractions.entrySet()) {
            String i = "&6" + attraction.getKey() + ". ";
            String AttStatus = attraction.getValue().getStatus();
            String AttName = attraction.getValue().getName();
            String AttZone = attraction.getValue().getZone();

            String Name = ChatColor.getLastColors(
                    AttStatus.replace('&', ChatColor.COLOR_CHAR))
                    + AttName.replace('_', ' ');

            final Map<String, String> zone = Maps.newHashMap();
            zone.put("%zone%", AttZone);

            String Zone = Util.placeHolder(
                    MsgUtil.ATTRACTION_IN_ZONE.getMessage(),
                    zone,
                    false
            );

            final Map<String, String> hover = Maps.newHashMap();
            hover.put("%attraction%", AttName.replace('_', ' '));

            String hoverString = Util.placeHolder(
                    MsgUtil.ATTRACTION_HOVER.getMessage(),
                    hover,
                    false
            );

            ComponentBuilder message = new ComponentBuilder(Util.color(i))
                    .append(new ComponentBuilder(Util.color(Name))
                            .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/attractions tp " + AttName))
                            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverString).create()))
                            .create())
                    .append(new ComponentBuilder(Util.color("&8 - " + Zone.replace('_', ' '))).create());

            Util.msg(sender, message);
        }

        Util.centerMsg(sender, "&7&m―――――");
    }
}
