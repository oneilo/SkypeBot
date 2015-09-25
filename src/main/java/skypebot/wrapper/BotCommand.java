package skypebot.wrapper;

import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import skypebot.permissions.Permission;
import xyz.gghost.jskype.Chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public abstract class BotCommand {
    
    @Getter
    private final String label;
    @Getter
    private final String description;
    @Getter
    private final Permission permission;
    @Getter
    private final List<BotCommand> subCommands;
    
    protected final Bot botHost;
    
    public BotCommand(Bot bot, String label, String description, Permission permission) {
        this.label = label;
        this.botHost = bot;
        this.description = description;
        this.permission = permission;
        this.subCommands = new ArrayList<>();
    }
    
    public BotCommand(Bot bot, String label, String description) {
        this(bot, label, description, Permission.DEFAULT);    
    }
        
    public String run(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (subCommands.size() != 0) {
            String error;
            if (args.length != 0) {
                String sub = args[0].toLowerCase();
                
                for (BotCommand botCommand : subCommands) {
                    if (botCommand.getLabel().equals(sub)) {
                        return botCommand.run(sender, command + " " + sub, chatMessage, chat, Arrays.copyOfRange(args, 1, args.length));
                    }
                }
                
                error = "command not found";
            } else {
                error = getUsage(command, "command");
            }
            
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error: ").append(error);
            
            subCommands.forEach(botCommand -> {
                stringBuilder.append("\n");
                stringBuilder.append(Chat.bold(botCommand.getLabel()));
                stringBuilder.append(" - ").append(botCommand.getDescription());
            });
            
            return stringBuilder.toString();
        } else {
            return called(sender, command, chatMessage, chat, args);
        }
    }
    
    protected String getUsage(String command, String... params) {
        return "Usage: " + command + " " + StringUtils.join(params, ' ');
    }
    
    protected abstract String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args);
}
