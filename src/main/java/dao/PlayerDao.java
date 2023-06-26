package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import constant.Position;
import lombok.RequiredArgsConstructor;
import model.Player;

@RequiredArgsConstructor
public class PlayerDao {
	private final Connection connection;

	public Player createPlayer(int teamId, String name, Position position) {
		String sql = "insert into player(team_id, name, position) values (?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, teamId);
			statement.setString(2, name);
			statement.setString(3, position.getName());

			int rowCount = statement.executeUpdate();

			if (rowCount > 0) {
				ResultSet resultSet = statement.getGeneratedKeys();
				resultSet.next();
				return getPlayerById(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	public List<Player> getPlayersByTeam(int teamId) {
		List<Player> playerList = new ArrayList<>();

		String sql = "select * from player where team_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, teamId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Player player = buildPlayerFromResultSet(resultSet);
					playerList.add(player);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return playerList;
	}

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
		Position position = Position.findByName(resultSet.getString("position"));
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
