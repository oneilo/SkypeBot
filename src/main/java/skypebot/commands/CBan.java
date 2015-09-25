package skypebot.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import skypebot.obj.ChatMeta;
import skypebot.permissions.Permission;
import skypebot.wrapper.*;

/**
 * Created by Kyle on 8/25/2015.
 */
public class CBan extends BotCommand {
    
    public CBan(Bot bot) {
        super(bot, "ban", "Ban a user from a chat", Permission.ADMIN);
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (!chat.isUserChat()) {
            if (chat.isAdmin(sender)) {
                if (args.length != 0) {
                    BotUser user = botHost.getUser(args[0]);
                    if (user != null) {
                        ChatMeta meta = chat.getChatMeta();
                        JsonArray bans = meta.getOrSet("bans", JsonArray::new).getAsJsonArray();
                        JsonPrimitive target = new JsonPrimitive(user.getUsername());
                        if (!bans.contains(target)) {
                            bans.add(target);
                        }
                        chat.kick(user);
                        return "Banned " + user.getUsername() + " from this chat";
                    } else {
                        return "User " + args[0] + " not found";
                    }
                } else {
                    return getUsage(command, "user");
                }
            } else {
                return "You must be a chat admin to run this command!";
            }
        } else {
            return "This command is only allowed in conversations";
        }
    }
}
