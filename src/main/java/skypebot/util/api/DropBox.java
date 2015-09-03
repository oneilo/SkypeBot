package skypebot.util.api;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWriteMode;
import skypebot.BotMain;

import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;

/**
 * Created by Kyle on May 23, 2015
 */
public class DropBox {

	public static String upload(BotMain main, File inputFile) {
		try {
            String token = main.getConfiguration().get("api").getAsJsonObject().get("dropbox-token").getAsString();
			DbxRequestConfig config = new DbxRequestConfig("SkypeBotHost", Locale.getDefault().toString());
			DbxClient client = new DbxClient(config, token);
			FileInputStream inputStream = new FileInputStream(inputFile);
			client.uploadFile("/" + inputFile.getName(), DbxWriteMode.force(), inputFile.length(), inputStream);

			return client.createShareableUrl("/" + inputFile.getName());
		} catch (Exception e) {
			System.err.println("Error while uploading file: " + inputFile.getAbsolutePath());
			e.printStackTrace();
		}
		return null;
	}
}
