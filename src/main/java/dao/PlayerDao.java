package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constant.Position;
import db.DBConnection;
import dto.PositionRespDto;
import exception.ElementNotFoundException;
import lombok.RequiredArgsConstructor;
import model.Player;

@RequiredArgsConstructor
public class PlayerDao {
	private final Connection connection;
	private TeamDAO teamDao = new TeamDAO(DBConnection.getInstance());

	public int createPlayer(int teamId, String name, Position position) {
		if (!teamDao.isExistTeam(teamId))
			throw new ElementNotFoundException("해당 팀은 존재하지 않습니다.");

		String sql = "insert into player(team_id, name, position) values (?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, teamId);
			statement.setString(2, name);
			statement.setString(3, position.getName());

			int rowCount = statement.executeUpdate();

			return rowCount;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Player> selectPlayersByTeam(int teamId) {
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

	public int updatePlayerTeamId(int id, int teamId) {
		String sql = "update player set team_id = ? where id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			if (teamId == 0) {
				statement.setNull(1, Types.INTEGER);
			} else {
				statement.setInt(1, teamId);
			}

			statement.setInt(2, id);

			int rowCount = statement.executeUpdate();

			return rowCount;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Player selectPlayerById(int id) {
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

	public PositionRespDto positionList() {
		List<String> teamList = getTeamNameList();
		Map<Position, List<String>> positionMap = new HashMap<>();

		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT a.position, ");

			// 팀이름으로 각각 대체
			for (String team : teamList) {
				builder.append("MAX(IF(a.team_name = ?, a.player_name, '-')) as ");
				builder.append(team);
				builder.append(", ");
			}

			builder.delete(builder.length() - 2, builder.length());
			builder.append(" FROM ( ");
			builder.append("SELECT p.name as player_name, p.position, t.name as team_name ");
			builder.append("FROM team t ");
			builder.append("JOIN player p ON p.team_id = t.id ");
			builder.append(") a ");
			builder.append("GROUP BY a.position");

			PreparedStatement statement = connection.prepareStatement(builder.toString());

			int index = 1;
			for (String team : teamList) {
				statement.setString(index++, team);
			}

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Position position = Position.findByName(resultSet.getString("position"));
					List<String> teamPlayerList = new ArrayList<>();

					for (String team : teamList) {
						String teamName = resultSet.getString(team);
						teamPlayerList.add(teamName);
					}
					positionMap.put(position, teamPlayerList);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return PositionRespDto.builder()
			.positionMap(positionMap)
			.teamList(teamList)
			.build();
	}

	public List<String> getTeamNameList() {
		List<String> teamList = new ArrayList<>();
		try {
			String query = "SELECT name FROM team";
			PreparedStatement statement = connection.prepareStatement(query);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					teamList.add(resultSet.getString("name"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return teamList;
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