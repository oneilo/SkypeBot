package skypebot.commands;

import skypebot.util.Messages;
import skypebot.util.api.Paste;
import skypebot.wrapper.*;

public class CHelp extends BotCommand {

	private String url;

	public CHelp(Bot bot) {
		super(bot, "help", "Shows all commands and info");
		bot.getExecutor().runSync(() -> {
            StringBuilder message = new StringBuilder(Messages.getTitleFormat(bot.getName() + " Command Help") + "\n");
            
            for (BotCommand c : bot.getCommands()) {
                message.append(" ").append(Messages.matchLength(c.getLabel(), 18)).append(": ").append(c.getDescription()).append("\n");
            }
            System.out.println("Uploading command list...");
            url = Paste.postString(bot.getMain(), message.toString());
        });
	}

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		return "Commands and info: " + url;
	}
}
