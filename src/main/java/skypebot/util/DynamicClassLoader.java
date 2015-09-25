package skypebot.util;

import lombok.Getter;
import skypebot.wrapper.Bot;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on May 16, 2015
 */
public class DynamicClassLoader<T> {

	private final Class type;
    @Getter
	private final List<T> objects;
    private final Bot main;
    
	public DynamicClassLoader(Class<T> type, Bot instance) {
		this.type = type;
        this.main = instance;
		objects = new ArrayList<>();
	}

	public List<T> load() {
        for (Class<?> c : PackageUtils.getRecursiveClasses("skypebot.commands")) { // get all classes in package
			try {
				DynamicClassLoader.class.getClassLoader().loadClass(c.getCanonicalName());
				if (type.isAssignableFrom(c)) {
                    Constructor<T> constructor = (Constructor<T>) c.getConstructors()[0];
                    T instance;
                    if (constructor.getParameterCount() == 0) {
                        instance = constructor.newInstance();
                    } else if (constructor.getParameterCount() == 1 && constructor.getParameters()[0].getType().equals(Bot.class)){
                        instance = constructor.newInstance(main);
                    } else {
                        System.err.println("Error loading class " + c.getCanonicalName() + " no valid constructor found");
                        continue;
                    }
					objects.add(instance);
                } else {
					System.err.println("Could not load class : " + c.getCanonicalName());
				}
			} catch (ClassNotFoundException e) {
				System.err.println("Could not load class : " + c.getCanonicalName() + " due to invalid dependencies "
						+ e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println("Error while loading class " + (c != null ? c.getName() : "null class?"));
				e.printStackTrace();
			}
		}
		return objects;
	}
}
