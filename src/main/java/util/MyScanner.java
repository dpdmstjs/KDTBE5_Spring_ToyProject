package util;

import java.util.ArrayList;
import java.util.List;
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

		if (!request.contains("?")) {
			methodInfo.setName(request);
			return methodInfo;
		}

		String[] methodArray = request.split("\\?");

		methodInfo.setName(methodArray[0]);

		String[] paramArray = methodArray[1].split("&");

		for (String param : paramArray) {
			params.add(param.split("=")[1]);
		}

		methodInfo.setParameters(params.toArray());

		return methodInfo;
	}
}
