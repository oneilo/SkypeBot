package skypebot.commands;

import com.google.gson.Gson;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import skypebot.wrapper.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class CGoogle extends BotCommand {

	public CGoogle(Bot bot) {
		super(bot, "google", "Google stuff");
	}

    @Data
	public class GoogleResults {
		private ResponseData responseData;
	}

    @Data
	class Result {
		private String url;
		private String title;
	}

    @Data
	class ResponseData {
		private List<Result> results;
	}

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		if (args.length == 0) {
			return "Usage: " + command + " [search term]";
		}

		String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
		String charset = "UTF-8";

		try {
			URL url = new URL(google + URLEncoder.encode(StringUtils.join(args, ' ').trim(), charset));
			Reader reader = new InputStreamReader(url.openStream(), charset);
			GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
			// Show title and URL of 1st result.

            return results.getResponseData().getResults().get(0).getTitle().replaceAll("<.+>", "") +
                    results.getResponseData().getResults().get(0).getUrl().replaceAll("<.+>", "");
		} catch (IOException e) {
			e.printStackTrace();
            return "An error occurred while performing this command, please try again later";
		}
	}
}
