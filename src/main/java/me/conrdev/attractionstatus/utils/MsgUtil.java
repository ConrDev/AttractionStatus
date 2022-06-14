package me.conrdev.attractionstatus.utils;

import me.conrdev.attractionstatus.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
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

    NOPERM(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "errors.no-permissions")),
    NOTPLR(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "errors.player-only")),
    CMDNF(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "errors.command-not-found")),
    DONT_EXISTS(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "errors.dont-exists")),
    EXISTS(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "errors.exists")),
    WRONGCMD(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "errors.wrong-command")),
    WRONGSTATUS(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "errors.wrong-status")),
//    NOATTRACTIONS(Core.getConfigManager().getRawStrings(Core.getConfigs().getLang(), "errors.no-attractions")),
//    NOZONES(Core.getConfigManager().getRawStrings(Core.getConfigs().getLang(), "errors.no-zones")),
    NOTOWNER(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "errors.not-owner")),
    NOATTRACTIONS(Arrays.toString(Core.getConfigManager().getRawStrings(Core.getConfigs().getLang(), "errors.no-attractions")).replace("[", "").replace("]", "")),
    NOZONES(Arrays.toString(Core.getConfigManager().getRawStrings(Core.getConfigs().getLang(), "errors.no-zones")).replace("[", "").replace("]", "")),

    OPENED(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "status.opened")),
    MAINTENANCE(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "status.maintenance")),
    CLOSED(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "status.closed")),

    SUCCES_TP(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.succes-tp")),
    SUCCES_REMOVED(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.succes-removed")),
    SUCCES_CREATED(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.succes-created")),
    SUCCES_LOADED(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.succes-loaded")),
    ZONE_CHANGED(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.zone-changed")),
    STATUS_CHANGED(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.status-changed")),

    ATTRACTIONS_LOADING(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.attraction.loading-all")),

    ATTRACTION_CREATING_DATA(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.attraction.data.creating")),
    ATTRACTION_LOADING_DATA(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.attraction.data.loading")),
    ATTRACTION_SAVING_DATA(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.attraction.data.saving")),

    ATTRACTION_NOT_FOUND(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.attraction.not-found")),
    ATTRACTION_IN_ZONE(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.attraction.in-zone")),
    ATTRACTION_HOVER(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.attraction.hover")),
    ATTRACTION_NO_ZONE(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.attraction.no-zone")),

    SIGNS_LOADING(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.sign.loading-all")),

    ATTRACTION_SIGN_CREATED(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.sign.created")),
    ATTRACTION_SIGN_REMOVED(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.sign.removed")),

    ZONES_LOADING(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.zone.loading-all")),

    ZONE_CREATING_DATA(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.zone.data.creating")),
    ZONE_LOADING_DATA(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.zone.data.loading")),
    ZONE_SAVING_DATA(Core.getConfigManager().getRawString(Core.getConfigs().getLang(), "messages.zone.data.saving")),
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

    public String getMessage() {
        return getMessage("");
    }

    public String getMessage(String splitter) {
        if (getMessages().length > 1) {
            return String.join(splitter, getMessages());
        }
        return String.join("", getMessages());
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
//            Util.msg(Bukkit.getConsoleSender(), this.getMessages());
            Util.msg(target, this.getMessages(), map, ignoreCase);
        } else {
            Util.msg(target, this.getMessages()[0], map, ignoreCase);
        }

    }

}
