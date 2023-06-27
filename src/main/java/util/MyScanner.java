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
		List<Object> params = new ArrayList<>();

		String[] methodArray = request.split("\\?");

		String[] paramArray = methodArray[1].split("&");

		for (String param : paramArray) {
			params.add(param.split("=")[1]);
		}

		MethodInfo methodInfo = new MethodInfo(methodArray[0], params.toArray());

		return methodInfo;
	}
}
