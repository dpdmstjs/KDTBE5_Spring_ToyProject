import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dao.PositionDao;
import dao.TeamDAO;
import db.DBConnection;
import dao.StadiumDAO;
import dto.PositionRespDto;
import util.ComponentScan;
import util.MethodInfo;
import util.MyScanner;
import util.annotation.RequestMapping;

public class BaseBallApp {

	//@TODO: 별도 App 클래스 만들어서 메인 메서드에서는 App.run(); 방식으로 구조 변경
	public static void main(String[] args) throws Exception {
		Connection connection = DBConnection.getInstance();
		StadiumDAO stadiumDAO = new StadiumDAO(connection);
		TeamDAO teamDAO = new TeamDAO(connection);
		PositionDao positionDao = new PositionDao(connection);

		PositionRespDto positionRespDto = positionDao.positionList();

		Map<String, List<String>> positionMap = positionRespDto.getPositionMap();
		System.out.printf("%-10s", "Position");
		positionMap.keySet().forEach(team -> System.out.printf("%-10s", team));
		System.out.println();
		positionMap.forEach((position, teamPlayerList) -> {
			System.out.printf("%-10s", position);
			teamPlayerList.forEach(player -> System.out.printf("%-10s", player));
			System.out.println();
		});
    
		MyScanner scanner = new MyScanner();
		ComponentScan componentScan = ComponentScan.getInstance();

		while (true) {
			String uri = scanner.getRequest();
			MethodInfo methodInfo = scanner.parseData(uri);
			Set<Class> classes = componentScan.scanPackage("controller");
			String response = componentScan.findUri(classes, methodInfo);

			System.out.println(response);
		}

	}
}
