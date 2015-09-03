package skypebot.util.api.apis.mc;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import skypebot.util.Messages;
import skypebot.util.api.REST;

import java.util.Map.Entry;

/**
 * Created by Kyle on Jun 3, 2015
 */
public class MCStatusExecutor implements RestExecute {

	public String execute(REST con, String[] vars) {
		JsonArray array = con.getAsJsonObject(JsonArray.class);

		StringBuilder result = new StringBuilder(Messages.getTitleFormat("Minecraft Status") + "\n");
		for (JsonElement o : array) {
			Entry<String, JsonElement> e = (Entry<String, JsonElement>) o.getAsJsonObject().entrySet().toArray()[0];
			result.append(e.getKey()).append(": ").append(parseColor(e.getValue().getAsString())).append("\n");
		}

		return result.toString();
	}

	private static String parseColor(String s) {
        switch (s) {
            case "green":
                return "online";
            case "yellow":
                return "may be experiencing issues";
            case "red":
                return "offline";
            default:
                return "error while fetching status";
        }
	}

	public String getUsage() {
		return "";
	}

	public String getUrl(String[] args) {
		return "http://status.mojang.com/check";
	}

	public String getDescription() {
		return "Get Minecraft status";
	}
}
