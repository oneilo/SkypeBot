package skypebot.types.skype.listener;

import lombok.AllArgsConstructor;
import skypebot.listener.ChatListener;
import skypebot.types.skype.SkypeBot;
import skypebot.types.skype.SkypeBotConversation;
import skypebot.types.skype.SkypeBotMessage;
import skypebot.types.skype.SkypeBotUser;
import xyz.gghost.jskype.api.SkypeAPI;
import xyz.gghost.jskype.api.event.EventListener;
import xyz.gghost.jskype.api.events.UserChatEvent;

/**
 * Created by Kyle on 8/31/2015.
 */
@AllArgsConstructor
public class SkypeChatListener implements EventListener {
    
    private SkypeBot bot;
    private SkypeAPI api;
    private ChatListener listener;
 
    public void onChat(UserChatEvent e) {
        if (!e.getUser().getUsername().equals(api.getSkype().getUsername())){
            listener.onMessageReceived(bot, new SkypeBotUser(e.getUser(), bot), new SkypeBotMessage(bot, e.getMsg()), new SkypeBotConversation(e.getChat(), bot));
        }
    }
}
