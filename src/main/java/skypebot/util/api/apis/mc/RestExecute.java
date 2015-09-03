package skypebot.util.api.apis.mc;

import skypebot.util.api.REST;

public interface RestExecute {
	
    String getUsage();

	String getDescription();

	String getUrl(String[] args);

	String execute(REST con, String[] variables);
}