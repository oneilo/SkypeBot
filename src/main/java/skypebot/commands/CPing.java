package skypebot.commands;

import skypebot.wrapper.*;

/**
 * Created by Kyle on May 17, 2015
 */
public class CPing extends BotCommand {

	public CPing(Bot bot) {
		super(bot, "ping", "Check if the bot is alive");
	}

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		return "pong";
	}
}
