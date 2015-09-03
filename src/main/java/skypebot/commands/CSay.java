package skypebot.commands;

import org.apache.commons.lang.StringUtils;
import skypebot.wrapper.*;

/**
 * Created by Kyle on 8/27/2015.
 */
public class CSay extends BotCommand {

    public CSay(Bot bot) {
        super(bot, "say", "Say something in this chat");
    }

    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (args.length != 0) {
            return StringUtils.join(args, ' ');
        } else {
            return "Usage: " + command + " [message]";
        }
    }
}
