package skypebot.types.skype;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.apache.commons.lang.builder.CompareToBuilder;
import skypebot.BotMain;
import skypebot.obj.bot.BotConfiguration;
import skypebot.types.skype.listener.SkypeChatListener;
import skypebot.types.skype.listener.SkypeContactAddListener;
import skypebot.types.skype.listener.SkypeConversationJoinListener;
import skypebot.util.DynamicClassLoader;
import skypebot.wrapper.Bot;
import skypebot.wrapper.BotCommand;
import skypebot.wrapper.BotUser;
import xyz.gghost.jskype.SkypeAPI;

import java.util.Collections;

/**
 * Created by Kyle on 8/31/2015.
 */
public class SkypeBot extends Bot {
    
    @Getter
    private SkypeAPI api;
    
    public SkypeBot(BotMain main) throws Exception {
        super(main, "Skype");
        
        BotConfiguration configuration = main.getConfiguration();
        JsonObject jsonObject = configuration.get("skype").getAsJsonObject();
        api = new SkypeAPI(jsonObject.get("user").getAsString(), jsonObject.get("pass").getAsString());
        api.login();
        
        api.getEventManager().registerListener(new SkypeChatListener(this, api, main.getChatListener()));
        api.getEventManager().registerListener(new SkypeConversationJoinListener(api, this, main.getConversationJoinListener()));
        api.getEventManager().registerListener(new SkypeContactAddListener(api));
        
        commands = new DynamicClassLoader<>(BotCommand.class, this).load();
        Collections.sort(commands, (o1, o2) -> new CompareToBuilder().append(o1.getLabel(), o2.getLabel()).toComparison());
    }
    
    @Override
    public BotUser getUser(String user) {
        return new SkypeBotUser(api.getUserByUsername(user), this);
    }
}
