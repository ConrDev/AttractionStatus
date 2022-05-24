package me.conrdev.attractionstatus.commands.abstracts;

import org.bukkit.command.CommandSender;

public abstract class DefaultZonesCMD {

    private final String name;
    private final String usage;
    private final String description;
    private final String permission;

    public DefaultZonesCMD(String name, String usage, String description, String permission) {
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
