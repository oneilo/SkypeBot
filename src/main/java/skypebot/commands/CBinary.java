package skypebot.commands;

import org.apache.commons.lang.StringUtils;
import skypebot.util.api.Paste;
import skypebot.wrapper.*;

public class CBinary extends BotCommand {
    
    public CBinary(Bot bot) {
        super(bot, "binary", "Translate a message into or from binary");
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (args.length != 0) {
            String s = StringUtils.join(args, ' ');
            
            // Credit for the translation goes to some guy on SO, idk
            
            StringBuilder output = new StringBuilder();
            if (s.matches("^[01 ]+$")) {
                s = s.replaceAll(" ", "");
                for (int i = 0; i <= s.length() - 8; i += 8) {
                    int k = Integer.parseInt(s.substring(i, i + 8), 2);
                    output.append((char) k);
                }
                return "Text:\n" + output;
            } else {
                byte[] bytes = s.getBytes();
                StringBuilder binary = new StringBuilder();
                for (byte b : bytes) {
                    int val = b;
                    for (int i = 0; i < 8; i++) {
                        binary.append((val & 128) == 0 ? 0 : 1);
                        val <<= 1;
                    }
                    binary.append(' ');
                }
                output.append(binary.toString());
                String url = Paste.postString(botHost.getMain(), output.toString());
                return "Translation:\n" + url;
            }
        } else {
            return "Syntax error: " + command + " [string] expected";
        }
    }
}
