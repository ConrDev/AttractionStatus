package me.conrdev.attractionstatus.commands.attractions;

import me.conrdev.attractionstatus.commands.AttractionsCMD;
import me.conrdev.attractionstatus.commands.abstracts.DefaultAttractionsCMD;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.menus.AttractionsMenu;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Set;

public class MainCMD extends DefaultAttractionsCMD {

    private final AttractionsCMD executor;
    private final Configs configs;
    private final ConfigManager configManager;

    public MainCMD(AttractionsCMD executor, Configs configs, ConfigManager configManager) {
        super("Menu",
                "/attractions",
                configManager.getRawString(configs.getLang(), "commands.attractions.menu"),
                "attractionstatus.attractions.menu",
                false);

        this.executor = executor;
        this.configs = configs;
        this.configManager = configManager;
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        new AttractionsMenu(player);
    }
}
