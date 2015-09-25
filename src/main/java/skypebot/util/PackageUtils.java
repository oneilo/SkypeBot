package skypebot.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackageUtils {
    
    // I hate myself for this
    private static File getJar() {
        File dir = new File(".");
        for (File f : dir.listFiles()) {
            if (f.getName().equalsIgnoreCase("SkypeBot.jar")) {
                return f;
            }
        }
        return null;
    }
    
	public static List<Class<?>> getRecursiveClasses(String path, String... exclude) {
		List<Class<?>> classes = new ArrayList();
		try {
			ZipFile zip = new ZipFile(getJar());
			Enumeration<? extends ZipEntry> s = zip.entries();
			while (s.hasMoreElements()) {
				to: {
					ZipEntry p = s.nextElement();
					String absPath = p.getName().replace("/", ".");

					for (String string : exclude) {
						if (absPath.contains(string)) {
							break to;
						}
					}

					if (absPath.startsWith(path) && p.getName().endsWith(".class")) {
						if (!absPath.contains("$")) {
							classes.add(Class.forName(absPath.replace(".class", "")));
						}
					}
				}
			}
			zip.close();
		} catch (NoClassDefFoundError | Exception e) {
			e.printStackTrace();
		}
        return classes;
	}
}
