package skypebot.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import skypebot.obj.ChatMeta;
import skypebot.wrapper.*;

/**
 * Created by Kyle on 8/29/2015.
 */
public class CUnignore extends BotCommand {
    
    public CUnignore(Bot Bot) {
        super(Bot, "unignore", "Unignore commands from a user");
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (!chat.isUserChat()) {
            if (chat.isAdmin(sender)) {
                if (args.length != 0) {
                    BotUser user = botHost.getUser(args[0]);
                    if (user != null) {
                        ChatMeta meta = chat.getChatMeta();
                        JsonArray ignored = meta.getOrSet("ignored", JsonArray::new).getAsJsonArray();
                        JsonPrimitive remove = new JsonPrimitive(user.getUsername());
                        ignored.remove(remove);
                        return "Now listening to " + user.getUsername();
                    } else {
                        return "User " + args[0] + " not found";
                    }
                } else {
                    return "Usage: " + command + " user";
                }
            } else {
                return "You must be a chat admin to run this command!";
            }
        } else {
            return "This command is only allowed in conversations";
        }
    }
}
