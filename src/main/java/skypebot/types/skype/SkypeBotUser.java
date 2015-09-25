package skypebot.types.skype;

import lombok.AllArgsConstructor;
import skypebot.wrapper.Bot;
import skypebot.wrapper.BotMessage;
import skypebot.wrapper.BotUser;
import xyz.gghost.jskype.user.User;

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
        return new SkypeBotMessage(bot, user.getGroup(bot.getApi()).sendMessage(message));
    }
    
    @Override
    public void requestContact(String message) {
        bot.getApi().sendContactRequest(getUsername(), message);
    }
    
    @Override
    public boolean isContact() {
        return user.isContact();
    }
    
    @Override
    public String getMood() {
        return user.getMood();
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
