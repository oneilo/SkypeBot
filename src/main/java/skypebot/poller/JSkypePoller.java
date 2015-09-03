package skypebot.poller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import skypebot.BotMain;
import skypebot.types.skype.SkypeBot;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kyle on 8/31/2015.
 */
public class JSkypePoller extends Poller {
    
    private final String SKYPE_ID = "3000ebdcfcca4b42b9f6964f4066e1ad";
    
    private SkypeBot bot;
    
    public JSkypePoller(BotMain main) {
        super(TimeUnit.MINUTES.toMillis(5), main);
    }
    
    @Override
    public void onUpdate(String old, String newState) {
        if (bot == null) {
            bot = (SkypeBot) main.getRunningBots().stream().filter(b->b.getName().equalsIgnoreCase("Skype")).findAny().get();
        }
    
        bot.getConversation(SKYPE_ID).sendMessage("jSkype has updated from " + old + " to " + newState);
    }
    
    @Override
    public String run() {
        try {
            Document doc = Jsoup.parse(new URL("http://gghost.xyz/maven/xyz/gghost/jskype/"), 5000);
            Element e = null;
            Max max = null;
            for (Element element : doc.getElementsByTag("pre").get(0).getElementsByTag("a")) {
                String text = element.text();
                try {
                    int a = Integer.parseInt(text.substring(0, 1));
                    int b = Integer.parseInt(text.substring(2, text.indexOf("-")));
                    
                    if (max == null) {
                        max = new Max(a, b, element);
                    } else {
                        if (a > max.getA()) {
                            max = new Max(a, b, element);
                        } else if (b > max.getB()) {
                            max = new Max(a, b, element);
                        }
                    }
                } catch (NumberFormatException e1) {
                }
            }
            return max.getE().text();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @AllArgsConstructor
    @Data
    class Max {
        private int a;
        private int b;
        private Element e;
    }
}
