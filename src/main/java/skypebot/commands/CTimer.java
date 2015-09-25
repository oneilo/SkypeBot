package skypebot.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import skypebot.util.TimeBuilder;
import skypebot.wrapper.*;

import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kyle on May 26, 2015
 */
public class CTimer extends BotCommand {

    private final long MAX_TIME = TimeUnit.HOURS.toMillis(1);

	private final ConcurrentHashMap<MessageData, Long> timers;

	public CTimer(Bot bot) {
		super(bot, "timer", "Start a timer");
		timers = new ConcurrentHashMap<>();
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
                for (Entry<MessageData, Long> e : timers.entrySet()) {
                    long time = e.getValue() - System.currentTimeMillis();
                    if (time > 0) {
                        e.getKey().getMessage().edit(TimeBuilder.fromLong(time));
                    } else {
                        e.getKey().getMessage().edit("Complete");
                        cancel();
                    }
                }
			}
		}, 700, 700);
	}

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		
        if (timers.entrySet().stream().filter(e->e.getKey().getConversation().getId().equals(chat.getId())).findAny().isPresent()) {
            return "There is already a timer running in this chat";
        }
        
        if (args.length == 0) {
			return "Usage: " + command + " [d h m s]";
		} else {
			String timeString = StringUtils.join(args, ' ');
            if (timeString.contains("-")) {
                return "Invalid time";
            }
			long time = new TimeBuilder(timeString).buildTime();
			if (time <= 0) {
				return "Invalid time!";
			} else {
                if (time <= MAX_TIME) {
                    BotMessage message = chat.sendMessage("Starting");
                    timers.put(new MessageData(message, sender, chat), time + System.currentTimeMillis());
                    return null;
                } else {
                    return "Timer cannot exceed 1 hour";
                }
			}
		}
	}

    @Data
    @AllArgsConstructor
    class MessageData {
        private BotMessage message;
        private BotUser user;
        private BotConversation conversation;
    }
}
