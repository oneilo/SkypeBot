package skypebot.commands;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import skypebot.wrapper.*;
import xyz.gghost.jskype.chat.Chat;

import java.io.IOException;

/**
 * Created by Kyle on May 26, 2015
 */
public class CRandomVideo extends BotCommand {
    
    private static final String URL = "http://www.petittube.com";
    private static final String BASE_URL = "http://youtube.com/watch?v=";
    
    public CRandomVideo(Bot bot) {
        super(bot, "randomvideo", "Get a really random video");
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        BotMessage message = chat.sendMessage("Generating");
        try {
            message.edit("Random video: " + Chat.link(getRandomURL()));
        } catch (IOException e) {
            message.edit("Could not fetch video at this time, try again later\n" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    private static String getRandomURL() throws IOException {
        Document doc = Jsoup.connect(URL).get();
        String s = doc.getElementsByTag("IFRAME").first().attr("src");
        return BASE_URL + s.substring(s.lastIndexOf("/") + 1, s.indexOf("?"));
    }
}
