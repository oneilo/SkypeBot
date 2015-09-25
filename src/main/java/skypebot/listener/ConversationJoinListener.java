package skypebot.listener;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import skypebot.obj.ChatMeta;
import skypebot.wrapper.BotConversation;
import skypebot.wrapper.BotUser;

/**
 * Created by Kyle on 8/31/2015.
 */
public class ConversationJoinListener {
    
    public void onAdd(BotUser user, BotConversation conversation) {
        ChatMeta meta = conversation.getChatMeta();
        if (meta.has("bans")) {
            JsonArray bans = meta.get("bans").getAsJsonArray();
            JsonPrimitive contains = new JsonPrimitive(user.getUsername());
            if (bans.contains(contains)) {
                conversation.kick(user);
                conversation.sendMessage("User is banned from this botChat");
            }
        }
    }
}
