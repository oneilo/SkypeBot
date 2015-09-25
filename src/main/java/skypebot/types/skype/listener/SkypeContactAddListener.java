package skypebot.types.skype.listener;

import lombok.AllArgsConstructor;
import xyz.gghost.jskype.SkypeAPI;
import xyz.gghost.jskype.event.EventListener;
import xyz.gghost.jskype.events.UserPendingContactRequestEvent;
import xyz.gghost.jskype.user.User;

/**
 * Created by Kyle on 8/26/2015.
 */
@AllArgsConstructor
public class SkypeContactAddListener implements EventListener {

    private SkypeAPI api;
    
    public void onUserAdd(UserPendingContactRequestEvent e) {
        User userAccount = api.getContact(e.getUser());
        userAccount.getGroup(api).sendMessage("Thanks for adding me! Too see a list of commands type -help");
        userAccount.sendContactRequest(api);
    }
}
