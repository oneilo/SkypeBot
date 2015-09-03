package skypebot.util;

import lombok.Getter;

/**
 * Created by Kyle on 8/26/2015.
 */
public enum BukkitChatFormat {

    BLACK("#000000", '0'),
    DARK_BLUE("#0000AA", '1'),
    DARK_GREEN("#00AA00", '2'),
    TEAL("#00AAAA", '3'),
    DARK_RED("#AA0000", '4'),
    PURPLE("#AA00AA", '5'),
    GOLD("#FFAA00", '6'),
    GREY("#AAAAAA", '7'),
    DARK_GREY("#555555", '8'),
    BLUE("#5555FF", '9'),
    BRIGHT_GREEN("#55FF55", 'a'),
    CYAN("#55FFFF", 'b'),
    RED("#FF5555", 'c'),
    PINK("#FF55FF", 'd'),
    YELLOW("#FFFF55", 'e'),
    WHITE("#FFFFFF", 'f'),
    BOLD("b", 'l'),
    UNDERLINE("u", 'n'),
    STRIKETHROUGH("s", 'm'),
    ITALIC("i", 'o');

    private static final char COLOR_CHAR = '\u00A7';
    private static final String ACCEPTABLE_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRr";

    @Getter
    private String hex;
    @Getter
    private String tag;
    @Getter
    private final char bukkit;

    BukkitChatFormat(String s, char bukkit) {
        if (s.startsWith("#")) {
            hex = s;
        } else {
            tag = s;
        }
        this.bukkit = bukkit;
    }

    public String format(String message) {
        if (hex != null) {
            return "<font color=\"" + hex + "\">" + message + "</font>";
        } else {
            return "<" + tag + ">" + message + "</" + tag + ">";
        }
    }

    public static String getSkypeString(String string) {
        String[] split = string.split("(" + COLOR_CHAR + "r)");
        StringBuilder full = new StringBuilder();
        for (String segment : split) {
            full.append(getSkypeStringInternalV2(segment));
        }
        return full.toString();
    }

    private static String getSkypeStringInternalV2(String bukkitString) {
        StringBuilder ret = new StringBuilder(bukkitString);

        int i = 0;
        while (i < ret.length() - 1) {
            if (ret.charAt(i) == COLOR_CHAR && ACCEPTABLE_CODES.indexOf(ret.charAt(i + 1)) > -1) {

                BukkitChatFormat format = getByBukkitChar(ret.charAt(i + 1));

                // get from/to
                int from = i + 2;
                int to = ret.length();
                String sub = ret.substring(from, to);
                String mat = format.format(getSkypeStringInternalV2(sub));
                ret.replace(i, to, mat);
            }
            i++;
        }
        return ret.toString();
    }

    private static boolean isColorCode(char c1, char c2) {
        return c1 == COLOR_CHAR && (Character.isDigit(c2) || ((int) c2) >= 65 && ((int) c2) <= 102);
    }

    public static BukkitChatFormat getByBukkitChar(char c) {
        c = Character.toLowerCase(c);
        for (BukkitChatFormat chatColor : values()) {
            if (chatColor.getBukkit() == c) {
                return chatColor;
            }
        }
        return null;
    }
}
