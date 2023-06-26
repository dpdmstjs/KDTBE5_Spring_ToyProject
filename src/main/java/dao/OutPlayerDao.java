package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OutPlayerDao {
	private final Connection connection;

	public int createOutPlayer(int playerId, String reason) {
		String sql = "insert into out_player(player_id, reason) values (?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, playerId);
			statement.setString(2, reason);

			int rowCount = statement.executeUpdate();

			return rowCount;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
