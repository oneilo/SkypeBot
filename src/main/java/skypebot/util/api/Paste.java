package skypebot.util.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import skypebot.BotMain;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Kyle on May 21, 2015
 */
public class Paste {
	public static String postString(BotMain main, String post) {
		String ret = "ERROR";
		try {
			byte[] postData = post.getBytes(Charset.forName("UTF-8"));
            
            String url = main.getConfiguration().get("api").getAsJsonObject().get("haste-url").getAsString();
            
			HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "text/plain");
			con.setRequestProperty("charset", "utf-8");
			con.setRequestProperty("Content-Length", Integer.toString(postData.length));

			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.write(postData);

			InputStream in = con.getInputStream();

			int i;
			String s = "";
			while ((i = in.read()) != -1) {
				s += (char) i;
			}
			JsonElement e = new JsonParser().parse(s);
			String f = e.getAsJsonObject().getAsJsonPrimitive("key").getAsString().replace("\"", "");
			ret = url.substring(0, url.lastIndexOf("/")+1) + f + ".hs";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
