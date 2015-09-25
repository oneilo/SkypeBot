package skypebot.commands;

import org.apache.commons.lang.StringUtils;
import skypebot.permissions.Permission;
import skypebot.types.skype.SkypeBot;
import skypebot.wrapper.*;
import xyz.gghost.jskype.Group;
import xyz.gghost.jskype.SkypeAPI;

/**
 * Created by Kyle on 9/23/2015.
 */
public class CTopic extends BotCommand {
    
    public CTopic(Bot bot) {
        super(bot, "topic", "Set the conversation topic", Permission.ADMIN);
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (chat.isAdmin(sender)) {
            if (args.length != 0) {
                SkypeAPI api = ((SkypeBot) botHost).getApi();
                Group conversation = (Group) chat.getHandle();
                conversation.changeTopic(StringUtils.join(args, ' '));
                return null;
            } else {
                return getUsage(command, "topic");
            }
        } else {
            return "Only chat admins can run this command!";
        }
    }
}
