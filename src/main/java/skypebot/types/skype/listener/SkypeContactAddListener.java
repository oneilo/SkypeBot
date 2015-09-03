package skypebot.types.skype.listener;

import lombok.AllArgsConstructor;
import xyz.gghost.jskype.api.SkypeAPI;
import xyz.gghost.jskype.api.event.EventListener;
import xyz.gghost.jskype.api.events.UserPendingContactRequestEvent;
import xyz.gghost.jskype.var.User;

/**
 * Created by Kyle on 8/26/2015.
 */
@AllArgsConstructor
public class SkypeContactAddListener implements EventListener {

    private SkypeAPI api;
    
    public void onUserAdd(UserPendingContactRequestEvent e) {
        User user = new User(e.getUser());
        api.getSkype().acceptContact(e.getUser());
        user.sendMessage(api , "Thanks for adding me! Too see a list of commands type -help");
    }
}
