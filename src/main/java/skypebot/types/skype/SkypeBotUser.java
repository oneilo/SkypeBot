package skypebot.types.skype;

import lombok.AllArgsConstructor;
import skypebot.wrapper.Bot;
import skypebot.wrapper.BotMessage;
import skypebot.wrapper.BotUser;
import xyz.gghost.jskype.var.User;

/**
 * Created by Kyle on 8/31/2015.
 */
@AllArgsConstructor
public class SkypeBotUser implements BotUser {
    
    private User user;
    private SkypeBot bot;
    
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    
    @Override
    public BotMessage sendMessage(String message) {
        return new SkypeBotMessage(bot, user.sendMessage(bot.getApi(), message));
    }
    
    @Override
    public Object getHandle() {
        return user;
    }
    
    @Override
    public Bot getBot() {
        return bot;
    }
}
