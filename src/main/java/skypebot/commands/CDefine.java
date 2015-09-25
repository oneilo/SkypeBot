package skypebot.commands;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import skypebot.wrapper.*;
import xyz.gghost.jskype.Chat;

import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Kyle on 9/8/2015.
 */
public class CDefine extends BotCommand {
    
    public CDefine(Bot bot) {
        super(bot, "define", "Define a term");
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (args.length != 0) {
            try {
                String search = StringUtils.join(args, ' ');
                String url = "http://www.urbandictionary.com/define.php?term=" + URLEncoder.encode(search, "UTF-8");
                Document doc = Jsoup.parse(new URL(url), 5000);
                Elements elements = doc.getElementsByClass("meaning");
        
                if (elements.size() != 0) {
                    return Chat.bold(search) + "\n" + elements.get(0).text() + "\n" + Chat.link(url);
                } else {
                    return "No definition found";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "An error occurred, try again later " + e.getMessage();
            }
        } else {
            return getUsage(command, "term");
        }
    }
}
