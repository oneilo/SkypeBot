package skypebot.commands;

import org.apache.commons.lang.StringUtils;
import skypebot.util.api.StrawPoll;
import skypebot.wrapper.*;
import xyz.gghost.jskype.Chat;

import java.util.Arrays;

/**
 * Created by Kyle on 8/25/2015.
 */
public class CPoll extends BotCommand {

    public CPoll(Bot bot) {
        super(bot, "poll", "Strawpoll commands");
    }

    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (args.length != 0) {
            String sub = args[0].toLowerCase();
            switch (sub) {
                case "create":
                    String all = StringUtils.join(args, ' ', 1, args.length);
                    if (args.length > 1 && all.contains(",")) {
                        String[] split = all.split(",");
                        String title = split[0];
                        split = Arrays.copyOfRange(split, 1, split.length);
                        StrawPoll poll = new StrawPoll(title, Arrays.asList(split));
                        poll.create();
                        return "Poll " + Chat.link(poll.getUrl());
                    } else {
                        return "Usage: " + command + "poll create Name of poll,option1,option2,option3,option4...";
                    }
                case "view":
                    if (args.length != 1) {
                        try {
                            int id = Integer.parseInt(args[1]);
                            StrawPoll poll = new StrawPoll(id);
                            poll.refresh();
                            StringBuilder message = new StringBuilder();
                            message.append(poll.getTitle()).append("\n");
                            poll.getOptions().forEach((k, v) -> message.append(Chat.bold(k)).append(": ").append(v).append("\n"));
                            return message.toString();
                        } catch (NumberFormatException e) {
                            return "The poll ID must be an integer";
                        }
                    } else {
                        return "Usage: " + command + "poll view id";
                    }
                default:
                    return "Unknown sub command " + sub + ", use " + command + " for a list of subcommands";
            }
        } else {
            return "Usage: " + command + " create|view";
        }
    }
}
