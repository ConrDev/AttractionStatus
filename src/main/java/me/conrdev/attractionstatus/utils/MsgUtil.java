package me.conrdev.attractionstatus.utils;

import me.conrdev.attractionstatus.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public enum MsgUtil {
    BANNER(" ",
            "&e   _____ _____    ",
            "&e  |  _  |   __|   &6%pluginName% &ev%pluginVersion%",
            "&e  |     |__   |   ",
            "&e  |__|__|_____|   &8Plugin Status: %status%",
            " ",
            " "
    ),

    NOPERM(Core.getConfigManager().getString(Core.getConfigs().getLang(), "errors.no-permissions")),
    NOTPLR(Core.getConfigManager().getString(Core.getConfigs().getLang(), "errors.player-only")),
    CMDNF(Core.getConfigManager().getString(Core.getConfigs().getLang(), "errors.command-not-found")),
    DEXISTS(Core.getConfigManager().getString(Core.getConfigs().getLang(), "errors.dont-exists")),
    EXISTS(Core.getConfigManager().getString(Core.getConfigs().getLang(), "errors.exists")),
    WRONGCMD(Core.getConfigManager().getString(Core.getConfigs().getLang(), "errors.wrong-command")),
    NOATTRACTIONS(Core.getConfigManager().getString(Core.getConfigs().getLang(), "errors.no-attractions")),
    NOZONES(Core.getConfigManager().getString(Core.getConfigs().getLang(), "errors.no-zones")),

    OPENED(Core.getConfigManager().getString(Core.getConfigs().getLang(), "status.opened")),
    MAINTENANCE(Core.getConfigManager().getString(Core.getConfigs().getLang(), "status.maintenance")),
    CLOSED(Core.getConfigManager().getString(Core.getConfigs().getLang(), "status.closed")),

    SUCCES_TP(Core.getConfigManager().getString(Core.getConfigs().getLang(), "succes-tp")),
    SUCCES_REMOVED(Core.getConfigManager().getString(Core.getConfigs().getLang(), "succes-removed")),
    SUCCES_CREATED(Core.getConfigManager().getString(Core.getConfigs().getLang(), "succes-created")),
    SUCCES_LOADED(Core.getConfigManager().getString(Core.getConfigs().getLang(), "succes-loaded")),
    ZONE_CHANGED(Core.getConfigManager().getString(Core.getConfigs().getLang(), "zone-changed")),
    STATUS_CHANGED(Core.getConfigManager().getString(Core.getConfigs().getLang(), "status-changed")),

    ATTRACTIONS_LOADING(Core.getConfigManager().getString(Core.getConfigs().getLang(), "messages.attraction.loading-all")),

    ATTRACTION_CREATING_DATA(Core.getConfigManager().getString(Core.getConfigs().getLang(), "messages.attraction.data.creating")),
    ATTRACTION_LOADING_DATA(Core.getConfigManager().getString(Core.getConfigs().getLang(), "messages.attraction.data.loading")),
    ATTRACTION_SAVING_DATA(Core.getConfigManager().getString(Core.getConfigs().getLang(), "messages.attraction.data.saving")),

    ZONES_LOADING(Core.getConfigManager().getString(Core.getConfigs().getLang(), "messages.zone.loading-all")),

    ZONE_CREATING_DATA(Core.getConfigManager().getString(Core.getConfigs().getLang(), "messages.zone.data.creating")),
    ZONE_LOADING_DATA(Core.getConfigManager().getString(Core.getConfigs().getLang(), "messages.zone.data.loading")),
    ZONE_SAVING_DATA(Core.getConfigManager().getString(Core.getConfigs().getLang(), "messages.zone.data.saving")),
    ;

//    private static MsgUtil msgU;

    private String[] messages;

    MsgUtil(final String... messages) {
        this.messages = messages;
    }

    private boolean multiLinedMessage() {
        return this.messages.length > 1;
    }

    public String[] getMessages() {
        return this.messages;
    }

    public void setMessages(final String[] messages) {
        this.messages = messages;
    }

    public void setMessages(final String messages) {
        this.messages[0] = messages;
    }


    public void msg(final CommandSender target) {
        msg(target, null, false);
    }

    public void msg(CommandSender target, final Map<String, String> map, final boolean ignoreCase) {
        if (this.multiLinedMessage()) {
            Util.msg(target, this.getMessages(), map, ignoreCase);
        } else {
            Util.msg(target, this.getMessages()[0], map, ignoreCase);
        }

    }

}
