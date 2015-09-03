package skypebot.util.api;

import com.google.gson.JsonObject;
import skypebot.BotMain;

import java.net.URLEncoder;
import java.util.Base64;

/**
 * Created by Kyle on Jun 3, 2015
 */
public class Imgur {

	public static String uploadImage(BotMain main, byte[] bytes) throws Exception {
		String data = URLEncoder.encode("image", "UTF-8") + "="
				+ URLEncoder.encode(Base64.getEncoder().encodeToString(bytes), "UTF-8");
		REST con = new REST("https://api.imgur.com/3/image");
		con.setRequest("POST");
        String key = main.getConfiguration().get("api").getAsJsonObject().get("imgur").getAsString();
        con.setRequestProperty("Authorization", "Client-ID " + key);
		con.setPayload(data);

		JsonObject o = con.getAsJsonObject();
		return o.getAsJsonObject("data").get("link").getAsString();
	}
}
