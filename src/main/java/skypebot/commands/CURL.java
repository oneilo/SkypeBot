package skypebot.commands;

import skypebot.util.api.Bitly;
import skypebot.wrapper.*;
import xyz.gghost.jskype.chat.Chat;

public class CURL extends BotCommand {

	public CURL(Bot bot) {
		super(bot, "url", "Shortens a URL");
	}

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		if (args.length == 0) {
			return "Usage: " + command + " url";
		}

        BotMessage message = chat.sendMessage("Generating");

		try {
            message.edit("Shortened URL: " + Chat.link(Bitly.shorten(botHost.getMain(), args[0])));
		} catch (Exception e) {
			message.edit("Error: could not shorten URL " + args[0] + "\n" + e.getMessage());
			e.printStackTrace();
		}
        return null;
	}
}
