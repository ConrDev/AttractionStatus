package me.conrdev.attractionstatus.utils;

import me.conrdev.attractionstatus.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public enum MsgUtil {
    NOPERM(""),
    NOTPLR(""),
    CMDNF(""),
    DEXISTS(""),
    EXISTS(""),
    WRONGCMD(""),
    BANNER(" ",
            "&e   _____ _____    ",
            "&e  |  _  |   __|   &6%pluginName% &ev%pluginVersion%",
            "&e  |     |__   |   ",
            "&e  |__|__|_____|   &8Plugin Status: %status%",
            " ",
            " "
    ),
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
