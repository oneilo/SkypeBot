package skypebot.listener;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import skypebot.BotMain;
import skypebot.obj.ChatMeta;
import skypebot.obj.bot.BotStats;
import skypebot.permissions.GlobalPermissions;
import skypebot.wrapper.*;
import xyz.gghost.jskype.Chat;

/**
 * Created by Kyle on 8/31/2015.
 */
public class ChatListener {
    
    private static final String COMMAND_PREFIX = "-";
    
    private BotStats stats;
    private GlobalPermissions globalPermissions;
    
    public ChatListener(BotMain botMain, GlobalPermissions globalPermissions) {
        this.stats = botMain.getStats();
        this.globalPermissions = globalPermissions;
    }
    
    public void onMessageReceived(Bot bot, BotUser sender, BotMessage message, BotConversation conversation) {
        
        ChatMeta meta = conversation.getChatMeta();
        String prefix = meta.has("command-prefix") ? meta.get("command-prefix").getAsString() : COMMAND_PREFIX;
    
        String text = message.getContent();
        
        System.out.println(bot.getName() + " < " + sender.getUsername() + " " + text);
        
        stats.setMessagesReceived(stats.getMessagesReceived()+1);
        
        if (text.startsWith(prefix)) {
            if (meta.has("ignore")) {
                JsonArray ignored = meta.get("ignored").getAsJsonArray();
                JsonPrimitive contains = new JsonPrimitive(sender.getUsername());
                if (ignored.contains(contains)) { // ignored
                    return;
                }
            }
    
            String command = text.substring(1, text.contains(" ") ? text.indexOf(" ") : text.length()).toLowerCase().trim();
            
            if (meta.has("disabled-commands")) {
                JsonArray disabledCommands = meta.get("disabled-commands").getAsJsonArray();
                JsonPrimitive contains = new JsonPrimitive(command);
                if (disabledCommands.contains(contains)) {
                    conversation.sendMessage("That command is disabled");
                    return;
                }
            }
    
            for (BotCommand c : bot.getCommands()) {
                if (c.getLabel().equals(command)) {
                    
                    /*
                     if (!globalPermissions.hasPermission(sender, c.getPermission()) && !meta.getChatPermissions().hasPermission(sender, c.getPermission())) {
                        conversation.sendMessage("You do not have permission to run this command");
                        return;
                    }
                     */
                    
                    try {
                        String[] args = text.contains(" ") ? text.substring(text.indexOf(" "), text.length()).trim().split(" ")
                                : new String[0];
                        String say = c.run(sender, prefix + command, message, conversation, args);
                        if (say != null && say.length() != 0) {
                            conversation.sendMessage(Chat.bold("(" + sender.getUsername() + ")") + " " + say);
                        }
                        return;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        return;
                    }
                }
            }
            conversation.sendMessage("Cannot find that command, use " + prefix + "help for a list of commands");
        }
    }
}
