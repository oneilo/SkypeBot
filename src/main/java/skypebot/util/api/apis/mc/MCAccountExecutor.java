package skypebot.util.api.apis.mc;

import skypebot.util.api.REST;

/**
 * Created by Kyle on Jun 3, 2015
 */
public class MCAccountExecutor implements RestExecute {

	public String execute(REST con, String[] variables) {
		boolean valid = canLogin(con, variables[0], variables[1]);
		if (valid) {
			return "successfully authenticated";
		} else {
			return "authentication failed";
		}
	}

	private boolean canLogin(REST con, String email, String pass) {
		String payload = "{\"username\": \"" + email + "\",\r\n" + "\"password\": \"" + pass + "\",\r\n"
				+ "\"agent\": {\"name\": \"Minecraft\",\"version\": 1}}";
		con.setResult("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setPayload(payload);
		con.setPrintErrors(false);
		con.fetch();
		return con.getError() == null;
	}

	public String getUsage() {
		return "email pass";
	}

	public String getUrl(String[] args) {
		return "https://authserver.mojang.com/authenticate";
	}

	public String getDescription() {
		return "Check if a Minecraft login/password is valid";
	}
}
