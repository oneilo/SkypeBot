package skypebot.commands;

import skypebot.wrapper.*;

/**
 * Created by Kyle on Jun 10, 2015
 */
public class CID extends BotCommand {

	public CID(Bot bot) {
		super(bot, "id", "Get current chat ID");
	}

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		return chat.getId();
	}
}
