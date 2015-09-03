package skypebot.types.skype.listener;

import lombok.AllArgsConstructor;
import skypebot.listener.ConversationJoinListener;
import skypebot.types.skype.SkypeBot;
import skypebot.types.skype.SkypeBotConversation;
import skypebot.types.skype.SkypeBotUser;
import xyz.gghost.jskype.api.SkypeAPI;
import xyz.gghost.jskype.api.event.EventListener;
import xyz.gghost.jskype.api.events.UserJoinEvent;
import xyz.gghost.jskype.var.Conversation;

/**
 * Created by Kyle on 8/25/2015.
 */
@AllArgsConstructor
public class SkypeConversationJoinListener implements EventListener {

    private SkypeAPI api;
    private SkypeBot bot;
    private ConversationJoinListener conversationJoinListener;
    
    public void onUserJoin(UserJoinEvent e) {
        conversationJoinListener.onAdd(new SkypeBotUser(e.getUser(), bot), new SkypeBotConversation(new Conversation(api, e.getGroup().getChatId(), true), bot));
    }
}
