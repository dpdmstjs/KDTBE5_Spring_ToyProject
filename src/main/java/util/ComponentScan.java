package util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import util.annotation.Controller;
import util.annotation.RequestMapping;

public class ComponentScan {
	private static ComponentScan componentScan;

	private ComponentScan() {

	}

	public static ComponentScan getInstance() {
		if (componentScan == null) {
			componentScan = new ComponentScan();
		}

		return componentScan;
	}

	public Set<Class> scanPackage(String pkg) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Set<Class> classes = new HashSet<>();

		URL packageUrl = classLoader.getResource(pkg);
		File packageDirectory = new File(packageUrl.toURI());
		for (File file : packageDirectory.listFiles()) {
			if (file.getName().endsWith(".class")) {
				String className = pkg + "." + file.getName().replace(".class", "");
				Class cls = Class.forName(className);
				classes.add(cls);
			}
		}
		return classes;
	}

	//@TODO: Depth 너무 깊음
	//@TODO: 입력값 파라미터 id도 비교해야됨
	public String findUri(Set<Class> classes, MethodInfo methodInfo) throws Exception {
		boolean isFind = false;
		for (Class cls : classes) {
			if (cls.isAnnotationPresent(Controller.class)) {
				Object instance = cls.newInstance();
				Method[] methods = cls.getDeclaredMethods();

				for (Method method : methods) {
					Annotation annotation = method.getDeclaredAnnotation(RequestMapping.class);
					RequestMapping requestMapping = (RequestMapping)annotation;

					if (requestMapping == null)
						continue;

					if (requestMapping.name().equals(methodInfo.getName())) {
						isFind = true;
						method.setAccessible(true);

						try {
							if (method.getParameterTypes().length == 0) {
								method.invoke(instance);
								break;
							}

							Class<?>[] parameterTypes = method.getParameterTypes();
							Object[] arg = new Object[parameterTypes.length];
							for (int i = 0; i < parameterTypes.length; i++) {
								arg[i] = convertArgumentType(methodInfo.getParameters()[i], parameterTypes[i]);
							}

							String response = (String)method.invoke(instance, arg);

							return response;
						} catch (Exception e) {
							e.printStackTrace();
						}

						break;
					}
				}
			}
		}

		return null;
	}

	private static Object convertArgumentType(Object arg, Class<?> targetType) {
		// 타입에 따라 적절한 변환 로직을 구현한다
		if (targetType.equals(int.class) || targetType.equals(Integer.class)) {
			return Integer.parseInt(arg.toString());
		} else if (targetType.equals(String.class)) {
			return arg.toString();
		} else if (targetType.equals(boolean.class) || targetType.equals(Boolean.class)) {
			return Boolean.parseBoolean(arg.toString());
		}
		// 추가적인 타입 변환 로직을 구현한다

		return arg;
	}
}
