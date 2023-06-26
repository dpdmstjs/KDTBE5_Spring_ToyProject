package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Stadium;

public class TeamDAO {
	private Connection connection;

	public TeamDAO(Connection connection) {
		this.connection = connection;
	}

	public Stadium createTeam(int id, int stadiumId, String name) throws SQLException {
		String query = "INSERT INTO team(id, stadium_id, name, created_at) VALUES (?, ?, ?, now())";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.setInt(2, stadiumId);
			statement.setString(3, name);
			statement.executeUpdate();
		}
		return null;
	}

}
