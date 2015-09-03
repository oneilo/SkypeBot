package skypebot.util;

import lombok.Cleanup;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectUtils {

	public static <T> T load(File f) {
		try {
			@Cleanup
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
			Object o = in.readObject();
			return (T) o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> List<T> getObjects(File f) {
		try {
			T o;
			List<T> objects = new ArrayList<>();
			@Cleanup
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
			while ((o = (T) in.readObject()) != null) {
				objects.add(o);
			}
			return objects;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveObjects(File file, Object... obj) {
		try {
			@Cleanup
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
			for (Object o : obj) {
				os.writeObject(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
