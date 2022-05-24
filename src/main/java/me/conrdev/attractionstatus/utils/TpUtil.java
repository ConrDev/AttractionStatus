package me.conrdev.attractionstatus.utils;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpUtil {

    private static TpUtil tpU;

    public static Location parseLoc(Player player, String str){
        String str2loc[]=str.split(",");

        Location loc = new Location(player.getWorld(),0,0,0);
        loc.setX(Double.parseDouble(str2loc[0]));
        loc.setY(Double.parseDouble(str2loc[1]));
        loc.setZ(Double.parseDouble(str2loc[2]));

        return loc;
    }

    public static boolean tp(CommandSender sender, String location) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        return tp(player, parseLoc(player, location));
    }

    public static boolean tp(CommandSender sender, Location location) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        return tp(player, location);
    }


    public static boolean tp(Player player, Location location) {
        player.teleport(location);
        return true;
    }

}
