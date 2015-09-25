package skypebot.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.NonNull;
import org.apache.commons.lang.StringUtils;
import skypebot.util.api.REST;
import skypebot.wrapper.*;
import xyz.gghost.jskype.Chat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Kyle on 9/16/2015.
 */
public class CPH extends BotCommand {
    
    public CPH(Bot bot) {
        super(bot, "ph", "Idk what this does");
        getSubCommands().add(new PHSearch(bot));
        getSubCommands().add(new PHInfo(bot));
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        return null;
    }
    
    private class PHSearch extends BotCommand {
    
        public PHSearch(Bot bot) {
            super(bot, "search", "search for something");
        }
    
        @Override
        protected String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
            if (args.length != 0) {
                String search = StringUtils.join(args, ' ', 1, args.length);
                try {
                    return getSearchResults(search);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return "An error occurred, try again later\n" + e.getMessage();
                }
            } else {
                return getUsage(command, "string");
            }
        }
    }
    
    private class PHInfo extends BotCommand {
    
        public PHInfo(Bot bot) {
            super(bot, "info", "Get video information");
        }
    
        @Override
        protected String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
            if (args.length != 0) {
                try {
                    return getInfo(args[1]);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return "An error occurred, try again later\n" + e.getMessage();
                }    
            } else {
                return getUsage(command, "id");
            }
        }
    }
    
    public String getInfo(String id) throws UnsupportedEncodingException {
        REST rest = new REST("http://www.pornhub.com/webmasters/video_by_id?id=" + id);
        JsonObject object = rest.getAsJsonObject();
        if (object.has("code")) {
            return object.get("message").getAsString();
        } else {
            return getInfo(object.get("video").getAsJsonObject());
        }
    }
    
    public String getInfo(@NonNull JsonObject jsonObject) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        
        String url = jsonObject.get("url").getAsString();
        String title = jsonObject.get("title").getAsString();
        
        builder.append("<a href=\"").append(url).append("\">").append(title).append("</a>").append("\n");
    
        JsonArray stars = jsonObject.getAsJsonArray("pornstars");
        
        if (stars.size() != 0) {
            builder.append(Chat.bold("Featuring: "));
            for (Iterator<JsonElement> iterator = stars.iterator(); iterator.hasNext(); ) {
                JsonElement star = iterator.next();
                String name = star.getAsJsonObject().get("pornstar_name").getAsString();
                builder.append("<a href=\"http://www.pornhub.com/pornstar/").append(URLEncoder.encode(name, "UTF-8")).append("\">").append(name)
                        .append("</a>");
                if (iterator.hasNext()) {
                    builder.append(", ");
                }
            }
            builder.append("\n");
        }
    
        System.out.println(jsonObject);
        
        int views = jsonObject.get("views").getAsInt();
        int ratings = jsonObject.get("ratings").getAsInt();
        double rating = jsonObject.get("rating").getAsDouble()/100;
        
        int like = (int) (ratings * rating);
        int dislike = ratings-like;
    
        builder.append("Views: ").append(views).append("    -   ").append(like).append("(y)  ").append(dislike).append("(n)\n");
        return builder.toString();
    }
    
    public String getSearchResults(String search) throws UnsupportedEncodingException {
    
        REST rest = new REST("http://www.pornhub.com/webmasters/search?search=" + URLEncoder.encode(search, "UTF-8"));
        JsonObject restAsJsonObject = rest.getAsJsonObject();
        
        if (restAsJsonObject.has("code")) {
            return restAsJsonObject.get("message").getAsString();
        }
        
        JsonArray jsonArray = restAsJsonObject.getAsJsonArray("videos");
        
        StringBuilder builder = new StringBuilder(search);
        builder.append("\n");
        if (jsonArray.size() != 0) {
            for (int i = 0; i < 3 && i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                String url = jsonObject.get("url").getAsString();
                String title = jsonObject.get("title").getAsString();
                builder.append("<a href=\"").append(url).append("\">").append(Chat.encodeRawText(title)).append("</a>");
                if (i+1 < 3 && i+1 < jsonArray.size()) {
                    builder.append("\n");
                }
            }
        } else {
            builder.append("no search results found");
        }
        return builder.toString();
    }
}
