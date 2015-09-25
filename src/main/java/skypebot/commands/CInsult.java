package skypebot.commands;

import skypebot.wrapper.*;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kyle on May 26, 2015
 */
public class CInsult extends BotCommand {

	private final Pattern pattern = Pattern.compile("<div class=\"wrap\"><br><br>( +)?([?()a-zA-Z #0-9.!,&;'-]+)");
    
    public CInsult(Bot bot) {
		super(bot, "insult", "Generate an insult");
	}

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		BotMessage message = chat.sendMessage("Generating");
        try {
			message.edit(getInsult(getHtml()));
		} catch (Exception e) {
            message.edit("Error grabbing insult, try again later\n" + e.getMessage());
			e.printStackTrace();
		}
        return null;
	}

	private StringBuilder getHtml() throws Exception {
        String site = "http://www.insultgenerator.org/";
        HttpURLConnection con = (HttpURLConnection) new URL(site).openConnection();
		con.setDoOutput(true);
		BufferedInputStream in = new BufferedInputStream(con.getInputStream());
		StringBuilder b = new StringBuilder();
		int i;
		while ((i = in.read()) != -1) {
			if (i != 10) {
				b.append((char) i);
			}
		}
		return b;
	}

	private String getInsult(StringBuilder s) {
		Matcher m = pattern.matcher(s);
		String match = "null";
		while (m.find()) {
			match = m.group();
		}
		match = match.replace("&nbsp;", " ");
		return match.substring(26, match.length());
	}
}
