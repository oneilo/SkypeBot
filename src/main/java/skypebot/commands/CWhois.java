package skypebot.commands;

import skypebot.util.api.DomainWhoisLookup;
import skypebot.util.api.Paste;
import skypebot.wrapper.*;
import xyz.gghost.jskype.chat.Chat;

import java.io.IOException;

/**
 * Created by Kyle on May 16, 2015
 */
public class CWhois extends BotCommand {
    
	public CWhois(Bot bot) {
		super(bot, "whois", "Get domain info");
    }

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		if (args.length != 0) {
            String domain = args[0];

            final BotMessage message = chat.sendMessage("Querying server, please wait");

            botHost.getExecutor().runAsync(() -> {
                try {
                    String whois = new DomainWhoisLookup(domain).getWhois();
                    if (whois.length() > 3) {
                        message.edit("Whois: " + Chat.link(Paste.postString(botHost.getMain(), whois)));
                    } else {
                        message.edit("No results found");
                    }
                } catch (IOException e) {
                    message.edit("Cannot connect to whois server at this time, please try again later\n" + e.getMessage());
                    e.printStackTrace();
                }
            });
            return null;
        } else {
            return "Usage: " + command + " domain";
		}
	}
}
