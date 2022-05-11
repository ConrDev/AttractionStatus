package me.conrdev.attractionstatus.utils;

import com.google.common.collect.Lists;
import me.conrdev.attractionstatus.Core;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Util {

    public static String color(final String msg) {
        Validate.notNull(msg, "The string can't be null!");
        
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String[] color(final String[] array) {
        Validate.notNull(array, "The string array can't be null!");
        Validate.noNullElements(array, "The string array can't have null elements!");

        final String[] arr = Arrays.copyOf(array, array.length);

        for (int i = 0; i < arr.length; i++) {
            arr[i] = color(array[i]);
        }

        return arr;
    }

    public static List<String> color(final List<String> collection) {
        Validate.notNull(collection, "The string collection can't be null!");
        Validate.noNullElements(collection, "The string collection can't have null elements!");

        final List<String> coll = Lists.newArrayList(collection);

        coll.replaceAll(Util::color);

        return coll;
    }



    public static String placeHolder(String string, final Map<String, String> map, final boolean ignoreCase) {
        Validate.notNull(string, "The string can't be null!");

        if (map == null) return string;

        for (final Map.Entry<String, String> entry : map.entrySet()) {
            string = ignoreCase ? replaceIgnoreCase(string, entry.getKey(), entry.getValue())
                                : string.replace(entry.getKey(), entry.getValue());
        }

        return string;
    }

    public static String[] placeHolder(final String[] array, final Map<String, String> map, final boolean ignoreCase) {
        Validate.notNull(array, "The string array can't be null!");
        Validate.noNullElements(array, "The string array can't have null elements!");

        final String[] arr = Arrays.copyOf(array, array.length);

        if (map == null) return arr;

        for (int i = 0; i < arr.length; i++) {
            arr[i] = placeHolder(arr[i], map, ignoreCase);
        }

        return arr;
    }

    public static List<String> placeHolder(final List<String> collection, final Map<String, String> map,
                                           final boolean ignoreCase) {
        Validate.notNull(collection, "The string collection can't be null!");
        Validate.noNullElements(collection, "The string collection can't have null elements!");

        if (map == null) return collection;

        return collection.stream().map(string -> placeHolder(string, map, ignoreCase)).collect(Collectors.toList());
    }

    private static String replaceIgnoreCase(final String text, String search, final String replacement) {
        if (text == null || text.length() == 0) return text;
        if (search == null || search.length() == 0) return text;
        if (replacement == null) return text;

        int max = -1;

        final String searchText = text.toLowerCase();
        search = search.toLowerCase();

        int start = 0;
        int end = searchText.indexOf(search, start);

        if (end == -1) return text;

        final int rLength = search.length();
        int increase = replacement.length() - rLength;

        increase = (Math.max(increase, 0)) * 16;
//        increase = increase < 0 ? 0 : increase;
//        increase *= 16;

        final StringBuilder sBuilder = new StringBuilder(text.length() + increase);

        while (end != -1) {
            sBuilder.append(text, start, end)
                    .append(replacement);
            start = end + rLength;

            if (--max == 0) break;

            end = searchText.indexOf(search, start);
        }

        return sBuilder.append(text, start, text.length()).toString();
    }



    public static void msg(final CommandSender target, final String message) {
        Validate.notNull(target, "The target can't be null!");

        if (message == null) return;

        target.sendMessage(color(message));
    }

    public static void msg(final CommandSender target, final String... array) {
        Validate.notNull(target, "The target can't be null!");

        if (array == null || array.length == 0) return;

        Validate.noNullElements(array, "The string array can't have null elements!");

        target.sendMessage(color(array));
    }

    public static void msg(final CommandSender target, final List<String> collection) {
        Validate.notNull(target, "The target can't be null!");

        if (collection == null || collection.isEmpty()) return;

        Validate.noNullElements(collection, "The string collection can't have null elements!");

        msg(target, collection.toArray(new String[0]));
    }

    public static void msg(final CommandSender target, final String message, final Map<String, String> map,
                           final boolean ignoreCase) {
        msg(target, placeHolder(message, map, ignoreCase));
    }
    public static void msg(final CommandSender target, final String[] array, final Map<String, String> map,
                           final boolean ignoreCase) {
        msg(target, placeHolder(array, map, ignoreCase));
    }
    public static void msg(final CommandSender target, final List<String> collection, final Map<String, String> map,
                           final boolean ignoreCase) {
        msg(target, placeHolder(collection, map, ignoreCase));
    }
}
