package skypebot.commands;

import org.apache.commons.lang.StringUtils;
import skypebot.util.api.ChatBot;
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
			ChatBot.chat(chat, StringUtils.join(args, ' '), botHost.getExecutor());
            return null;
		} else {
            return "Usage: " + command + " [message]";
		}
	}
}
