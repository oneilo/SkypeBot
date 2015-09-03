package skypebot.util.api;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import skypebot.obj.bot.BotExecutor;
import skypebot.wrapper.BotConversation;

/**
 * Created by Kyle on May 25, 2015
 */
public class ChatBot {
    
    public static void chat(BotConversation chat, String message, BotExecutor executor) {
        executor.runAsync(() -> {
            ChatterBotFactory factory = new ChatterBotFactory();
            
            try {
                String key = chat.getBot().getMain().getConfiguration().get("api").getAsJsonObject().get("pandorabot").getAsString();
                ChatterBot bot1 = factory.create(ChatterBotType.PANDORABOTS, key);
                ChatterBotSession bot1session = bot1.createSession();
                
                String response = bot1session.think(message);
                
                if (response.contains(".com") || response.contains(".org") || response.contains("Clevermessage")
                        || response.isEmpty()) {
                    chat(chat, message, executor);
                    return;
                }
                
                chat.sendMessage(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
