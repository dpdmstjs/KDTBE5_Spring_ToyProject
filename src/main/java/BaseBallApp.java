import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.DBConnection;
import dao.StadiumDAO;
import model.Stadium;

public class BaseBallApp {
	public static void main(String[] args) {
		Connection connection = DBConnection.getInstance();
		StadiumDAO stadiumDAO = new StadiumDAO(connection);

		try {
			List<Stadium> stadiumList = stadiumDAO.getStadiumList();
			System.out.println(stadiumList);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
