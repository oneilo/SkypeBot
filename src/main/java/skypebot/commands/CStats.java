package skypebot.commands;

import skypebot.obj.bot.BotStats;
import skypebot.util.Messages;
import skypebot.util.TimeBuilder;
import skypebot.wrapper.*;

public class CStats extends BotCommand {

    private final BotStats stats;
    
	public CStats(Bot botHost) {
		super(botHost, "stats", "Get botHost stats");
        this.stats = botHost.getStats();
	}

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		StringBuilder message = new StringBuilder();
		message.append(Messages.getTitleFormat(botHost.getName() + " Bot Stats")).append("\n");

        message.append("Version: ").append(botHost.getMain().getProperties().getVersion()).append("\n");
		message.append("Session uptime: ").append(TimeBuilder.fromLong(System.currentTimeMillis() - stats.getStartTime())).append("\n");
		message.append("Total online time: ").append(TimeBuilder.fromLong(stats.getTotalUpTime())).append("\n");
		//message.append("Messages sent: ").append(stats.getMessagesSent()).append("\n");
		message.append("Messages received: ").append(stats.getMessagesReceived()).append("\n");

		int cores = Runtime.getRuntime().availableProcessors();

		message.append("System:\n");
		message.append("	OS:			").append(System.getProperty("os.name")).append("\n");
		message.append("	Cores: 		").append(cores).append("\n");
		//message.append("	Free mem: 	").append(getGb(Runtime.getRuntime().freeMemory())).append("\n");
		//message.append("	Max mem:	").append(getGb(Runtime.getRuntime().maxMemory())).append("\n");
		message.append("Java version: ").append(System.getProperty("java.version")).append("\n");

		return message.toString();
	}

	private static String getGb(long i) {
		if (i == Long.MAX_VALUE) {
			return "no limit";
		}

		i = i / 1073741824;
		if (i == 0) {
			return "less than 1gb";
		} else {
			return i + "";
		}
	}
}
