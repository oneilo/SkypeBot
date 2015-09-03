package skypebot.commands;

import skypebot.wrapper.*;

import java.util.Random;

/**
 * Created by Kyle on May 17, 2015
 */
public class CCoin extends BotCommand {

	public CCoin(Bot bot) {
		super(bot, "coin", "Flip a coin");
	}

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		return new Random().nextBoolean() ? "heads" : "tails";
	}
}
