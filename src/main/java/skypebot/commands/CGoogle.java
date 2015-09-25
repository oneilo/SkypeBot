package skypebot.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import skypebot.util.api.REST;
import skypebot.wrapper.*;

import java.io.UnsupportedEncodingException;

public class CGoogle extends BotCommand {
    
    public CGoogle(Bot bot) {
        super(bot, "google", "Google stuff");
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (args.length != 0) {
            try {
                String search = StringUtils.join(args, ' ');
                String URL = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
                REST rest = new REST(URL, search);
                JsonObject object = rest.getAsJsonObject();
                int response = object.get("responseStatus").getAsInt();
                
                if (response == 200) {
                    JsonArray responses = object.getAsJsonObject("responseData").getAsJsonArray("results");
                    if (responses.size() > 0) {
                        StringBuilder builder = new StringBuilder(search);
                        for (int i = 0; i < 3 && i < responses.size(); i++) {
                            JsonObject result = responses.get(i).getAsJsonObject();
                            String url = result.get("url").getAsString();
                            String title = result.get("title").getAsString();
                            builder.append("\n").append("<a href=\"").append(url).append("\">").append(Jsoup.parse(title).text()).append
                                    ("</a>");
                        }
                        return builder.toString();
                    } else {
                        return "No results found";
                    }
                } else {
                    return "Unknown response code " + response + "\n" + object.get("responseDetails").getAsString();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        } else {
            return getUsage(command, "search");
        }
    }
}
