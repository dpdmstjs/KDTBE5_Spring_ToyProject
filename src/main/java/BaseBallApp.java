import java.sql.Connection;
import java.sql.SQLException;
import db.DBConnection;
import dao.StadiumDAO;

public class BaseBallApp {
	public static void main(String[] args) {
		Connection connection = DBConnection.getInstance();
		StadiumDAO stadiumDAO = new StadiumDAO(connection);

		try {
			stadiumDAO.createStadium(1, "잠실야구장");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
