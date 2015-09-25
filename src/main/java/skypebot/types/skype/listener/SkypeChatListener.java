package skypebot.types.skype.listener;

import skypebot.listener.ChatListener;
import skypebot.types.skype.SkypeBot;
import skypebot.types.skype.SkypeBotConversation;
import skypebot.types.skype.SkypeBotMessage;
import skypebot.types.skype.SkypeBotUser;
import xyz.gghost.jskype.SkypeAPI;
import xyz.gghost.jskype.event.EventListener;
import xyz.gghost.jskype.events.UserChatEvent;

/**
 * Created by Kyle on 8/31/2015.
 */
public class SkypeChatListener implements EventListener {
    
    private SkypeBot bot;
    private SkypeAPI api;
    private ChatListener listener;
    private String username;
    
    public SkypeChatListener(SkypeBot bot, SkypeAPI api, ChatListener listener) {
        this.bot = bot;
        this.api = api;
        this.listener = listener;
        this.username = bot.getMain().getConfiguration().get("skype").getAsJsonObject().get("user").getAsString();
    }
    
    public void onChat(UserChatEvent e) {
        if (!e.getUser().getUsername().equals(username)){
            listener.onMessageReceived(bot, new SkypeBotUser(e.getUser(), bot), new SkypeBotMessage(bot, e.getMsg()), new SkypeBotConversation(e.getChat(), bot));
        }
    }
}
