package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import lombok.RequiredArgsConstructor;
import model.Stadium;

@RequiredArgsConstructor
public class StadiumDAO {

	private static StadiumDAO stadiumDAO;
	private final Connection connection;

	private StadiumDAO() {
		connection = DBConnection.getInstance();
	}

	public static StadiumDAO getInstance() {
		if (stadiumDAO == null) {
			stadiumDAO = new StadiumDAO();
		}
		return stadiumDAO;
	}

	public int createStadium(String name) {
		String query = "INSERT INTO stadium(name, created_at) VALUES (?, now())";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, name);

			int rowCount = statement.executeUpdate();

			return rowCount;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Stadium selectStadiumById(int id) {
		String query = "SELECT * FROM stadium WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return buildStadiumFromResultSet(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	public List<Stadium> selectStadiumList() {
		List<Stadium> stadiumList = new ArrayList<>();
		String query = "SELECT * FROM stadium";
		try (Statement statement = connection.createStatement()) {
			try (ResultSet resultSet = statement.executeQuery(query)) {
				while (resultSet.next()) {
					Stadium stadium = buildStadiumFromResultSet(resultSet);
					stadiumList.add(stadium);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
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

