import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dao.PositionDao;
import dao.TeamDAO;
import db.DBConnection;
import dao.StadiumDAO;


public class BaseBallApp {
	public static void main(String[] args) throws SQLException {
		Connection connection = DBConnection.getInstance();
		StadiumDAO stadiumDAO = new StadiumDAO(connection);
		TeamDAO teamDAO = new TeamDAO(connection);
		PositionDao positionDao = new PositionDao(connection);


		Map<String, List<String>> positionMap = positionDao.positionList();

		System.out.printf("%-10s", "Position");
		positionMap.keySet().forEach(team -> System.out.printf("%-10s", team));
		System.out.println();

		positionMap.forEach((position, teamPlayerList) -> {
			System.out.printf("%-10s", position);
			teamPlayerList.forEach(player -> System.out.printf("%-10s", player));
			System.out.println();
		});

	}
}
