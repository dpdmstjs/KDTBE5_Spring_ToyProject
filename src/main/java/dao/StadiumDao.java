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
public class StadiumDao {

	private static StadiumDao stadiumDAO;
	private final Connection connection;

	private StadiumDao() {
		connection = DBConnection.getInstance();
	}

	public static StadiumDao getInstance() {
		if (stadiumDAO == null) {
			stadiumDAO = new StadiumDao();
		}
		return stadiumDAO;
	}

	public int insertStadium(String name) {
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

	public List<Stadium> selectStadiums() {
		List<Stadium> stadiums = new ArrayList<>();
		String query = "SELECT * FROM stadium";
		try (Statement statement = connection.createStatement()) {
			try (ResultSet resultSet = statement.executeQuery(query)) {
				while (resultSet.next()) {
					Stadium stadium = buildStadiumFromResultSet(resultSet);
					stadiums.add(stadium);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return stadiums;
	}

	protected boolean isExistStadiumId(int stadiumId) {
		String query = "SELECT COUNT(id) FROM stadium WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, stadiumId);

			try (ResultSet resultSet = statement.executeQuery()) {
				resultSet.next();

				if (resultSet.getInt(1) > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
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

