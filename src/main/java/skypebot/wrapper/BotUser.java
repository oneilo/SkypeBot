package skypebot.wrapper;

/**
 * Created by Kyle on 8/31/2015.
 */
public interface BotUser extends BotImplement{

    String getUsername();
    
    BotMessage sendMessage(String message);

    Object getHandle();
    
}
