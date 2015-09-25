package skypebot.commands;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import skypebot.permissions.Permission;
import skypebot.util.api.REST;
import skypebot.wrapper.*;
import xyz.gghost.jskype.Chat;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kyle on 8/27/2015.
 */
public class CSay extends BotCommand {

    public CSay(Bot bot) {
        super(bot, "say", "Say something in this chat", Permission.PLUS);
    }

    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (args.length != 0) {
            try {
                String text = Jsoup.parse(StringUtils.join(args, ' ')).text();
                System.out.println("Text: " + text);
                URL url = new URL(text);
                
                StringBuilder lines = new StringBuilder();
                REST rest = new REST(text);
                
                return rest.getAsString();
            } catch (MalformedURLException e) {
                System.out.println("Invalid url");
            }
            
            return Chat.encodeRawText(StringUtils.join(args, ' '));
        } else {
            return getUsage(command, "message");
        }
    }
}
