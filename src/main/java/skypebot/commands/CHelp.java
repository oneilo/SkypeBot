package skypebot.commands;

import skypebot.util.api.Paste;
import skypebot.wrapper.*;
import xyz.gghost.jskype.Chat;

public class CHelp extends BotCommand {
    
    private String url = null;
    
    public CHelp(Bot bot) {
        super(bot, "help", "Shows all commands and info");
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (url == null) {
            BotMessage botMessage = chat.sendMessage("Uploading command list, please wait...");
            try {
                generateUrl();
                botMessage.edit("Commands and info: " + url);
            } catch (Exception e) {
                e.printStackTrace();
                botMessage.edit("An internal error occurred, please try again later\n" + e.getMessage());
            }
            return null;
        } else {
            return "Commands and info: " + url;
        }
    }
    
    private void generateUrl() {
        StringBuilder message = new StringBuilder(botHost.getName() + " Command Help:" + "\n");
        
        for (BotCommand c : botHost.getCommands()) {
            message.append(" ").append(matchLength(c.getLabel(), 18)).append(": ").append(c.getDescription()).append("\n");
        }
        System.out.println("Uploading command list...");
        url = Paste.postString(botHost.getMain(), message.toString());
        url = Chat.link(url);
    }
    
    private String matchLength(String string, int length) {
        StringBuilder builder = new StringBuilder(string);
        while (builder.length() < length) {
            builder.append(" ");
        }
        return builder.toString();
    }
}
