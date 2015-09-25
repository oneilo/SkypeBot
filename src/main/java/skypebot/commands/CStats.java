package skypebot.commands;

import skypebot.obj.bot.BotStats;
import skypebot.types.skype.SkypeBot;
import skypebot.util.TimeBuilder;
import skypebot.wrapper.*;
import xyz.gghost.jskype.SkypeAPI;

public class CStats extends BotCommand {

    private final BotStats stats;
    
	public CStats(Bot bot) {
		super(bot, "stats", "Get bot stats");
        this.stats = bot.getStats();
	}

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		StringBuilder message = new StringBuilder();
		message.append("Stats").append("\n");

        message.append("Version: ").append(botHost.getMain().getProperties().getVersion()).append("\n");
        message.append("jSkype version: ").append(botHost.getMain().getProperties().getDependency("jSkype").getVersion()).append("\n");
        
        if (botHost instanceof SkypeBot) {
            SkypeBot bot = (SkypeBot) botHost;
            SkypeAPI api = bot.getApi();
            message.append("Contacts: ").append(api.getContacts().size()).append("\n");
            message.append("Groups: ").append(api.getGroups().size()).append("\n");
        }
        
        //message.append("Contacts: " + botHost.getMain())
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
		message.append("    Java version: ").append(System.getProperty("java.version")).append("\n");

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
