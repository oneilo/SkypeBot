package skypebot.poller;

import skypebot.BotMain;

import java.util.concurrent.TimeUnit;

/**
 * Created by Kyle on 8/31/2015.
 */
public abstract class Poller {
    
    protected String lastState;
    private long interval;
    protected BotMain main;
    
    public Poller(long interval, BotMain main) {
        this.interval = interval;
        this.main = main;
        main.getExecutor().runLaterAsync(() -> check(), interval, TimeUnit.MILLISECONDS);
        
    }
    
    public void check() {
        String state = run();
        
        if (lastState == null) {
            lastState = state;
        } else {
            if (state != lastState) {
                onUpdate(lastState, state);
                lastState = state;
            } 
        }
    }
    
    public abstract void onUpdate(String old, String newState);
    
    public abstract String run();
    
}
