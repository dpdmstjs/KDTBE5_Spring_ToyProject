import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.TeamDAO;
import db.DBConnection;
import dao.StadiumDAO;
import dto.TeamRespDTO;
import model.Stadium;

public class BaseBallApp {
	public static void main(String[] args) {
		Connection connection = DBConnection.getInstance();
		StadiumDAO stadiumDAO = new StadiumDAO(connection);
		TeamDAO teamDAO = new TeamDAO(connection);



	}
}
