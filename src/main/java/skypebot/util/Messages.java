package skypebot.util;

// Note to self: remove or make better
public class Messages {

    public static String getTitleFormat(String name) {
        return "-------[ " + name + " ]-------";
    }
    
    public static String matchLength(String string, int length) {
        StringBuilder builder = new StringBuilder(string);
        while (builder.length() < length) {
            builder.append(" ");
        }
        return builder.toString();
    }
}
