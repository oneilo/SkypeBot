package skypebot.listener;

import skypebot.wrapper.BotUser;

/**
 * Created by Kyle on 9/10/2015.
 */
public class ContactAddListener {
    
    public void onAdd(BotUser user) {
        user.sendMessage("Thanks for adding me as a contact");
    }
}
