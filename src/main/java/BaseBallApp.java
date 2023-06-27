import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import dao.StadiumDAO;
import dao.TeamDAO;
import db.DBConnection;
import util.MethodInfo;
import util.MyScanner;
import util.annotation.RequestMapping;
import util.annotation.Service;

public class BaseBallApp {
	public static void main(String[] args) throws Exception {
		Connection connection = DBConnection.getInstance();
		StadiumDAO stadiumDAO = new StadiumDAO(connection);
		TeamDAO teamDAO = new TeamDAO(connection);

		MyScanner scanner = new MyScanner();

		while (true) {
			String uri = scanner.getRequest();
			MethodInfo methodInfo = scanner.parseData(uri);
			Set<Class> classes = componentScan("service");
			findUri(classes, methodInfo);
		}

	}

	public static Set<Class> componentScan(String pkg) throws Exception {
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

	public static void findUri(Set<Class> classes, MethodInfo methodInfo) throws Exception {
		boolean isFind = false;
		for (Class cls : classes) {
			if (cls.isAnnotationPresent(Service.class)) {
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
							Class<?>[] parameterTypes = method.getParameterTypes();
							Object[] arg = new Object[parameterTypes.length];
							for (int i = 0; i < parameterTypes.length; i++) {
								arg[i] = convertArgumentType(methodInfo.getParameters()[i], parameterTypes[i]);
							}
							method.invoke(instance, arg);
						} catch (Exception e) {
							e.printStackTrace();
						}

						break;
					}
				}
			}
		}
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
