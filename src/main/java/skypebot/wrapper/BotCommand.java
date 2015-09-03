package skypebot.wrapper;

import lombok.Data;
import lombok.Getter;

@Data
public abstract class BotCommand {

    @Getter
	private final String label;
    @Getter
	private final String description;
    
    protected final Bot botHost;

	public BotCommand(Bot bot, String label, String description) {
		this.label = label;
        this.botHost = bot;
		this.description = description;
	}

	public abstract String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args);

}
