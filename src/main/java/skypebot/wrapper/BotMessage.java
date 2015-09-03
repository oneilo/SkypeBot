package skypebot.wrapper;

/**
 * Created by Kyle on 8/31/2015.
 */
public interface BotMessage extends BotImplement {
    
    void edit(String text);
    
    BotUser getSender();
    
    String getContent();
    
    Object getHandle();
    
}
