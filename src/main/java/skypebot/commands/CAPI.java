package skypebot.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.AllArgsConstructor;
import lombok.Data;
import skypebot.obj.ChatMeta;
import skypebot.wrapper.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyle on 8/29/2015.
 */
public class CAPI extends BotCommand {

    private final Map<BotUser, ConversationData> chats;

    public CAPI(Bot bot) {
        super(bot, "api", "Activate API keys");
        chats = new HashMap<>();
    }

    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (chat.isUserChat()) {
            if (chats.containsKey(sender)) {
                if (args.length > 1) {
                    ConversationData data = chats.remove(sender);
                    ChatMeta meta = chat.getChatMeta();
    
                    JsonObject keys = meta.getOrSet("api", JsonObject::new).getAsJsonObject();
                    keys.add(data.getApiKey(), new JsonPrimitive(args[1]));
                    data.getConversation().sendMessage(sender.getUsername() + " has activated " + data.getApiKey());
                    return "Added key " + data.getApiKey() + " '" + args[1] + "' to " + data.getConversation().getId();
                } else {
                    return "Usage: " + command + " api [api key]";
                }
            } else {
                return "This command must first be run in a group conversation";
            }
        } else {
            if (chat.isAdmin(sender)) {
                if (args.length != 0) {
                    String key = args[0].toLowerCase();
                    
                    if (key.equals("cl") || key.equals("weather")) {
                        sender.sendMessage("To activate your API key please reply to this message with the following" +
                                "\n-api " + key + " [api key]");
                        chats.put(sender, new ConversationData(chat, key));
                        return "Please follow the sent instructions";
                    } else {
                        return "Invalid API " + key;
                    }
                } else {
                    return "Usage: " + command + " api";
                }
            } else {
                return "You must be a chat admin to run this command!";
            }
        }
    }
    
    @Data
    @AllArgsConstructor
    class ConversationData {
        private BotConversation conversation;
        private String apiKey;
    }
}
