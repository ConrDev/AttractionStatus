package me.conrdev.attractionstatus.commands.as;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class DefaultCMD {

    private final String name;
    private final String usage;
    private final String description;
    private final String permission;

    public DefaultCMD(String name, String usage, String description, String permission) {
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.permission = permission;
    }

    abstract public void run(CommandSender sender, String[] args);

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }

    public String getPermission() {
        return permission;
    }
}
