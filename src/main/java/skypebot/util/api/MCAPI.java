package skypebot.util.api;

import skypebot.util.api.apis.mc.MCNameExecutor;
import skypebot.util.api.apis.mc.MCPingServer;
import skypebot.util.api.apis.mc.MCStatusExecutor;
import skypebot.util.api.apis.mc.MCUUIDExecutor;
import skypebot.util.api.apis.mc.RestExecute;

/**
 * Created by Kyle on Jun 3, 2015
 */
public enum MCAPI {

	PING(new MCPingServer()),
	NAME(new MCNameExecutor()),
	UUID(new MCUUIDExecutor()),
	STATUS(new MCStatusExecutor());
	//ACCOUNT(new MCAccountExecutor());

	private final RestExecute execute;

	@SuppressWarnings("SpellCheckingInspection")
    MCAPI(RestExecute execute) {
		this.execute = execute;
	}

	public String getDescription() {
		return execute.getDescription();
	}

	public String getUsage() {
		return "!mc " + this.name() + " " + execute.getUsage();
	}

	public String get(String... variables) {
		REST rest = new REST(execute.getUrl(variables));
		return execute.execute(rest, variables);
	}
}
