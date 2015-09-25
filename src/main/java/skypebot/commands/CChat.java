package skypebot.commands;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import org.apache.commons.lang.StringUtils;
import skypebot.wrapper.*;

/**
 * Created by Kyle on May 25, 2015
 */
public class CChat extends BotCommand {

	public CChat(Bot bot) {
		super(bot, "chat", "Chat with the bot because you are lonely");
	}

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		if (args.length != 0) {
            sendChat(chat, StringUtils.join(args, ' '));
            return null;
		} else {
            return getUsage(command, "message");
		}
	}
    
    public void sendChat(BotConversation chat, String message) {
        botHost.getExecutor().runAsync(() -> {
            ChatterBotFactory factory = new ChatterBotFactory();
            
            try {
                String key = chat.getBot().getMain().getConfiguration().get("api").getAsJsonObject().get("pandorabot").getAsString();
                ChatterBot bot1 = factory.create(ChatterBotType.PANDORABOTS, key);
                ChatterBotSession bot1session = bot1.createSession();
                
                String response = bot1session.think(message);
                
                if (response.contains(".com") || response.contains(".org") || response.contains("Clevermessage")
                        || response.isEmpty()) {
                    sendChat(chat, message);
                    return;
                }
                
                chat.sendMessage(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
