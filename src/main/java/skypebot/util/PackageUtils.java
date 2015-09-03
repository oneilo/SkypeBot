package skypebot.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackageUtils {
	public static List<String> getClassesInPackage(String path) {
		List<String> c = new ArrayList();
		try {
			File jar = new File(PackageUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI());

			ZipFile zip = new ZipFile(jar);
			String start = path.replace(".", "/");
			Enumeration<? extends ZipEntry> s = zip.entries();
			while (s.hasMoreElements()) {
				ZipEntry p = s.nextElement();
				if (p.getName().startsWith(start) && p.getName().endsWith(".class")) {
					String pack = p.getName().substring(0, p.getName().lastIndexOf("/"));
					if (pack.replace("/", ".").equals(path)) {
						c.add(p.getName().replace("/", "."));
					}
				}
			}
			zip.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	public static List<Class<?>> getRecursiveClasses(String path, Class<?> example, String... exclude) {
		List<Class<?>> classes = new ArrayList();
		try {
			File jar = new File(example.getProtectionDomain().getCodeSource().getLocation().toURI());

			ZipFile zip = new ZipFile(jar);
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
