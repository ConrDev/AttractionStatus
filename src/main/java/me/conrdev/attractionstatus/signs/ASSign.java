package me.conrdev.attractionstatus.signs;

import me.conrdev.attractionstatus.Objects.Attraction;
import me.conrdev.attractionstatus.config.Configs;
import me.conrdev.attractionstatus.managers.AttractionManager;
import me.conrdev.attractionstatus.utils.TpUtil;
import me.conrdev.attractionstatus.utils.Util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ASSign {

    public static boolean toAttraction(final Player player, Location location) {
        return TpUtil.tp(player, location);
    }
    public static boolean toAttraction(final Player player, String location) {
        return TpUtil.tp(player, location);
    }

    public static boolean toAttraction(final Player player, String x, String y, String z) {
        return TpUtil.tp(player, x, y, z);
    }

    public static boolean isSign(final String s) {
        return s.equalsIgnoreCase(ChatColor.stripColor(Util.prefix()));
    }

    public static boolean isAttraction(String s) {
        s = ChatColor.stripColor(s);

        return AttractionManager.getInstance().getAttraction(s) != null;
    }

    public static Attraction getAttraction(String s) {
        s = ChatColor.stripColor(s);

        return AttractionManager.getInstance().getAttraction(s);
    }

    public static String setDefaults(final Attraction attraction, final int i) {
        switch (i) {
            case 0: return Util.color("&f&l" + attraction.getName());
            case 1: return "-";
            case 2: return Util.color(attraction.getStatus());
            default: return "";
        }
    }
}