package skypebot.commands;

import skypebot.types.skype.SkypeBotConversation;
import skypebot.wrapper.*;
import xyz.gghost.jskype.Chat;
import xyz.gghost.jskype.Group;

/**
 * Created by Kyle on 8/29/2015.
 */
public class CInfo extends BotCommand {
    
    public CInfo(Bot bot) {
        super(bot, "info", "Show conversation info");
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        StringBuilder info = new StringBuilder();
        info.append("Chat Information").append("\n");
        info.append(Chat.bold("Users in chat: ")).append(chat.getUsers().size()).append("\n");
        info.append(Chat.bold("Chat ID: ")).append(chat.getId()).append("\n");
        
        if (!chat.getName().isEmpty()) {
            info.append(Chat.bold("Topic: ")).append(chat.getName()).append("\n");
        }
        
         if (chat instanceof SkypeBotConversation) {
            String url = ((Group) chat.getHandle()).getPictureUrl();
            if (!url.isEmpty()) {
                info.append(Chat.bold("Picture: ")).append(Chat.link(url)).append("\n");
            }
        }
        
        
        //ChatMeta meta = chat.getChatMeta();
        
        //info.append(Chat.bold("Meta: ")).append("\n");
        
        //info.append("   ").append(meta.toString().replace("\n", "\n   "));
        
        return info.toString();
    }
}
