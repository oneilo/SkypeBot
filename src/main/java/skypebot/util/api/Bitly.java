package skypebot.util.api;

import net.swisstech.bitly.BitlyClient;
import skypebot.BotMain;

/**
 * Created by Kyle on 8/25/2015.
 */
public class Bitly {

    private static BitlyClient client;

    public static String shorten(BotMain main, String url) {
        if (client == null) {
            String key = main.getConfiguration().get("api").getAsJsonObject().get("bitly").getAsString();
            client = new BitlyClient(key);
        }
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        return client.shorten().setLongUrl(url).call().data.url;
    }
}
