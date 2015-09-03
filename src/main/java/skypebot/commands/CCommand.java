package skypebot.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import skypebot.obj.ChatMeta;
import skypebot.wrapper.*;

/**
 * Created by Kyle on 8/26/2015.
 */
public class CCommand extends BotCommand {

    public CCommand(Bot bot) {
        super(bot, "command", "Disable or enable commands");
    }

    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (!chat.isUserChat()) {
            if (chat.isAdmin(chatMessage.getSender())) {
                if (args.length > 1) {
                    String subcommand = args[0].toLowerCase();
            
                    if (!botHost.getCommands().stream().filter(c->c.getLabel().equals(subcommand)).findAny().isPresent()) {
                        return subcommand + " is not a valid command";
                    }
            
                    boolean enable;
            
                    String e = args[1].toLowerCase();
            
                    switch (e) {
                        case "enable":
                            enable = true;
                            break;
                        case "disable":
                            enable = false;
                            break;
                        default:
                            return "Could not find value " + e + ", use disable/enable";
                    }
            
                    ChatMeta meta = chat.getChatMeta();
                    JsonArray disabledCommands = meta.getOrSet("disabled-commands", JsonArray::new).getAsJsonArray();
                    JsonPrimitive set = new JsonPrimitive(subcommand);
                    if (enable) {
                        disabledCommands.remove(set);
                    } else {
                        disabledCommands.add(set);
                    }
                    return (enable ? "Enabled" : "Disabled") + " command " + subcommand;
                } else {
                    return "Usage: " + command + " command enable/disable";
                }
            } else {
                return "You must be a chat admin to run this command!";
            }
        } else {
            return "This command must be run in a group conversation";
        }
    }
}
