package skypebot.util.api.apis.mc;

import skypebot.util.api.REST;

import com.google.gson.JsonObject;

/**
 * Created by Kyle on Jun 3, 2015
 */
public class MCUUIDExecutor implements RestExecute {

	public String execute(REST con, String[] vars) {
		JsonObject object = con.getAsJsonObject();
		String id = object.get("id").getAsString();
		return addHiphens(id);
	}

	private String addHiphens(String id) {
		return id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-"
				+ id.substring(20, id.length());
	}

	public String getUsage() {
		return "name";
	}

	public String getUrl(String[] args) {
		return "https://api.mojang.com/users/profiles/minecraft/" + args[0];
	}

	public String getDescription() {
		return "Get a Minecraft UUID from name";
	}
}
