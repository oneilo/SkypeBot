package skypebot.commands;

import com.google.gson.JsonObject;
import skypebot.util.api.REST;
import skypebot.wrapper.*;
import xyz.gghost.jskype.Chat;

import java.io.UnsupportedEncodingException;

public class CURL extends BotCommand {
    
    public CURL(Bot bot) {
        super(bot, "url", "Shortens a URL");
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (args.length == 0) {
            return getUsage(command, "url");
        }
        
        BotMessage message = chat.sendMessage("Generating");
        
        try {
            message.edit("Shortened URL: " + Chat.link(shorten(args[0])));
        } catch (RuntimeException e) {
            message.edit(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            message.edit("Error: could not shorten URL " + args[0] + "\n" + e.getMessage());
        }
        return null;
    }
    
    public String shorten(String url) throws UnsupportedEncodingException {
        String key = botHost.getMain().getConfiguration().get("api").getAsJsonObject().get("bitly").getAsString();
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        REST rest = new REST("https://api-ssl.bitly.com/v3/shorten?access_token=" + key + "&longUrl=", url);
        System.out.println("URL " + rest.getUrl());
        JsonObject object = rest.getAsJsonObject();
        if (object.get("status_code").getAsInt() == 200) {
            return object.get("data").getAsJsonObject().get("url").getAsString();
        } else {
            throw new RuntimeException("An error occurred while shortening url " + object.get("status_txt").getAsString() + " original " +
                    "url " + url);
        }
    }
}
