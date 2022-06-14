package me.conrdev.attractionstatus.commands.abstracts;

import org.bukkit.command.CommandSender;

public abstract class DefaultAttractionsCMD {

    private final String name;
    private final String usage;
    private final String description;
    private final String permission;
    public final boolean canUseInConsole;

    public DefaultAttractionsCMD(String name, String usage, String description, String permission, boolean canUseInConsole) {
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.permission = permission;
        this.canUseInConsole = canUseInConsole;
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
