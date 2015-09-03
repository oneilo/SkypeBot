package skypebot.commands;

import skypebot.util.api.MCAPI;
import skypebot.wrapper.*;
import xyz.gghost.jskype.chat.Chat;

import java.util.Arrays;

/**
 * Created by Kyle on Jun 3, 2015
 */
public class CMC extends BotCommand {

	private String help = "Minecraft commands:";

	public CMC(Bot bot) {
		super(bot, "mc", "Minecraft commands");
		for (MCAPI m : MCAPI.values()) {
			help += "\n" + Chat.bold(m.name()) + ": " + m.getDescription();
		}
	}

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		if (args.length != 0) {
			String sub = args[0].toUpperCase();
			for (MCAPI m : MCAPI.values()) {
				if (sub.equals(m.name())) {
					return m.get(Arrays.copyOfRange(args, 1, args.length));
				}
			}
		}
        return help;
	}
}
