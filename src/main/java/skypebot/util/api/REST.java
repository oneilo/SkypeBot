package skypebot.util.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import skypebot.BotMain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Kyle on Jun 3, 2015
 */
public class REST {

	private static final Gson gson = new Gson();

	private String url;
	private String result;
	private String request;
	private String payload;
	private final Map<String, String> requestProperities;
	private Exception error;
	private boolean printErrors;
	private int responseCode;

	public REST(String url) {
		this.url = url;
		this.request = "GET";
		this.requestProperities = new HashMap<>();
		this.printErrors = true;
	}
    
    public REST(String url, String encode) throws UnsupportedEncodingException {
        this(url + URLEncoder.encode(encode, "UTF-8"));
    }

	public String getUrl() {
		return url;
	}

	public void setPrintErrors(boolean printErrors) {
		this.printErrors = printErrors;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setResult(String result) {
		this.request = result;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getAsString() {
		fetch();
		return result;
	}

	public <T> T getAsJsonObject(Class c) {
		fetch();
		return (T) gson.fromJson(result, c);
	}

	public String getAsCroppedURL(int lengthCut) {
		fetch();
		StringBuilder builder = new StringBuilder(result);
		int column = 0;
		for (int i = 0; i < builder.length(); i++) {
			if (column > lengthCut && builder.charAt(i) == ' ') {
				builder.setCharAt(i, '\n');
				column = 0;
			}
			column++;
		}
		return Paste.postString(BotMain.instance, builder.toString().replace("<br>", "\n"));
	}

	public void setRequestProperty(String key, String value) {
		requestProperities.put(key, value);
	}

	public JsonObject getAsJsonObject() {
		fetch();
		return gson.fromJson(result, JsonObject.class);
	}

	public void fetch() {
		if (result == null) {
			HttpURLConnection con = null;
			try {
				con = (HttpURLConnection) new URL(url).openConnection();
				con.setDoOutput(true);
				con.setRequestMethod(request);

				for (Entry<String, String> e : requestProperities.entrySet()) {
					con.setRequestProperty(e.getKey(), e.getValue());
				}

				con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");

				if (payload != null) {
					con.setDoInput(true);
					con.getOutputStream().write(payload.getBytes());
				}

				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

				String inputLine;
				StringBuilder b = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					b.append(inputLine);
				}
				in.close();
				result = b.toString();
			} catch (Exception e) {
				this.error = e;
				if (printErrors) {
					e.printStackTrace();
				}
			}
			try {
				if (con != null) {
					responseCode = con.getResponseCode();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Exception getError() {
		return error;
	}
}
