package skypebot.commands;

import com.google.gson.JsonObject;
import skypebot.obj.ChatMeta;
import skypebot.util.api.Weather;
import skypebot.wrapper.*;
import xyz.gghost.jskype.chat.Chat;

/**
 * Created by Kyle on May 17, 2015
 */
public class CWeather extends BotCommand {
    
    public CWeather(Bot bot) {
        super(bot, "weather", "Get weather in your area");
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        ChatMeta meta = chat.getChatMeta();
        
        if (!meta.has("api") || meta.get("api").getAsJsonObject().has("weather")) {
            return "Due to API restrictions this command has been disabled for this chat" +
                    "\nYou can register an API key at " + Chat.link("http://openweathermap.org/appid#get") +
                    " and run '" + command.charAt(0) + "api weather' to activate the weather command in this chat";
        }
    
        JsonObject api = meta.get("api").getAsJsonObject();
        
        BotMessage message = chat.sendMessage("Generating");
        if (args.length != 0) {
            message.edit(Weather.getWeather(args[0], api.get("weather").getAsString()));
        } else {
            message.edit("Usages:\n" + command + "weather zipcode,countrycode or !weather city\nExample: !weather 80540,US or !weather Lyons,US");
        }
        return null;
    }
}
