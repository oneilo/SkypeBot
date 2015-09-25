package skypebot.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import skypebot.obj.ChatMeta;
import skypebot.permissions.Permission;
import skypebot.wrapper.*;

/**
 * Created by Kyle on 8/29/2015.
 */
public class CIgnore extends BotCommand {
    
    public CIgnore(Bot bot) {
        super(bot, "ignore", "Ignore commands from a user", Permission.MODERATOR);
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
                        JsonPrimitive add = new JsonPrimitive(user.getUsername());
                        ignored.add(add);
                        return "Now ignoring " + user.getUsername();
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
