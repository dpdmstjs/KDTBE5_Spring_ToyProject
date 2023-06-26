package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
			int rowCount = statement.executeUpdate();
			if (rowCount > 0) {
				return getStadiumById(id);
			}
		}
		return null;
	}

	public Stadium getStadiumById(int id) throws SQLException {
		String query = "SELECT * FROM stadium WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return buildStadiumFromResultSet(resultSet);
				}
			}
		}
		return null;
	}

	public List<Stadium> getStadiumList() throws SQLException {
		List<Stadium> stadiumList = new ArrayList<>();
		String query = "SELECT * FROM stadium";
		try (Statement statement = connection.createStatement()) {
			try(ResultSet resultSet = statement.executeQuery(query)){
				while (resultSet.next()) {
					Stadium stadium = buildStadiumFromResultSet(resultSet);
					stadiumList.add(stadium);
				}
			}
		}
		return stadiumList;
	}


	private Stadium buildStadiumFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		Timestamp createdAt = resultSet.getTimestamp("created_at");

		return Stadium.builder()
			.id(id)
			.name(name)
			.createdAt(createdAt)
			.build();
	}

}