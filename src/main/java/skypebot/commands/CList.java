package skypebot.commands;

import skypebot.wrapper.*;

/**
 * Created by Kyle on May 16, 2015
 */
public class CList extends BotCommand {

	public CList(Bot bot) {
		super(bot, "list", "List all users in a chat");
	}

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		StringBuilder users = new StringBuilder("All users in chat: ");
		for (BotUser u : chat.getUsers()) {
			users.append(u.getUsername()).append(", ");
		}
		return users.substring(0, users.length() - 2);
	}
}
