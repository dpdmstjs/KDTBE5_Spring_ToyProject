package util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import exception.ArgumentMismatchException;
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

	private Set<Class> scanPackage(String pkg) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Set<Class> classes = new HashSet<>();

		URL packageUrl = classLoader.getResource(pkg);
		File packageDirectory = new File(packageUrl.toURI());
		for (File file : packageDirectory.listFiles()) {
			if (!file.getName().endsWith(".class"))
				continue;

			String className = pkg + "." + file.getName().replace(".class", "");
			Class cls = Class.forName(className);
			classes.add(cls);
		}
		return classes;
	}

	public String invokeMethod(MethodInfo methodInfo) {
		try {
			Set<Class> classes = scanPackage("controller");

			for (Class cls : classes) {
				if (!cls.isAnnotationPresent(Controller.class))
					continue;

				Object instance = cls.newInstance();
				Method[] methods = cls.getDeclaredMethods();

				Method method = findMethod(instance, methods, methodInfo);

				if (method == null)
					continue;

				method.setAccessible(true);

				if (method.getParameterTypes().length == 0) {
					String response = (String)method.invoke(instance);

					return response;
				}

				Object[] arguments = createMethodArguments(method, methodInfo);

				String response = (String)method.invoke(instance, arguments);

				return response;
			}

			return null;
		} catch (ArgumentMismatchException e) {
			return e.getMessage();
		} catch (Exception e) {
			return "실행 중 오류가 발생했습니다.";
		}
	}

	private Method findMethod(Object instance, Method[] methods, MethodInfo methodInfo) {
		for (Method method : methods) {
			if (!isEquals(method, methodInfo.getName()))
				continue;

			return method;
		}

		return null;
	}

	private boolean isEquals(Method method, String methodName) {
		Annotation annotation = method.getDeclaredAnnotation(RequestMapping.class);
		RequestMapping requestMapping = (RequestMapping)annotation;

		if (requestMapping == null || !requestMapping.name().equals(methodName))
			return false;

		return true;
	}

	private Object convertParameterType(Object arg, Class<?> targetType) {
		if (arg.toString().equals(" "))
			throw new ArgumentMismatchException("공백을 입력할 수 없습니다.");
		
		if (targetType.equals(int.class) || targetType.equals(Integer.class)) {
			return Integer.parseInt(arg.toString());
		}

		if (targetType.equals(String.class)) {
			return arg.toString();
		}

		return arg;
	}

	private Object[] convertParameterMaptoArray(Parameter[] parameters, Map<String, Object> inputParameters) {
		if (parameters.length != inputParameters.size())
			throw new ArgumentMismatchException("입력 값을 확인해주세요.");

		Object[] returnArray = new Object[parameters.length];

		for (int i = 0; i < returnArray.length; i++) {
			if (!inputParameters.containsKey(parameters[i].getName())) {
				throw new ArgumentMismatchException("입력 값을 확인해주세요.");
			}

			returnArray[i] = inputParameters.get(parameters[i].getName());
		}

		return returnArray;
	}

	//입력받은 파라미터를 실제 호출될 메서드의 파라미터 순서로 정렬 및 Object[] 타입으로 변환
	private Object[] createMethodArguments(Method method, MethodInfo methodInfo) throws ArgumentMismatchException {
		Class<?>[] parameterTypes = method.getParameterTypes();
		Object[] arg = new Object[parameterTypes.length];
		Object[] inputArgs = convertParameterMaptoArray(method.getParameters(),
			methodInfo.getParameterMap());

		for (int i = 0; i < parameterTypes.length; i++) {
			arg[i] = convertParameterType(inputArgs[i], parameterTypes[i]);
		}

		return arg;
	}
}
