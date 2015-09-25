package skypebot;

import lombok.Getter;
import skypebot.listener.ChatListener;
import skypebot.listener.ConversationJoinListener;
import skypebot.obj.ChatMeta;
import skypebot.obj.bot.BotConfiguration;
import skypebot.obj.bot.BotExecutor;
import skypebot.obj.bot.BotProperties;
import skypebot.obj.bot.BotStats;
import skypebot.permissions.GlobalPermissions;
import skypebot.types.skype.SkypeBot;
import skypebot.wrapper.Bot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class BotMain {
    
    @Deprecated
    public static BotMain instance; // I didn't want to do this, will fix later
    
    @Getter
    private BotProperties properties;
    @Getter
    private BotConfiguration configuration;
    @Getter
    private BotExecutor executor;
    @Getter
    private BotStats stats;
    @Getter
    private AtomicBoolean running;
    @Getter
    private List<Bot> runningBots;
    @Getter
    private ChatListener chatListener;
    @Getter
    private ConversationJoinListener conversationJoinListener;
    @Getter
    private GlobalPermissions globalPermissions;
    
    public static void main(String[] args) throws Exception {
        BotMain bot = new BotMain();
        instance = bot;
        bot.init();
    }
    
    private void init() throws Exception {
        running = new AtomicBoolean(true);
        System.out.println("Getting properties");
        properties = new BotProperties();
        System.out.println("Loading " + properties.getName() + " version " + properties.getVersion());
        
        System.out.println("Loading bot configuration");
        configuration = new BotConfiguration(new File("config.json"), true);
        
        ChatMeta.initChatMeta(this);
    
        System.out.println("Loading global permissions...");
        globalPermissions = new GlobalPermissions();
    
        System.out.println("Loading stats");
        stats = BotStats.getStats(new File("stats.json"));
        stats.setStartTime(System.currentTimeMillis());
        
        chatListener = new ChatListener(this, globalPermissions);
        executor = new BotExecutor();
        
        startCommandListener();
        
        runningBots = new ArrayList<>();
        
        System.out.println("Loading Skype...");
        runningBots.add(new SkypeBot(this));
        
        System.out.println("Done");
        
        executor.runLaterSync(() -> shutdown(0), 1, TimeUnit.DAYS);
        
        while (running.get()) {
            try {
                executor.tick();
                Thread.sleep(1);
            } catch (Exception ignored) {
            }
        }
    }
    
    public void shutdown(int code) {
        System.out.println("Saving stats");
        stats.save();
        System.out.println("No longer running");
        running.set(false);
        System.out.println("Saving chat meta");
        ChatMeta.saveAllMeta();
        System.out.println("Exiting");
        System.exit(code);
        System.out.println("Done");
    }
    
    public void startCommandListener() {
        new Thread(() -> {
            Scanner s = new Scanner(System.in);
            while (running.get()) {
                String line = s.nextLine().toLowerCase();
                if (line.equals("stop")) {
                    System.out.println("Stopping the server...");
                    shutdown(0);
                } else {
                    System.out.println("Unknown command, acceptable commands: stop");
                }
            }
        }).start();
    }
}
