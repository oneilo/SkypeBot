package skypebot.util.api.apis.mc;

import skypebot.BotMain;
import skypebot.util.BukkitChatFormat;
import skypebot.util.api.Imgur;
import skypebot.util.api.REST;
import skypebot.util.api.apis.mcping.MinecraftPing;
import skypebot.util.api.apis.mcping.MinecraftPingOptions;
import skypebot.util.api.apis.mcping.MinecraftPingReply;

import java.util.Base64;

/**
 * Created by Kyle on Jun 3, 2015
 */
public class MCPingServer implements RestExecute {

	public String getUsage() {
		return "ip port";
	}

	public String getDescription() {
		return "Pings a Minecraft server";
	}

	public String getUrl(String[] args) {
		return "";
	}

	public String execute(REST con, String[] variables) {
		String address = variables[0];
		int port = 25565;
		if (variables.length > 1) {
			try {
				port = Integer.parseInt(variables[1]);
			} catch (Exception e) {
				return "Invalid port: " + variables[1];
			}
		}

		try {
			MinecraftPingReply reply = new MinecraftPing().getPing(new MinecraftPingOptions().setHostname(address).setPort(port));

			StringBuilder re = new StringBuilder();
			re.append(address).append(":").append(port).append(":\n");
			re.append("Players: ").append(reply.getPlayers().getOnline()).append("/").append(reply.getPlayers().getMax()).append("\n");
			re.append("MOTD:\n").append(BukkitChatFormat.getSkypeString(reply.getDescription()));


			if (reply.getFavicon() != null) {
				String base = reply.getFavicon().substring(reply.getFavicon().indexOf(",") + 1);

				byte[] decoded = Base64.getDecoder().decode(base);
				String s = Imgur.uploadImage(BotMain.instance, decoded);    // I had to create that static just for this
				re.append("\nFavicon: ").append(s);
			}

			return re.toString();
		} catch (Exception e) {
			e.printStackTrace();
            return "An unknown error occurred, try again later\n" + e.getMessage();
        }
	}
}
