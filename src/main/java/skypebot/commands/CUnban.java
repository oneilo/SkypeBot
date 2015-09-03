package skypebot.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import skypebot.obj.ChatMeta;
import skypebot.wrapper.*;

/**
 * Created by Kyle on 8/25/2015.
 */
public class CUnban extends BotCommand {
    
    public CUnban(Bot Bot) {
        super(Bot, "unban", "Ban a user from a chat");
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (!chat.isUserChat()) {
            if (chat.isAdmin(sender)) {
                if (args.length != 0) {
                    BotUser user = botHost.getUser(args[0]);
                    if (user != null) {
                        ChatMeta meta = chat.getChatMeta();
                        if (meta.has("bans")) {
                            JsonArray bans = meta.get("bans").getAsJsonArray();
                            JsonPrimitive remove = new JsonPrimitive(user.getUsername());
                            bans.remove(remove);
                        }
                        return "Unbanned " + user.getUsername();
                    } else {
                        return "User " + args[0] + " not found";
                    }
                } else {
                    return "Usage: " + command + " user";
                }
            } else {
                return "You must be a chat admin to run this command!";
            }
        } else  {
            return "This command is only allowed in conversations";
        }
    }
}
