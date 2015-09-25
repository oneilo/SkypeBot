package skypebot.commands;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import skypebot.wrapper.*;
import xyz.gghost.jskype.Chat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Kyle on 9/23/2015.
 */
public class CQR extends BotCommand {
    
    public CQR(Bot bot) {
        super(bot, "qr", "Generate a QR code from a link");
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (args.length > 0) {
            String url = Jsoup.parse(StringUtils.join(args, ' ')).text();
            try { 
                return Chat.link("http://chart.googleapis.com/chart?cht=qr&chs=200x200&chl=" + URLEncoder.encode(url, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "Could not encode URL " + e.getMessage();
            }
        } else {
            return getUsage(command, "url");
        }
    }
}
