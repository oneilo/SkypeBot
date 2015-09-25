package skypebot.disabledcommands;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import skypebot.obj.ChatMeta;
import skypebot.permissions.Permission;
import skypebot.wrapper.*;

/**
 * Created by Kyle on 9/20/2015.
 */
public class CMute extends BotCommand {
    
    public CMute(Bot bot) {
        super(bot, "mute", "Mute a user", Permission.ADMIN);
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (!chat.isUserChat()) {
            if (chat.isAdmin(sender)) {
                if (args.length != 0) {
                    BotUser user = botHost.getUser(args[0]);
                    if (user != null) {
                        ChatMeta meta = chat.getChatMeta();
                        JsonArray ignored = meta.getOrSet("mute", JsonArray::new).getAsJsonArray();
                        JsonPrimitive add = new JsonPrimitive(user.getUsername());
                        ignored.add(add);
                        return user.getUsername() + " is now muted";
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
