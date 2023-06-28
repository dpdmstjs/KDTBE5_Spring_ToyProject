import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dao.PositionDao;
import dao.StadiumDAO;
import dao.TeamDAO;
import db.DBConnection;
import dto.PositionRespDto;
import util.ComponentScan;
import util.MethodInfo;
import util.MyScanner;

public class BaseBallApp {

	//@TODO: 별도 App 클래스 만들어서 메인 메서드에서는 App.run(); 방식으로 구조 변경
	public static void main(String[] args) throws Exception {
		Connection connection = DBConnection.getInstance();
		StadiumDAO stadiumDAO = new StadiumDAO(connection);
		TeamDAO teamDAO = new TeamDAO(connection);
		PositionDao positionDao = new PositionDao(connection);

		PositionRespDto positionRespDto = positionDao.positionList();
		Map<String, List<String>> positionMap = positionRespDto.getPositionMap();
		List<String> teamList = positionRespDto.getTeamList();

		System.out.printf("%-10s", "포지션");
		for (String team : teamList) {
			System.out.printf("%-10s", team);
		}
		System.out.println();

		for (String position : positionMap.keySet()) {
			System.out.printf("%-10s", position);
			List<String> teamPlayerList = positionMap.get(position);
			for (String team : teamList) {
				String playerName = teamPlayerList.get(teamList.indexOf(team));
				System.out.printf("%-10s", playerName);
			}
			System.out.println();
		}

		MyScanner scanner = new MyScanner();
		ComponentScan componentScan = ComponentScan.getInstance();

		while (true) {
			String uri = scanner.getRequest();
			if (uri.equals("종료"))
				break;
			
			MethodInfo methodInfo = scanner.parseData(uri);
			Set<Class> classes = componentScan.scanPackage("controller");
			String response = componentScan.findUri(classes, methodInfo);

			System.out.println(response);
		}

	}
}
