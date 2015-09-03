package skypebot.util.api;

import java.io.IOException;

public enum CLAPI {

	RESOLVE("skyperesolver", "username", "Resolve by username"),
	RESOLVEDB("resolvedb", "username", "Resolve from database"),
	IPTOSKYPE("ip2skype", "ip", "IP to types"),
	GETEMAIL("email2skype", "email", "Email to types"),
	PING("ping", "host", "Ping an address"),
	GETHOST("gethostname", "host", "IP to hostname"),
	DNSRESOLVE("dnsresolver", "host", "Hostname to IP"),
	WHOIS("whois", "domain", "Whois a domain"),
	SCREENSHOT("createscreenshot", "url", "Get a screenshot of a site"),
	GEOIP("geoip", "host", "IP geographical data"),
	SITESTATUS("upordown", "host", "Check if a website is online"),
	URLBACKUP("linkbackup", "url", "Create a backup of a URL"),
	DEADFLY("deadfly", "url", "Bypass adfly"),
	DICTIONARY("dictionary", "word", "Define a word"),
	EMAIL("emailvalidatorexternal", "email", "Check if an email address is valid"),
	IP("ipvalidator", "ip", "Check if an ip is valid"),
	PHONE("phonechecker", "number", "Get phone number info"),
	RANDOMINFO("randomperson", "", "Make up random info"),
	HTMLPASTE("htmlpaste", "content", "Pastes html to a new page");

	private static final String URL = "http://api.c99.nl/";

	private final String append;
	private final String key;
	private final String description;

	@SuppressWarnings("SpellCheckingInspection")
    CLAPI(String append, String key, String description) {
		this.append = append + ".php?";
		this.key = key;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getKey() {
		return key;
	}

	public String getURL(String apiKey, String value) {
        String url = appendOption(appendOption(URL + append, "key", apiKey), key, value);
        System.out.println("Getting " + url);
        return url;
	}

	public REST getValue(String apiKey, String value) throws IOException {
		return new REST(getURL(apiKey, value));
	}

	public boolean isRequireValue() {
		return !key.isEmpty();
	}

	private static String appendOption(String url, String key, String value) {
		if (!key.isEmpty()) {
			return (url.endsWith("?") ? "" : "&") + key + "=" + value;
		} else {
			return url;
		}
	}
}
