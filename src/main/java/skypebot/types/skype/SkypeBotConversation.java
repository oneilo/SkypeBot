package skypebot.types.skype;

import lombok.AllArgsConstructor;
import skypebot.wrapper.Bot;
import skypebot.wrapper.BotConversation;
import skypebot.wrapper.BotMessage;
import skypebot.wrapper.BotUser;
import xyz.gghost.jskype.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 8/31/2015.
 */
@AllArgsConstructor
public class SkypeBotConversation implements BotConversation {
    
    private Group conversation;
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
        
        conversation.getClients().forEach(g -> users.add(new SkypeBotUser(g.getUser(), bot)));
        
        return users;
    }
    
    @Override
    public boolean isUserChat() {
        return conversation.isUserChat();
    }
    
    @Override
    public boolean isAdmin(BotUser user) {
        return conversation.isAdmin(user.getUsername());
    }
    
    @Override
    public void kick(BotUser user) {
        conversation.kick(user.getUsername());
    }
    
    @Override
    public BotMessage sendMessage(String message) {
        System.out.println("=> (" + getId() + ") " + message);
        message = message.replace("&", "");
        return new SkypeBotMessage(bot, conversation.sendMessage(message));
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
