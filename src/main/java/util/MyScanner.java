package util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MyScanner {

	public String getRequest() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("어떤 기능을 요청하시겠습니까?");

		return scanner.nextLine();
	}

	public MethodInfo parseData(String request) {
		MethodInfo methodInfo = new MethodInfo();
		List<Object> params = new ArrayList<>();
		Map<String, Object> parameterMap = new LinkedHashMap<>();

		if (!request.contains("?")) {
			methodInfo.setName(request);
			return methodInfo;
		}

		try {
			String[] methodArray = request.split("\\?");

			methodInfo.setName(methodArray[0]);

			String[] paramArray = methodArray[1].split("&");

			for (String param : paramArray) {
				parameterMap.put(param.split("=")[0], param.split("=")[1]);
			}

			methodInfo.setParameterMap(parameterMap);

			return methodInfo;
		} catch (Exception e) {
			System.out.println("입력 값을 확인해주세요.");
		}

		return null;
	}
}
