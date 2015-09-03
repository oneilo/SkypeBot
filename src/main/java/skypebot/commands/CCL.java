package skypebot.commands;

import com.google.gson.JsonObject;
import skypebot.obj.ChatMeta;
import skypebot.util.Messages;
import skypebot.util.api.CLAPI;
import skypebot.util.api.Paste;
import skypebot.util.api.REST;
import skypebot.wrapper.*;

import java.io.IOException;

/**
 * Created by Kyle on May 26, 2015
 */
public class CCL extends BotCommand {
    
    private final String URL;
    
    public CCL(Bot bot) {
        super(bot, "cl", "API commands");
        StringBuilder message = new StringBuilder();
        message.append(Messages.getTitleFormat("CLAPI BotCommand List")).append("\n");
        for (CLAPI c : CLAPI.values()) {
            message.append(Messages.matchLength(c.name(), 20)).append(" ").append(c.getDescription()).append("\n");
        }
        URL = Paste.postString(bot.getMain(), message.toString());
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        
        ChatMeta meta = chat.getChatMeta();
        if (!meta.has("api") || meta.get("api").getAsJsonObject().has("cl")) {
            return "Due to API restrictions this command has been disabled for this chat" +
                    "\nYou can purchase an API key at http://api.c99.nl/ and run '" + command.charAt(0) + "api cl' to activate the cl command in this chat";
        }
    
        JsonObject keys = meta.get("api").getAsJsonObject();
        
        if (args.length != 0) {
            String sub = args[0];
            for (CLAPI clapi : CLAPI.values()) {
                if (sub.equalsIgnoreCase(clapi.name())) {
                    String value = "";
                    
                    if (clapi.isRequireValue()) {
                        if (args.length > 1) {
                            value = args[1];
                        } else {
                            return "Usage: " + command + " " + clapi.name() + " " + clapi.getKey();
                        }
                    }
                    
                    BotMessage message = chat.sendMessage("Running please wait");
                    
                    try {
                        REST val = clapi.getValue(keys.get("cl").getAsString(), value);
                        String ret = val.getAsString().replace("<br>", "\n");
                        if (ret.length() < 100) {
                            message.edit(ret);
                        } else {
                            val.setResult(Paste.postString(botHost.getMain(), Messages.getTitleFormat("CL " + clapi.name()) + "\n" + ret));
                            message.edit(val.getAsCroppedURL(100));
                        }
                    } catch (IOException e) {
                        message.edit("An error occurred, try again later\n" + e.getMessage());
                        e.printStackTrace();
                    }
                    return null;
                }
            }
            return "BotCommand not found, CL Help: " + URL;
        } else {
            return "CL Help: " + URL;
        }
    }
}
