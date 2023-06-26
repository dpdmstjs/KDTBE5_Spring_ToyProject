package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import lombok.Getter;
import model.Stadium;

@Getter
public class StadiumDAO {
	private Connection connection;

	public StadiumDAO(Connection connection) {
		this.connection = connection;
	}

	public Stadium createStadium(int id, String name) throws SQLException {
		String query = "INSERT INTO stadium(id, name, created_at) VALUES (?, ?, now())";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.setString(2, name);
			statement.executeUpdate();
		}
		return null;
	}



}
