package me.conrdev.attractionstatus.utils;

import com.google.common.collect.Lists;
import me.conrdev.attractionstatus.Core;
import me.conrdev.attractionstatus.Objects.Attraction;
import me.conrdev.attractionstatus.config.ConfigManager;
import me.conrdev.attractionstatus.config.Configs;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.K;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Util {
    private static Util instance = null;
    private Core plugin = null;

    private static final int CENTER_PX = 154;

    public static Util getInstance() {
        if (instance == null) instance = new Util();

        return instance;
    }

    public static boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public void setPlugin(Core plugin) {
        this.plugin = plugin;
    }

    public static String prefix() {
        return color(ConfigManager.getInstance().getRawString(Configs.getInstance().getConfig(), "AttractionStatus.Prefix"));
    }

    public static boolean containsIgnoreCase(String haystack, String needle) {
        if (haystack == null || needle == null) return false;

        return (haystack.toLowerCase().contains(needle.toLowerCase()));
    }

    public static String title(String string) {
        final char[] delimiters = { ' ', '_' };

        return WordUtils.capitalizeFully(string, delimiters);
    }

    // Colors

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

    // Placeholders

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

    // Messages

    public static void msg(final CommandSender target, final String message) {
        Validate.notNull(target, "The target can't be null!");

        if (message == null) return;

        target.sendMessage(color(message));
    }

    public static void msg(final CommandSender target, final BaseComponent[] message) {
        Validate.notNull(target, "The target can't be null!");

        if (message == null) return;

        target.spigot().sendMessage(message);
//        target.sendMessage(color(message));
    }

    public static void msg(final CommandSender target, final ComponentBuilder message) {
        Validate.notNull(target, "The target can't be null!");

        if (message == null) return;

        target.spigot().sendMessage(message.create());
//        target.sendMessage(color(message));
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

    // Player

    public Location parseLocation(UUID playerUUID, String string) {
        Player player = plugin.getServer().getPlayer(playerUUID);

        // String to Location
        String s2l[] = string.split(",");
        Location location = new Location(player.getWorld(),0,0,0);

        location.setX(Double.parseDouble(s2l[0]));
        location.setY(Double.parseDouble(s2l[1]));
        location.setZ(Double.parseDouble(s2l[2]));

        return location;
    }

    public static <V> int getHighestId(Map<Integer, V> map) {
        int highestId = 0;

        for (int id : map.keySet()) {
            if (id > highestId) highestId = id;
        }

        return highestId;
    }

    public static void centerMsg(final CommandSender target, final String message) {
        if (message == null || message.equals("")) return;

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
            } else if (previousCode) {
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            } else {
                final DefaultFontSize dFI = DefaultFontSize.getDefaultFontSize(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        final int halvedMessageSize = messagePxSize / 2;
        final int toCompensate = CENTER_PX - halvedMessageSize;
        final int spaceLength = DefaultFontSize.SPACE.getLength() + 1;
        int compensated = 0;
        final StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }

        msg(target, sb.toString() + message);
    }

    private enum DefaultFontSize {
        A('A', 5),
        a('a', 5),
        B('B', 5),
        b('b', 5),
        C('C', 5),
        c('c', 5),
        D('D', 5),
        d('d', 5),
        E('E', 5),
        e('e', 5),
        F('F', 5),
        f('f', 4),
        G('G', 5),
        g('g', 5),
        H('H', 5),
        h('h', 5),
        I('I', 3),
        i('i', 1),
        J('J', 5),
        j('j', 5),
        K('K', 5),
        k('k', 4),
        L('L', 5),
        l('l', 1),
        M('M', 5),
        m('m', 5),
        N('N', 5),
        n('n', 5),
        O('O', 5),
        o('o', 5),
        P('P', 5),
        p('p', 5),
        Q('Q', 5),
        q('q', 5),
        R('R', 5),
        r('r', 5),
        S('S', 5),
        s('s', 5),
        T('T', 5),
        t('t', 4),
        U('U', 5),
        u('u', 5),
        V('V', 5),
        v('v', 5),
        W('W', 5),
        w('w', 5),
        X('X', 5),
        x('x', 5),
        Y('Y', 5),
        y('y', 5),
        Z('Z', 5),
        z('z', 5),
        NUM_1('1', 5),
        NUM_2('2', 5),
        NUM_3('3', 5),
        NUM_4('4', 5),
        NUM_5('5', 5),
        NUM_6('6', 5),
        NUM_7('7', 5),
        NUM_8('8', 5),
        NUM_9('9', 5),
        NUM_0('0', 5),
        EXCLAMATION_POINT('!', 1),
        AT_SYMBOL('@', 6),
        NUM_SIGN('#', 5),
        DOLLAR_SIGN('$', 5),
        PERCENT('%', 5),
        UP_ARROW('^', 5),
        AMPERSAND('&', 5),
        ASTERISK('*', 5),
        LEFT_PARENTHESIS('(', 4),
        RIGHT_PERENTHESIS(')', 4),
        MINUS('-', 5),
        UNDERSCORE('_', 5),
        PLUS_SIGN('+', 5),
        EQUALS_SIGN('=', 5),
        LEFT_CURL_BRACE('{', 4),
        RIGHT_CURL_BRACE('}', 4),
        LEFT_BRACKET('[', 3),
        RIGHT_BRACKET(']', 3),
        COLON(':', 1),
        SEMI_COLON(';', 1),
        DOUBLE_QUOTE('"', 3),
        SINGLE_QUOTE('\'', 1),
        LEFT_ARROW('<', 4),
        RIGHT_ARROW('>', 4),
        QUESTION_MARK('?', 5),
        SLASH('/', 5),
        BACK_SLASH('\\', 5),
        LINE('|', 1),
        TILDE('~', 5),
        TICK('`', 2),
        PERIOD('.', 1),
        COMMA(',', 1),
        SPACE(' ', 3),
        DEFAULT('a', 4);

        private final char character;
        private final int length;

        DefaultFontSize(char character, int length) {
            this.character = character;
            this.length = length;
        }

        public static DefaultFontSize getDefaultFontSize(char c) {
            for (DefaultFontSize dFI : DefaultFontSize.values()) {
                if (dFI.getCharacter() == c) {
                    return dFI;
                }
            }
            return DefaultFontSize.DEFAULT;
        }

        public char getCharacter() {
            return character;
        }

        public int getLength() {
            return length;
        }

        public int getBoldLength() {
            if (this == DefaultFontSize.SPACE) {
                return getLength();
            }
            return length + 1;
        }
    }
}
