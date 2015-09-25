package skypebot.commands;

import org.apache.commons.lang.StringUtils;
import skypebot.types.skype.SkypeBotUser;
import skypebot.wrapper.*;
import xyz.gghost.jskype.Chat;
import xyz.gghost.jskype.user.User;

/**
 * Created by Kyle on 9/10/2015.
 */
public class CMe extends BotCommand {
    
    public CMe(Bot bot) {
        super(bot, "me", "Get your info");
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        StringBuilder report = new StringBuilder("Info");
       
        appendIfExist("Username", sender.getUsername(), report);
        appendIfExist("Mood", sender.getMood(), report);
       
        if (sender instanceof SkypeBotUser) {
            User skypeBotUser = (User) ((SkypeBotUser) sender).getHandle();
            appendIfExist("Display name", skypeBotUser.getDisplayName(), report);
            appendIfExist("Profile picture", skypeBotUser.getPictureUrl(), report);
            appendIfExist("Status", StringUtils.capitalize(skypeBotUser.getOnlineStatus().name().toLowerCase()), report);
        }
        
        return report.toString();
    }
    
    private void appendIfExist(String key, String value, StringBuilder builder) {
        builder.append("\n    ").append(Chat.bold(key)).append(": ").append(value);
    }
}
