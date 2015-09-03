package skypebot.types.skype;

import lombok.AllArgsConstructor;
import skypebot.wrapper.Bot;
import skypebot.wrapper.BotConversation;
import skypebot.wrapper.BotMessage;
import skypebot.wrapper.BotUser;
import xyz.gghost.jskype.var.Conversation;
import xyz.gghost.jskype.var.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 8/31/2015.
 */
@AllArgsConstructor
public class SkypeBotConversation implements BotConversation {
    
    private Conversation conversation;
    private SkypeBot bot;
   
    @Override
    public String getId() {
        return conversation.getId();
    }
    
    @Override
    public String getName() {
        return conversation.getTopic();
    }
    
    @Override
    public List<BotUser> getUsers() {
        
        List<BotUser> users = new ArrayList<>();
        
        conversation.getConnectedClients().forEach(g -> users.add(new SkypeBotUser(new User(g.getAccount().getUsername()), bot)));
        
        return users;
    }
    
    @Override
    public boolean isUserChat() {
        return conversation.isUserChat();
    }
    
    @Override
    public boolean isAdmin(BotUser user) {
        return conversation.isAdmin((User) user.getHandle());
    }
    
    @Override
    public void kick(BotUser user) {
        conversation.kick((User) user.getHandle());
    }
    
    @Override
    public BotMessage sendMessage(String message) {
        return new SkypeBotMessage(bot, conversation.sendMessage(bot.getApi(), message));
    }
    
    @Override
    public Object getHandle() {
        return conversation;
    }
    
    @Override
    public Bot getBot() {
        return bot;
    }
}
