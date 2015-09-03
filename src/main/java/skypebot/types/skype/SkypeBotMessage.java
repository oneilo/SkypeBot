package skypebot.types.skype;

import lombok.AllArgsConstructor;
import skypebot.wrapper.Bot;
import skypebot.wrapper.BotMessage;
import skypebot.wrapper.BotUser;
import xyz.gghost.jskype.var.Message;

/**
 * Created by Kyle on 8/31/2015.
 */
@AllArgsConstructor
public class SkypeBotMessage implements BotMessage {
    
    private SkypeBot bot;
    private Message message;
    
    @Override
    public void edit(String text) {
        message.editMessage(bot.getApi(), text);
    }
    
    @Override
    public BotUser getSender() {
        return new SkypeBotUser(message.getSender(), bot);
    }
    
    @Override
    public String getContent() {
        return message.getMessage();
    }
    
    @Override
    public Object getHandle() {
        return message;
    }
    
    @Override
    public Bot getBot() {
        return bot;
    }
}
