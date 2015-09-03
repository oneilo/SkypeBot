package skypebot.util.api.apis.mc;

import skypebot.util.api.REST;

import com.google.gson.JsonObject;

/**
 * Created by Kyle on Jun 3, 2015
 */
public class MCNameExecutor implements RestExecute {

	public String execute(REST con, String[] variables) {
		con.setUrl(con.getUrl().replace("-", ""));
		con.setPrintErrors(false);
		JsonObject object = con.getAsJsonObject();
		if (con.getResponseCode() == 429) {
			return "Connection throttled, try again later";
		}
		return object.get("name").getAsString();
	}

	public String getUsage() {
		return "uuid";
	}

	public String getUrl(String[] args) {
		return " https://sessionserver.mojang.com/session/minecraft/profile/" + args[0];
	}

	public String getDescription() {
		return "Get a Minecraft username from a UUID";
	}
}
