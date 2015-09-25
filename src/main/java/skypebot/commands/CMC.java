package skypebot.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import skypebot.BotMain;
import skypebot.util.BukkitChatFormat;
import skypebot.util.api.Imgur;
import skypebot.util.api.REST;
import skypebot.util.api.apis.mcping.MinecraftPing;
import skypebot.util.api.apis.mcping.MinecraftPingOptions;
import skypebot.util.api.apis.mcping.MinecraftPingReply;
import skypebot.wrapper.*;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Kyle on Jun 3, 2015
 */
public class CMC extends BotCommand {
    
    public CMC(Bot bot) {
        super(bot, "mc", "Minecraft commands");
        getSubCommands().add(new MCPing(bot));
        getSubCommands().add(new MCName(bot));
        getSubCommands().add(new MCUUID(bot));
        getSubCommands().add(new MCStatus(bot));
        
    }
    
    @Override
    protected String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        return null;
    }
    
    private class MCPing extends BotCommand {
        
        public MCPing(Bot bot) {
            super(bot, "ping", "Ping a Minecraft server");
        }
        
        @Override
        protected String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
            if (args.length != 0) {
                String address = args[0];
                int port = 25565;
                if (args.length > 1) {
                    try {
                        port = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        return "Invalid port: " + args[1];
                    }
                }
                
                try {
                    MinecraftPingReply reply = new MinecraftPing().getPing(new MinecraftPingOptions().setHostname(address).setPort(port));
                    
                    StringBuilder re = new StringBuilder();
                    re.append(address).append(":").append(port).append(":\n");
                    re.append("Players: ").append(reply.getPlayers().getOnline()).append("/").append(reply.getPlayers().getMax()).append
                            ("\n");
                    re.append("MOTD:\n").append(BukkitChatFormat.getSkypeString(reply.getDescription()));
                    
                    
                    if (reply.getFavicon() != null) {
                        String base = reply.getFavicon().substring(reply.getFavicon().indexOf(",") + 1);
                        
                        byte[] decoded = Base64.getDecoder().decode(base);
                        String s = Imgur.uploadImage(BotMain.instance, decoded);    // I had to create that static just for this
                        re.append("\nFavicon: ").append(s);
                    }
                    
                    return re.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return "An unknown error occurred, try again later\n" + e.getMessage();
                }
            } else {
                return getUsage(command, "address");
            }
        }
    }
    
    private class MCName extends BotCommand {
        
        public MCName(Bot bot) {
            super(bot, "name", "Get a Minecraft username from a UUID");
        }
        
        @Override
        protected String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
            if (args.length != 0) {
                try {
                    UUID id = UUID.fromString(args[0]);
                    REST rest = new REST("https://sessionserver.mojang.com/session/minecraft/profile/" + id.toString().replace("-", ""));
                    rest.setPrintErrors(false);
                    JsonObject object = rest.getAsJsonObject();
                    if (rest.getResponseCode() != 429) {
                        return object.get("name").getAsString();
                    } else {
                        return "Connection throttled, try again later";
                    }
                } catch (IllegalArgumentException e) {
                    return "Invalid UUID";
                }
            } else {
                return getUsage(command, "name");
            }
        }
    }
    
    private class MCUUID extends BotCommand {
        
        public MCUUID(Bot bot) {
            super(bot, "uuid", "Get a Minecraft UUID from a username");
        }
        
        @Override
        protected String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
            if (args.length != 0) {
                REST rest = new REST("https://api.mojang.com/users/profiles/minecraft/" + args[0]);
                JsonObject object = rest.getAsJsonObject();
                String id = object.get("id").getAsString();
                return id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + 
                        id.substring(20, id.length());
            } else {
                return getUsage(command, "uuid");
            }
        }
    }
    
    private class MCStatus extends BotCommand {
    
        public MCStatus(Bot bot) {
            super(bot, "status", "Get Minecraft status");
        }
    
        @Override
        protected String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
            REST rest = new REST("http://status.mojang.com/check");
            JsonArray array = rest.getAsJsonObject(JsonArray.class);
            StringBuilder result = new StringBuilder("Minecraft Status" + "\n");
            for (JsonElement o : array) {
                Map.Entry<String, JsonElement> e = (Map.Entry<String, JsonElement>) o.getAsJsonObject().entrySet().toArray()[0];
                result.append(e.getKey()).append(": ").append(parseColor(e.getValue().getAsString())).append("\n");
            }
    
            return result.toString();
        }
    
        private String parseColor(String s) {
            switch (s) {
                case "green":
                    return "online";
                case "yellow":
                    return "may be experiencing issues";
                case "red":
                    return "offline";
                default:
                    return "error while fetching status";
            }
        }
    }
}
