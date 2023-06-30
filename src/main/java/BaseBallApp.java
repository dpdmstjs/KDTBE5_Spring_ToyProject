import exception.ArgumentMismatchException;
import util.ComponentScan;
import util.MethodInfo;
import util.MyScanner;

public class BaseBallApp {

	//@TODO: 별도 App 클래스 만들어서 메인 메서드에서는 App.run(); 방식으로 구조 변경
	public static void main(String[] args) {

		MyScanner scanner = new MyScanner();
		ComponentScan componentScan = ComponentScan.getInstance();

		while (true) {
			String request = scanner.getRequest();
			if (request.equals("종료"))
				break;

			try {
				MethodInfo methodInfo = scanner.parseData(request);
				String response = componentScan.invokeMethod(methodInfo);

				System.out.println(response);
			} catch (ArgumentMismatchException e) {
				System.out.println(e.getMessage());
			}
		}

	}
}