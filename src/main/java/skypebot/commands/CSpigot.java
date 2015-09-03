package skypebot.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import skypebot.wrapper.*;
import xyz.gghost.jskype.chat.Chat;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 8/26/2015.
 */
public class CSpigot extends BotCommand {

    private final String API_URL = "https://hub.spigotmc.org/javadocs/spigot/";
    @Getter
    private List<SpigotClass> classes;

    public CSpigot(Bot bot) {
        super(bot, "spigot", "Browse API docs");
        load();
    }

    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (classes == null || classes.isEmpty()) {
            return "Unable to load classes at this time please try again later or go to " + Chat.link(API_URL);
        }

        if (args.length != 0) {
            String c = args[0];
            SpigotClass spigotClass = classes.stream().filter(cl -> cl.getName().equalsIgnoreCase(c)).findAny().orElse(null);
            if (spigotClass != null) {
                return Chat.bold(spigotClass.getName()) + "\n" + spigotClass.getDescription() + "\n" + Chat.link(API_URL + spigotClass.getUrl());
            } else {
                return "Class not found";
            }
        } else {
            return "Usage: " + command + " class";
        }

    }

    private void load() {
        Document parse;
        try {
            parse = Jsoup.parse(new URL(API_URL + "allclasses-frame.html"), 5000);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        classes = new ArrayList<>();
        Element indexContainer = parse.getElementsByClass("indexContainer").get(0);
        for (Element e : indexContainer.getElementsByTag("a")) {
            String path = e.attr("href");
            String description = e.attr("title");
            String name = e.text();
            SpigotClass c = new SpigotClass(name, path, description);
            classes.add(c);
        }
    }

    @AllArgsConstructor
    @Data
    private static class SpigotClass {
        private final String name;
        private final String url;
        private final String description;
    }
}
