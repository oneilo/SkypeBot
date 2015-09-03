package skypebot.wrapper;

import lombok.Getter;
import skypebot.BotMain;
import skypebot.obj.bot.BotExecutor;
import skypebot.obj.bot.BotStats;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 8/31/2015.
 */
public abstract class Bot {
    
    @Getter
    protected final BotMain main;   
    @Getter
    protected List<BotCommand> commands;
    @Getter
    protected final String name;
    
    public Bot(BotMain main, String name) {
        this.main = main;
        this.name = name;
        this.commands = new ArrayList<>();
    }
    
    public BotExecutor getExecutor() {
        return main.getExecutor();
    }
    
    public BotStats getStats() {
        return main.getStats();
    }
    
    public abstract BotUser getUser(String user);
}
