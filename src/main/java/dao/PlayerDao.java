package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import constant.Position;
import lombok.RequiredArgsConstructor;
import model.Player;

@RequiredArgsConstructor
public class PlayerDao {
	private final Connection connection;

	public Player getPlayerById(int id) {
		String sql = "select * from player where id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return buildPlayerFromResultSet(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	private Player buildPlayerFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		int teamId = resultSet.getInt("team_id");
		String name = resultSet.getString("name");
		Position position = Position.valueOf(resultSet.getString("position"));
		Timestamp createdAt = resultSet.getTimestamp("created_at");

		return Player.builder()
			.id(id)
			.teamId(teamId)
			.name(name)
			.position(position)
			.createdAt(createdAt)
			.build();

	}
}
