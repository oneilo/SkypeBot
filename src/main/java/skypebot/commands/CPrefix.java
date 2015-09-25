package skypebot.commands;

import com.google.gson.JsonPrimitive;
import skypebot.obj.ChatMeta;
import skypebot.wrapper.*;

/**
 * Created by Kyle on 8/25/2015.
 */
public class CPrefix extends BotCommand {

    public CPrefix(Bot bot) {
        super(bot, "prefix", "Set the command prefix for this chat");
    }

    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (chat.isAdmin(sender)) {
            if (args.length != 0) {
                String prefix = args[0];
                if (prefix.length() == 1) {
                    ChatMeta meta = chat.getChatMeta();
                    meta.set("command-prefix", new JsonPrimitive(prefix));
                    return "Bot prefix set to '" + prefix + "'";
                } else {
                    return "Prefix length cannot exceed 1 character";
                }
            } else {
                return getUsage(command, "prefix");
            }
        } else {
            return "You must be a chat admin to run this command!";
        }
    }
}
