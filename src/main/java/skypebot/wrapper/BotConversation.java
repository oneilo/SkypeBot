package skypebot.wrapper;

import skypebot.obj.ChatMeta;

import java.util.List;

/**
 * Created by Kyle on 8/31/2015.
 */
public interface BotConversation extends BotImplement {
    
    String getId();
    
    String getName();
    
    List<BotUser> getUsers();
    
    boolean isUserChat();
    
    boolean isAdmin(BotUser user);
    
    void kick(BotUser user);
    
    BotMessage sendMessage(String message);
    
    default ChatMeta getChatMeta() {
        return ChatMeta.getChatMeta(this);
    }
    
    Object getHandle();
    
}
